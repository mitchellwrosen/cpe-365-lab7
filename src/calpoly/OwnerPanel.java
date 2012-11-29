package calpoly;
/* 
 * @author Matthew Tondreau (mmtondre) 
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import owner.Occupancy;
import owner.OccupancyModel;
import owner.OwnerModel;
import owner.Reservations;
import owner.Revenue;
import owner.Rooms;

public class OwnerPanel extends JPanel {
	private DatabaseHandle handle;
	private static final long serialVersionUID = 1L;
	private static final int strutSize = 15;
	private JTabbedPane ownerTabs;
	public static String DefaultStop = "31-DEC-2010";
	public static String DefaultStart = "01-JAN-2010";
	public OwnerPanel(DatabaseHandle handle) {
		this.handle = handle;
		OwnerModel.setHandle(handle);
		createOwnerTabs();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	private void createOwnerTabs() {
		ownerTabs = new JTabbedPane();
		ownerTabs.addTab("Occupancy", Occupancy.createOccupancyTab(handle));
		ownerTabs.addTab("Revenue", Revenue.createRevenueTab(handle));
		ownerTabs.addTab("Reservations", Reservations.createReservationsTab(handle));
		ownerTabs.addTab("Rooms", Rooms.createRoomsTab(handle));
		
		this.add(ownerTabs);
	}
	/* Popups up when row selected from Reservation List Frame */
	public static class ReservationListPopup extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		private JTable table;

		public ReservationListPopup(String roomName, String start, String stop) throws SQLException {
			
			panel = new JPanel();
			Box vBox = Box.createVerticalBox();
			table = new JTable(OccupancyModel.getRoomOccupancy(start,stop,roomName),OccupancyModel.roomOccupancyColV);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Action(e);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			this.setTitle(roomName+" "+start+" - "+stop );
			vBox.add(new JScrollPane(table));
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			this.add(panel);
			pack();

		}
		public void Action(MouseEvent e) throws SQLException {
			JTable target = (JTable)e.getSource();
			int row = target.getSelectedRow();
			/* Display Selected Rows detailed info */
			new ReservationDetailedPopup((String)target.getValueAt(row, 0)).setVisible(true);
		}
	}
	/* for detailed reservation information */
	public static class ReservationDetailedPopup extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;
	
		public ReservationDetailedPopup(String rsvID) throws SQLException {
			panel = new JPanel();
			Box vBox = Box.createVerticalBox();
			String [] data = OccupancyModel.getReservation(rsvID);
			for( int i = 0; i < data.length; ++i) {
				Box hBox = Box.createHorizontalBox();
				hBox.add(new JLabel(DatabaseConstants.RESERVATIONS_ATTRS[i]));
				hBox.add(Box.createHorizontalStrut(strutSize));
				hBox.add(new JLabel(data[i]));
				vBox.add(hBox);
			}
			this.setTitle(rsvID);
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			this.add(panel);
			pack();

		}
		public ReservationDetailedPopup(String roomName, String date) throws SQLException {
			panel = new JPanel();
			Box vBox = Box.createVerticalBox();
			String [] data = OccupancyModel.getReservation(roomName, date);
			for( int i = 0; i < data.length; ++i) {
				Box hBox = Box.createHorizontalBox();
				hBox.add(new JLabel(DatabaseConstants.RESERVATIONS_ATTRS[i]));
				hBox.add(Box.createHorizontalStrut(strutSize));
				hBox.add(new JLabel(data[i]));
				vBox.add(hBox);
			}
			
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			this.add(panel);
			this.setTitle(roomName + " " + date);
			pack();

		}
	}
}
