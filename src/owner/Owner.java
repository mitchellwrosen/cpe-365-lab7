package owner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import calpoly.DatabaseConstants;
import calpoly.DatabaseHandle;



public class Owner {
	static private DatabaseHandle handle;
	private Owner() {} /* Singleton */
	static public void setHandle(DatabaseHandle handle) {
		Owner.handle=handle;
	}
	static public Vector<Vector<String>> resToTable(ResultSet res) throws SQLException {
		boolean f = res.next();
		Vector<Vector<String>> vStr = new Vector<Vector<String>>();
		while(f) {
			Vector<String> row = new Vector<String>();
			for(int i = 1; i <= res.getMetaData().getColumnCount(); ++i) {
				
				row.add(res.getString(i));
			}
			vStr.add(row);
			f= res.next();
		}
		return vStr;
	}
	static public String [] resToArray(ResultSet res) throws SQLException {
		Vector<String> ret = new Vector<String>();
		boolean f = res.next();
		while(f) {
			for(int i = 1; i <= res.getMetaData().getColumnCount(); ++i) {
				ret.add(res.getString(i));
			}
			f= res.next();
		}
		return (String[]) ret.toArray(new String[ret.size()]);
	}
	/*
	 * OR-1 OCCUPANCY OVERVIEW
	 */
	
	public final static String [] OccupancyCol = { "Room", "Status" };
	public final static String defaultStart = "01-JAN-2010";
	public final static String defaultStop = "31-DEC-2010";
	static public Vector<Vector<String>> getOccupancy( String Date) throws SQLException {
		String query = "(SELECT UNIQUE name, 'Occupied' " +
				" FROM Reservations RE, Rooms RO" +
				" WHERE RE.RoomId=RO.Id" +
				" AND CheckInDate<'" + Date + "'"+
				" AND CheckOutDate>'"+Date+"')"+
				
				" UNION(SELECT UNIQUE name, 'Unoccupied' " +
				" FROM Reservations RE, Rooms RO" +
				" WHERE RE.RoomId=RO.Id" +
				" AND RoomId NOT IN " +
				"(SELECT RoomId " +
				" FROM Reservations RE, Rooms RO" +
				" WHERE RE.RoomId=RO.Id" +
				" AND CheckInDate<'"+Date+"'"+
				" AND CheckOutDate>'"+Date+"'))";
			System.out.println(query);
		return resToTable(handle.executeQuery(query));
	}
	
	public final static Vector<String> OccupancyColV = new Vector<String>(Arrays.asList(OccupancyCol));
	static public Vector<Vector<String>> getOccupancy( String start, String end) throws SQLException {
		String query;
		System.out.println("WTF\n");
			query = "(SELECT UNIQUE name, 'Occupied' " +
					" FROM Reservations RE, Rooms RO" +
					" WHERE RE.RoomId=RO.Id" +
					" AND CheckInDate<='"+start+"'"+
					" AND CheckOutDate>='"+end+"')"+
					
					" UNION(SELECT UNIQUE name, 'Unoccupied' " +
					" FROM Reservations RE, Rooms RO" +
					" WHERE RE.RoomId=RO.Id" +
					" AND RoomId NOT IN " +
					"(SELECT RoomId " +
					" FROM Reservations RE, Rooms RO" +
					" WHERE RE.RoomId=RO.Id" +
					" AND CheckInDate<='"+start+"'"+
					" AND CheckOutDate>='"+end+"'))";
			System.out.println(query);
		
		
		
		
		return resToTable(handle.executeQuery(query));
	}
	static final public String [] roomOccupancyCol =  { "Reservation", "CheckInDate","CheckOutDate", "Last Name" };
	static final public Vector<String> roomOccupancyColV = new Vector<String>(Arrays.asList(roomOccupancyCol));
	static public Vector<Vector<String>> getRoomOccupancy( String start, String end, String roomName) throws SQLException {
		String query = "SELECT Code, CheckInDate, CheckOutDate, LastName" +
					   " FROM Reservations RE, Rooms RO"+
					   " WHERE RE.RoomId=RO.Id "+
					   " AND RO.name='"+roomName+"' "+
					   " AND CheckInDate<='"+end+"'"+
					   " AND CheckOutDate>'"+start+"'";
		
		return resToTable(handle.executeQuery(query));
	}
	static public final String [] RevenueColName =  {"Room", "Jan.", "Feb.", "Mar.",
		"Apr.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.",
		"Dec.", "Total" };

	static final public Vector<String> RevenueColNameV = new Vector<String>(Arrays.asList(RevenueColName));
	static public Vector<Vector<String>> getMonthlyRevenueReservations(String start, String end) {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"Awesome Room","1","1","1","1","1","1","1","1","1","1","1","1","12"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
	static public Vector<Vector<String>> getMonthlyDaysOccupied(String start, String end) {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"Awesome Room","2","2","2","2","2","2","2","2","2","2","2","2","24"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
	static public Vector<Vector<String>> getMonthlyRevenue( String start, String end) {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"Awesome Room","12.0","12.0","12.0","12.0","12.0","12.0","12.0","12.0","12.0","12.0","12.0","12.0","148.0"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
	
	
	static public final String [] ReservationColName = { "Id.", "Room Name", "Check In",
	"Check Out" };
	static final public Vector<String> ReservationColNameV = new Vector<String>(Arrays.asList(ReservationColName));
	static public Vector<Vector<String>> getReservations(String start, String end, String room) {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"12C","Awesome Room","03-MAR-2010","05-MAR-2010"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
	

	static public final String [] roomsCol = {"Room Name"}; 
	static final public Vector<String> roomsColV = new Vector<String>(Arrays.asList(roomsCol));
	static public Vector<Vector<String>> getRooms() {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"Awesome Room"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
	static final public Vector<String> DCreservationCols = new Vector<String>(Arrays.asList(DatabaseConstants.RESERVATIONS_ATTRS));
	static public String [] getReservation( String reservationId) throws SQLException {
		String query = "SELECT * FROM Reservations RE" +
					   " WHERE Code='"+reservationId+"'";
		return resToArray(handle.executeQuery(query));
	}
	
	static public String [] getReservation( String roomName, String date) {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"222","12C","03-MAR-2010","05-MAR-2010","5.50","Hanks","Tom","2","3"};
			
		return junk;
	}
	static final public Vector<String> DCroomCols = new Vector<String>(Arrays.asList(DatabaseConstants.ROOMS_ATTRS));
	static public Vector<Vector<String>> getInformation( String roomId) {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"12C","Awesome Room","2","Queen","3","5.50","Stupid-like"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
}
