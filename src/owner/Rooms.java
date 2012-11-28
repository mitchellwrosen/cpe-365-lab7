package owner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import calpoly.DatabaseConstants;

public class Rooms {
	static private JTabbedPane RoomsTab;
	static private JButton RoomsInformationButton;
	static private JButton RoomsReservationsButton;
	static private JTable RoomsTable;
	static private final int strutSize = 15;

	public static JTabbedPane createRoomsTab() {
		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		RoomsTable = createRoomsTable();
		RoomsTab = new JTabbedPane();
		RoomsInformationButton = new JButton("Information");
		RoomsInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RoomsInformationAction();
			}
		});
		RoomsReservationsButton = new JButton("Reservations");
		RoomsReservationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RoomsReservationsAction();
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

		RoomsTab.add(vBox);

		return RoomsTab;
	}

	static private void RoomsInformationAction() {
		// new RoomInfoPanel("test").setVisible(true); /* TODO: changed Id */
		System.out.println("Rooms->Information Pressed");
	}

	static private void RoomsReservationsAction() {
		// new DetailedReservationPanel("test").setVisible(true); /* TODO:
		// changed Id */
		System.out.println("Rooms-Reservations Pressed");
	}

	static private JTable createRoomsTable() {
		return new JTable(Owner.getRooms(), Owner.roomsColV);
	}

	private class RoomInfoPanel extends JFrame {
		private static final long serialVersionUID = 1L;
		JPanel panel;
		JTable table;

		public RoomInfoPanel(String ID) {
			panel = new JPanel();
			add(panel);
			Box vBox = Box.createVerticalBox();
			vBox.add(Box.createVerticalStrut(strutSize));

			for (String str : DatabaseConstants.ROOMS_ATTRS) {
				Box hBox = Box.createHorizontalBox();
				hBox.add(new JLabel(str + ":"));
				hBox.add(Box.createHorizontalStrut(strutSize));
				hBox.add(new JLabel("N/A")); /* TODO: Implement */
				vBox.add(hBox);
				vBox.add(Box.createVerticalStrut(strutSize));
			}
			panel.add(vBox);

			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			pack();
		}
	};

	public class DetailedReservationPanel extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;

		public DetailedReservationPanel(String ID) {
			panel = new JPanel();
			add(panel);
			Box vBox = Box.createVerticalBox();
			vBox.add(Box.createVerticalStrut(strutSize));

			for (String str : DatabaseConstants.RESERVATIONS_ATTRS) {
				Box hBox = Box.createHorizontalBox();
				hBox.add(new JLabel(str + ":"));
				hBox.add(Box.createHorizontalStrut(strutSize));
				hBox.add(new JLabel("N/A")); /* TODO: Implement */
				vBox.add(hBox);
				vBox.add(Box.createVerticalStrut(strutSize));
			}
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			pack();
		}
	}
}
