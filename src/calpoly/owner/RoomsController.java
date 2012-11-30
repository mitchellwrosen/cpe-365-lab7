package calpoly.owner;
/* 
 * @author Matthew Tondreau (mmtondre) 
 */
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import calpoly.DatabaseConstants;
import calpoly.DatabaseHandle;

public class RoomsController {
	
	private RoomsController(){};
	private static DatabaseHandle handle;
	public static void setHandle(DatabaseHandle handle) {
		RoomsController.handle = handle;
	}
	static public final String [] roomsCol = {"Room Name"}; 
	static final public Vector<String> roomsColV = new Vector<String>(Arrays.asList(roomsCol));
	static public Vector<Vector<String>> getRooms() throws SQLException {
		String query = "SELECT name FROM Rooms";
		return OwnerController.resToTable(handle.executeQuery(query));
	}
	
	
	
	static final public Vector<String> DCroomCols = new Vector<String>(Arrays.asList(DatabaseConstants.ROOMS_ATTRS));
	static public String [] getInformation( String roomName) throws SQLException {
		String query = "SELECT * FROM Rooms WHERE name='"+roomName+"'";
		
		return OwnerController.resToArray(handle.executeQuery(query));
	}
}
