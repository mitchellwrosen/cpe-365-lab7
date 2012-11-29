package owner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;

import calpoly.DatabaseConstants;
import calpoly.DatabaseHandle;

public class Occupancy {

	static private JTabbedPane OccupancyTab;
	
	static private JTextField OccStartText;
	static private JTextField OccStopText;
	static private int strutSize = 15;

	static public JTabbedPane createOccupancyTab(DatabaseHandle handle) {
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		OccupancyModel.setHandle(handle);

		OccStartText = new JTextField();
		OccStartText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				occupancyAction();
			}
		});
		OccStopText = new JTextField();
		OccStopText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				occupancyAction();
			}
		});

		hBox.add(new JLabel("Start Date:"));
		hBox.add(OccStartText);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(new JLabel("Stop Date:"));
		hBox.add(OccStopText);
		hBox.add(Box.createHorizontalStrut(strutSize));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		OccupancyTab = new JTabbedPane();
		OccupancyTab.add(vBox);
		return OccupancyTab;
	}

	static private void occupancyAction() {
		String start = OccStartText.getText();
		String stop = OccStopText.getText();
		System.out.println("User input->" + start + " " + stop);

		try {
			if (start.length() != 0 && stop.length() != 0) {
				new ReservationListFrame(start,stop).setVisible(true);
			} else if (start.length() != 0) {
				new ReservationFrame(start).setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* For single date entries */
	static class ReservationFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		private JTable table;
		private final String date; 
		public ReservationFrame(String date) throws SQLException {
			this.date=date;
			panel = new JPanel();
			Box vBox = Box.createVerticalBox();
			table = new JTable(OccupancyModel.getOccupancy(date), OccupancyModel.OccupancyColV);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Action(e);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			vBox.add(new JScrollPane(table));
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			this.add(panel);
			pack();
		}
		/* Display detailed information about any selected reservation */
		public void Action(MouseEvent e) throws SQLException {
			JTable target = (JTable)e.getSource();
			int row = target.getSelectedRow();
			/* Display Selected Rows detailed info */
			new ReservationDetailedPopup((String)target.getValueAt(row, 0),date).setVisible(true); /*TODO: replace magic */
		}
	}

	/* For two date entries */
	static class ReservationListFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		private JTable table;
		private final String start;
		private final String stop;
		public ReservationListFrame( String start, String stop) throws SQLException {
			this.start=start;
			this.stop=stop;
			panel = new JPanel();
			Box vBox = Box.createVerticalBox();
			table = new JTable(OccupancyModel.getOccupancy(start, stop), OccupancyModel.OccupancyColV);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Action(e);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			vBox.add(new JScrollPane(table));
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			this.add(panel);
			pack();

		}
		private void Action( MouseEvent e ) throws SQLException {
			JTable target = (JTable)e.getSource();
			int row = target.getSelectedRow();
			/* Display Selected rows list of reservations in that interval */
			new ReservationListPopup((String)target.getValueAt(row, 0),start, stop).setVisible(true); /*TODO: Replace Magic Number */
		}
	}
	/* Popups up when row selected from Reservation List Frame */
	static class ReservationListPopup extends JFrame {
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
	static class ReservationDetailedPopup extends JFrame {
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
			pack();

		}
	}
}
