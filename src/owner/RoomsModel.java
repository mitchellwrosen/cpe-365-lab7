package owner;

import java.util.Arrays;
import java.util.Vector;

import calpoly.DatabaseConstants;
import calpoly.DatabaseHandle;

public class RoomsModel {
	
	private RoomsModel(){};
	private static DatabaseHandle handle;
	public static void setHandle(DatabaseHandle handle) {
		RoomsModel.handle = handle;
	}
	static public final String [] roomsCol = {"Room Name"}; 
	static final public Vector<String> roomsColV = new Vector<String>(Arrays.asList(roomsCol));
	static public Vector<Vector<String>> getRooms() {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"Awesome Room"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
	
	
	
	static final public Vector<String> DCroomCols = new Vector<String>(Arrays.asList(DatabaseConstants.ROOMS_ATTRS));
	static public Vector<Vector<String>> getInformation( String roomId) {
		Vector<Vector<String>> ret = new Vector<Vector<String>>();
		String [] junk = {"12C","Awesome Room","2","Queen","3","5.50","Stupid-like"};
		ret.add( new Vector<String>(Arrays.asList(junk)));	
		return ret;
	}
}
