import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OwnerPanel extends JPanel {
	/* OwnerPanel Members */
	private DatabaseHandle handle;
	private static final long serialVersionUID = 1L;
	private final int strutSize = 15;
	private JTabbedPane ownerTabs;

	/* Occupancy Members */
	private JTabbedPane OccupancyTab;
	private JTable OccupancyTable;
	private JTextField OccStartText;
	private JTextField OccStopText;
	private final String[] occTableColName = { "Room", "Status" };
	
	/* Revenue Members */
	private JTabbedPane RevenueTab;
	private JButton RevenueRevenueButton;
	private JButton RevenueReservationsButton;
	private JButton RevenueOccupiedButton;
	private JTable RevenueTable;
	private final String[] RevenueTableColName = { "Jan.", "Feb.", "Mar.",
			"Apr.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.",
			"Dec.", "Total" };
	
	/* Reservation Members */
	private JTabbedPane ReservationsTab;
	private JTextField ReservationRoomNameText;
	private JTextField ReservationStartText;
	private JTextField ReservationStopText;
	private JTable ReservationsTable;
	private final String[] RsvTableColName = { "Id.", "Room Name", "Check In",
			"Check Out" };

	/* Room Members */
	private JTabbedPane RoomsTab;
	private JButton RoomsInformationButton;
	private JButton RoomsReservationsButton;
	private JTable RoomsTable;
	private final String[] RoomsTableColName = { "Room Name" };

	public OwnerPanel(DatabaseHandle handle) {
		this.handle = handle;
		createOwnerTabs();
	}

	private void createOwnerTabs() {
		ownerTabs = new JTabbedPane();

		ownerTabs.addTab("Occupancy", createOccupancyTab());
		ownerTabs.addTab("Revenue", createRevenueTab());
		ownerTabs.addTab("Reservations", createReservationsTab());
		ownerTabs.addTab("Rooms", createRoomsTab());
		this.add(ownerTabs);
	}
	
	/*
	 * OR-1 OCCUPANCY OVERVIEW
	 */
	private JTabbedPane createOccupancyTab() {
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();

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
		hBox.add( new JLabel("Stop Date:"));
		hBox.add(OccStopText);
		hBox.add(Box.createHorizontalStrut(strutSize));

		
		OccupancyTable = createOccupancyTable();

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		vBox.add(new JScrollPane(OccupancyTable));
		OccupancyTab = new JTabbedPane();
		OccupancyTab.add(vBox);
		return OccupancyTab;
	}
	private void occupancyAction() {
		System.out.println("User input->" + this.OccStartText.getText() 
				+ " " + this.OccStopText.getText());
	}
	
	private JTable createOccupancyTable() {
		/* TODO(mmtondre): temporary for ui proto */
		return new JTable(new DefaultTableModel(occTableColName, 12));
	}

	/*
	 * OR-2 REVENUE 
	 */
	private JTabbedPane createRevenueTab() {

		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		RevenueTab = new JTabbedPane();
		RevenueTable = createRevenueTable();
		RevenueReservationsButton = new JButton("Reservations");
		RevenueReservationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RevenueReservationsAction(); 
			}
		});
		RevenueOccupiedButton = new JButton("Days Occupied");
		RevenueOccupiedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RevenueOccupiedAction(); 
			}
		});
		RevenueRevenueButton = new JButton("Monthly Revenue");
		RevenueRevenueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RevenueRevenueAction(); 
			}
		});
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RevenueReservationsButton);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RevenueOccupiedButton);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RevenueRevenueButton);
		hBox.add(Box.createHorizontalStrut(strutSize));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		vBox.add(new JScrollPane(RevenueTable));
		RevenueTab.add(vBox);
		return RevenueTab;
	}
	private void RevenueRevenueAction() {
		System.out.println("Revenue->Revnue Pressed");
	}
	private void RevenueOccupiedAction() {
		System.out.println("Revenue->Occupied Pressed");
	}
	private void RevenueReservationsAction() {	
		System.out.println("Revenue->Reservations Pressed");
	}
	private JTable createRevenueTable() {
		return new JTable(
				new DefaultTableModel(RevenueTableColName, 12));
	}
	
	/*
	 * OR-3 RESERVATIONS
	 */
	private JTabbedPane createReservationsTab() {
		ReservationsTab = new JTabbedPane();
		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		ReservationsTable = createReservationsTable();

		ReservationRoomNameText = new JTextField();
		ReservationRoomNameText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationsAction();
		}});
			
		ReservationStartText = new JTextField();
		ReservationStartText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationsAction();
		}});
		ReservationStopText = new JTextField();
		ReservationStopText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationsAction();
		}});

		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add( new JLabel("Room Name:"));
		hBox.add(ReservationRoomNameText);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add( new JLabel("Start Date:"));
		hBox.add(ReservationStartText);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add( new JLabel("Stop Date:"));
		hBox.add(ReservationStopText);
		hBox.add(Box.createHorizontalStrut(strutSize));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		vBox.add(new JScrollPane(ReservationsTable));
		ReservationsTab.add(vBox);

		return ReservationsTab;
	}
	private void reservationsAction() {
		System.out.println("User Input->"+this.ReservationRoomNameText.getText() +" "
				+ this.ReservationStartText.getText() +" "
				+ this.ReservationStopText.getText());
	}
	
	private JTable createReservationsTable() {
		return new JTable(new DefaultTableModel(RsvTableColName, 10));
	}
	
	/* 
	 * OR-4 ROOMS
	 */
	private JTabbedPane createRoomsTab() {
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
	private void RoomsInformationAction() {
		System.out.println("Rooms->Information Pressed");
	}
	private void RoomsReservationsAction() {
		System.out.println("Rooms-Reservations Pressed");
	}
	private JTable createRoomsTable() {
		return new JTable(new DefaultTableModel(RoomsTableColName, 12));
	}
}
