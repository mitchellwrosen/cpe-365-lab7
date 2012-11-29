package owner;
/* 
 * @author Matthew Tondreau (mmtondre) 
 */
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import calpoly.DatabaseHandle;
import calpoly.OwnerPanel;

public class ReservationModel {
	private static DatabaseHandle handle;
	private ReservationModel(){}
	
	public static void setHandle(DatabaseHandle handle) {
		ReservationModel.handle = handle;
	}
	static public final String [] ReservationColName = { "Id.", "Room Name", "Check In",
	"Check Out" };
	static final public Vector<String> ReservationColNameV = new Vector<String>(Arrays.asList(ReservationColName));
	static public Vector<Vector<String>> getReservations(String start, String end, String room) throws SQLException {
		String qStart = (start.length() == 0) ? OwnerPanel.DefaultStart : start; 
		String qStop = (end.length() == 0) ? OwnerPanel.DefaultStop : end;
		String query = "SELECT Code, Name, CheckInDate, CheckOutDate "+
					   "FROM Reservations RE, Rooms RO "+
					   "WHERE RE.RoomID=RO.ID "+
					   "AND RO.Name LIKE '"+room+"%'"+
					   "AND RE.CheckInDate BETWEEN '"+qStart+"' AND '"+qStop+"'" ;
					
		
		
		return OwnerModel.resToTable(handle.executeQuery(query));
	}
}
