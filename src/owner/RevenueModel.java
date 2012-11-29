package owner;

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
		String [] str = new String[14]; /* TODO: MAGIC */
		for(int i = 0; i < str.length; ++i) 
			str[i] = "0.00";
		str[0] = room;
		return str;
	}
	static private String sumRows(String [] row) {
		double sum = 0;
		for( int i = 1; i < 13; ++i ) { /* TODO MAGIC */
			sum += Integer.parseInt(row[i]);
		}
		return String.valueOf(sum);
	}
	static final public Vector<String> RevenueColNameV = new Vector<String>(Arrays.asList(RevenueColName));
	static public Vector<Vector<String>> getMonthlyRevenueReservations() {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"Awesome Room","1","1","1","1","1","1","1","1","1","1","1","1","12"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
	static public Vector<Vector<String>> getMonthlyDaysOccupied() {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"Awesome Room","2","2","2","2","2","2","2","2","2","2","2","2","24"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
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
				
				row[getMonPosition(mon)] = rev;
			}
			
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
}
