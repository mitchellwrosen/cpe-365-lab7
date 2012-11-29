package owner;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import calpoly.DatabaseConstants;
import calpoly.DatabaseHandle;

public class OccupancyModel {
	private static DatabaseHandle handle;
	private OccupancyModel() {} /* SINGLETON */
	public static void setHandle(DatabaseHandle handle) {
		OccupancyModel.handle = handle;
	}
	
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
		return OwnerModel.resToTable(handle.executeQuery(query));
	}
	
	public final static Vector<String> OccupancyColV = new Vector<String>(Arrays.asList(OccupancyCol));
	/* EXAMPLE  using 01-MAR-2010 AND 02-MAR-2010
	   (SELECT UNIQUE name, 'Occupied'  
		  FROM Reservations RE, Rooms RO 
		  WHERE RE.RoomId=RO.Id 
		    AND CheckInDate<='01-MAR-2010' 
		    AND CheckOutDate>='02-MAR-2010') 
		UNION 
		(
		  SELECT UNIQUE name, 'Partially Occupied'
		    FROM Reservations RE, Rooms RO 
		    WHERE RE.RoomId=RO.Id 
		     AND NOT (CheckInDate<='01-MAR-2010' AND CheckOutDate>'02-MAR-2010')
		     AND (CheckInDate BETWEEN '01-MAR-2010' AND '02-MAR-2010' 
		     OR (CheckOutDate>'01-MAR-2010' AND CheckOutDate<='02-MAR-2010'))
		)
		UNION
		(SELECT UNIQUE name, 'Unoccupied'  
		  FROM Rooms RO 
		  WHERE Id NOT IN 
		    ((SELECT UNIQUE Id
		        FROM Reservations RE, Rooms RO 
		        WHERE RE.RoomId=RO.Id 
		        AND CheckInDate<='01-MAR-2010' 
		        AND CheckOutDate>='02-MAR-2010') 
		    UNION 
		    (SELECT UNIQUE Id
		      FROM Reservations RE, Rooms RO 
		      WHERE RE.RoomId=RO.Id 
		        AND NOT (CheckInDate<='01-MAR-2010' AND CheckOutDate>'02-MAR-2010')
		        AND (CheckInDate BETWEEN '01-MAR-2010' AND '02-MAR-2010' 
		        OR (CheckOutDate>'01-MAR-2010' AND CheckOutDate<='02-MAR-2010'))
		    ))
		)
*/
	
	static public Vector<Vector<String>> getOccupancy( String start, String end) throws SQLException {
		String query;
		System.out.println("WTF\n");
			query = "(SELECT UNIQUE name, 'Occupied'  " +
					"  FROM Reservations RE, Rooms RO " +
					"  WHERE RE.RoomId=RO.Id " +
					"    AND CheckInDate<='"+start+"' " +
					"    AND CheckOutDate>='"+end+"') " +
					"UNION (SELECT UNIQUE name, 'Partially  Occupied' " +
					"    FROM Reservations RE, Rooms RO " +
					"    WHERE RE.RoomId=RO.Id " +
					"     AND NOT (CheckInDate<='"+start+"' AND CheckOutDate>'"+end+"') " +
					"     AND (CheckInDate BETWEEN '"+start+"' AND '"+end+"' " +
					"     OR (CheckOutDate>'"+start+"' AND CheckOutDate<='"+end+"'))) " +
					"UNION (SELECT UNIQUE name, 'Unoccupied' " +
					"  FROM Rooms RO "+
					"  WHERE Id NOT IN "+
					"    ((SELECT UNIQUE Id " +
					"        FROM Reservations RE, Rooms RO " +
					"        WHERE RE.RoomId=RO.Id " +
					"        AND CheckInDate<='"+start+"' " +
					"        AND CheckOutDate>='"+end+"') " +
					"    UNION (SELECT UNIQUE Id " +
					"      FROM Reservations RE, Rooms RO " +
					"      WHERE RE.RoomId=RO.Id " +
					"        AND NOT (CheckInDate<='"+start+"' AND CheckOutDate>'"+end+"') " +
					"        AND (CheckInDate BETWEEN '"+start+"' AND '"+end+"' " +
					"        OR (CheckOutDate>'"+start+"' AND CheckOutDate<='"+end+"'))))) ";
			System.out.println(query);
		
		
		
		
		return OwnerModel.resToTable(handle.executeQuery(query));
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
		
		return OwnerModel.resToTable(handle.executeQuery(query));
	}
	
	static public String [] getReservation( String roomName, String date) throws SQLException {
		String query = "SELECT * FROM Reservations RE "+
					   " WHERE RE.RoomId= (SELECT Id FROM Rooms RO WHERE RO.name='"+roomName+"')"+
					   " AND CheckInDate<='" +date + "'"+
					   " AND CheckOutDate>'"+date+"'";
		
		return OwnerModel.resToArray(handle.executeQuery(query));
	}
	
	static final public Vector<String> DCreservationCols = new Vector<String>(Arrays.asList(DatabaseConstants.RESERVATIONS_ATTRS));
	static public String [] getReservation( String reservationId) throws SQLException {
		String query = "SELECT * FROM Reservations RE" +
					   " WHERE Code='"+reservationId+"'";
		return OwnerModel.resToArray(handle.executeQuery(query));
	}
}
