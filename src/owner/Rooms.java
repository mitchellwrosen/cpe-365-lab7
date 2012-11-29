package owner;
/* 
 * @author Matthew Tondreau (mmtondre) 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import calpoly.DatabaseConstants;
import calpoly.DatabaseHandle;
import calpoly.OwnerPanel;

public class Rooms {
	static private JButton RoomsInformationButton;
	static private JButton RoomsReservationsButton;
	static private JTable RoomsTable;
	static private final int strutSize = 15;

	public static Box createRoomsTab(DatabaseHandle handle) {
		RoomsModel.setHandle(handle);
		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		try {
			RoomsTable = createRoomsTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RoomsInformationButton = new JButton("Information");
		RoomsInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					RoomsInformationAction(evt);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		RoomsReservationsButton = new JButton("Reservations");
		RoomsReservationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					RoomsReservationsAction(evt);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RoomsInformationButton);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RoomsReservationsButton);
		hBox.add(Box.createHorizontalStrut(strutSize));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		vBox.add(new JScrollPane(RoomsTable));

		return vBox;
	}

	static private void RoomsInformationAction(ActionEvent evt) throws SQLException {
		int row = RoomsTable.getSelectedRow();
		String name = (String) RoomsTable.getValueAt(row, 0);
		new RoomInfoPanel(name).setVisible(true);
		System.out.println("Rooms->Information Pressed");
		
	}

	static private void RoomsReservationsAction(ActionEvent evt) throws SQLException {
		int row = RoomsTable.getSelectedRow();
		String name = (String) RoomsTable.getValueAt(row, 0);
		new OwnerPanel.ReservationListPopup(name,"01-JAN-2010","31-DEC-2010").setVisible(true);
		System.out.println("Rooms-Reservations Pressed");
	}

	static private JTable createRoomsTable() throws SQLException {
		return new JTable(RoomsModel.getRooms(), RoomsModel.roomsColV);
	}

	static public class RoomInfoPanel extends JFrame {
		private static final long serialVersionUID = 1L;
		JPanel panel;
		JTable table;

		public RoomInfoPanel(String roomName) throws SQLException {
			panel = new JPanel();
			add(panel);
			Box vBox = Box.createVerticalBox();
			vBox.add(Box.createVerticalStrut(strutSize));
			String [] data = RoomsModel.getInformation(roomName);
			for (int i = 0; i <data.length; ++i) {
				Box hBox = Box.createHorizontalBox();
				hBox.add(new JLabel(DatabaseConstants.ROOMS_ATTRS[i]+":"));
				hBox.add(Box.createHorizontalStrut(strutSize));
				hBox.add(new JLabel(data[i])); 
				vBox.add(hBox);
				vBox.add(Box.createVerticalStrut(strutSize));
			}
			panel.add(vBox);
			this.setTitle(roomName);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			pack();
		}
	};

}
