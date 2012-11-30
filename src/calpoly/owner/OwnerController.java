package calpoly.owner;
/* 
 * @author Matthew Tondreau (mmtondre) 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import calpoly.DatabaseHandle;



public class OwnerController {
	private OwnerController() {} /* Singleton */
	static public void setHandle(DatabaseHandle handle) {

		RevenueController.setHandle(handle);
		RoomsController.setHandle(handle);
		ReservationController.setHandle(handle);
		OccupancyController.setHandle(handle);
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
	
}
