package owner;
/* 
 * @author Matthew Tondreau (mmtondre) 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import calpoly.DatabaseHandle;

public class RevenueModel {
	static private DatabaseHandle handle;
	static public void setHandle(DatabaseHandle handle) {
		RevenueModel.handle = handle;
	}
	static public final int RowSize = 14;
	static public final String [] RevenueColName =  {"Room", "Jan.", "Feb.", "Mar.",
		"Apr.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.",
		"Dec.", "Total" };
	
	static private final String[] Months = { "JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	static private int getMonPosition(String month) {
		for( int i= 0; i< Months.length; ++i ) {
			if( month.equals(Months[i]))
				return i;
		}
		return -1;
	}
	static private String [] initRow(String room) { 
		String [] str = new String[RowSize]; 
		for(int i = 0; i < str.length; ++i) 
			str[i] = "0.00";
		str[0] = room;
		return str;
	}
	static private String sumRows(String [] row) {
		double sum = 0;
		for( int i = 1; i < RowSize - 1; ++i ) { 
			sum += Double.valueOf(row[i]);
		}
		return String.valueOf(sum);
	}
	static final public Vector<String> RevenueColNameV = new Vector<String>(Arrays.asList(RevenueColName));
	
	static public Vector<Vector<String>> getMonthly(String ColName, String query ) throws SQLException {
		ResultSet res = handle.executeQuery(query);
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		boolean f = res.next();
		String curRoom = "";
		String [] row = null;
		while(f) {
			for( int i =0; i < res.getMetaData().getColumnCount(); ++i ) {
				
				String mon = res.getString("MONTH");
				String rev = res.getString(ColName);
				String room = res.getString("NAME");
				
				if( !room.equals(curRoom)) {
					if( row != null) {
						row[13] = sumRows(row);
						ret.add(new Vector<String>(Arrays.asList(row)));
					}
					curRoom = room;
					row = initRow(room);
				}
				
				row[getMonPosition(mon)+1] = rev;
			}
			f = res.next();
		}
		return ret;
		
	}
	static public Vector<Vector<String>> getMonthlyRevenue() throws SQLException {
		String query = "SELECT Name, TO_CHAR(CheckInDate,'MON') MONTH, SUM(Rate * (CheckOutDate-CheckInDate)) REVENUE "+
				"FROM Reservations RE, Rooms RO "+
			    "WHERE RE.RoomId=RO.Id "+
			  	"GROUP BY Name, TO_CHAR(CheckInDate,'MON') "+
			  	"ORDER BY Name";
		return getMonthly("REVENUE",query);
	}
	static public Vector<Vector<String>> getMonthlyRevenueReservations() throws SQLException {
		String query = "SELECT Name, TO_CHAR(CheckInDate,'MON') MONTH, COUNT(*) RESERVATIONS "+
				"FROM Reservations RE, Rooms RO "+
			    "WHERE RE.RoomId=RO.Id "+
			  	"GROUP BY Name, TO_CHAR(CheckInDate,'MON') "+
			  	"ORDER BY Name";
		return getMonthly("RESERVATIONS",query);
	}
	static public Vector<Vector<String>> getMonthlyDaysOccupied() throws SQLException {
		String query = "SELECT Name, TO_CHAR(CheckInDate,'MON') MONTH, SUM(CheckOutDate-CheckInDate) OCCUPIED "+
				"FROM Reservations RE, Rooms RO "+
			    "WHERE RE.RoomId=RO.Id "+
			  	"GROUP BY Name, TO_CHAR(CheckInDate,'MON') "+
			  	"ORDER BY Name";
		return getMonthly("OCCUPIED",query);
	}
}
