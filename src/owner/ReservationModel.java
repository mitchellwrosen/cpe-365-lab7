package owner;

import java.util.Arrays;
import java.util.Vector;

import calpoly.DatabaseHandle;

public class ReservationModel {
	private static DatabaseHandle handle;
	private ReservationModel(){}
	public static void setHandle(DatabaseHandle handle) {
		ReservationModel.handle = handle;
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
}
