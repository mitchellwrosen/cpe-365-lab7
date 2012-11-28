import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	
	/* Revenue Members */
	private JTabbedPane RevenueTab;
	private JButton RevenueRevenueButton;
	private JButton RevenueReservationsButton;
	private JButton RevenueOccupiedButton;
	private JTable RevenueTable;
	
	/* Reservation Members */
	private JTabbedPane ReservationsTab;
	private JTextField ReservationRoomNameText;
	private JTextField ReservationStartText;
	private JTextField ReservationStopText;
	private JTable ReservationsTable;

	/* Room Members */
	private JTabbedPane RoomsTab;
	private JButton RoomsInformationButton;
	private JButton RoomsReservationsButton;
	private JTable RoomsTable;

	public OwnerPanel(DatabaseHandle handle) {
		this.handle = handle;
		Owner.setHandle(handle);
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

		
		

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		OccupancyTab = new JTabbedPane();
		OccupancyTab.add(vBox);
		return OccupancyTab;
	}
	private void occupancyAction() {
		try {
			System.out.println("User input->" + this.OccStartText.getText() 
					+ " " + this.OccStopText.getText());
			if( this.OccStartText.getText().length() != 0 && this.OccStopText.getText().length() != 0) {
				new PopPanel(Owner.getOccupancy(this.OccStartText.getText(),this.OccStopText.getText()),Owner.OccupancyColV).setVisible(true); /* TODO: Pick out Id */
			}
			else if ( this.OccStartText.getText().length() != 0) {
				new PopPanel(Owner.getOccupancy(this.OccStartText.getText()),Owner.OccupancyColV).setVisible(true);
			}
			else System.out.print("POOP\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*private JTable createOccupancyTable() {
		//JTable ret = new JTable(new DefaultTableModel(Owner.OccupancyCol, 12));
		JTable ret = new JTable(Owner.getOccupancy("", ""),Owner.OccupancyColV);
		ret.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				new OwnerPanel.ReservationListPanel("2","29-MAR-2010","01-DEC-2020").setVisible(true); 
			}
		});
		return ret;
	}
	*/

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
		return new JTable(Owner.getMonthlyRevenueReservations("",""),Owner.RevenueColNameV);
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
		JTable ret = new JTable(Owner.getReservations("","",""),Owner.ReservationColNameV);
		ret.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				new OwnerPanel.DetailedReservationPanel("3").setVisible(true);
			}
		});
		return ret;
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
		new RoomInfoPanel("test").setVisible(true); /* TODO: changed Id */
		System.out.println("Rooms->Information Pressed");
	}
	private void RoomsReservationsAction() {
		new DetailedReservationPanel("test").setVisible(true); /* TODO: changed Id */
		System.out.println("Rooms-Reservations Pressed");
	}
	private JTable createRoomsTable() {
		return new JTable(Owner.getRooms(),Owner.roomsColV);
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
			
			for( String str : DatabaseConstants.ROOMS_ATTRS) {
				Box hBox = Box.createHorizontalBox();
				hBox.add(new JLabel(str+":"));
				hBox.add(Box.createHorizontalStrut(strutSize));
				hBox.add(new JLabel("N/A")); /* TODO: Implement */
				vBox.add(hBox);
				vBox.add(Box.createVerticalStrut(strutSize));
			}
			panel.add(vBox);
			
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			pack();
		}
	};
	
	private class DetailedReservationPanel extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		
		public DetailedReservationPanel(String ID) {
			panel = new JPanel();
			add(panel);
			Box vBox = Box.createVerticalBox();
			vBox.add(Box.createVerticalStrut(strutSize));
			
			for( String str : DatabaseConstants.RESERVATIONS_ATTRS) {
				Box hBox = Box.createHorizontalBox();
				hBox.add(new JLabel(str+":"));
				hBox.add(Box.createHorizontalStrut(strutSize));
				hBox.add(new JLabel("N/A")); /* TODO: Implement */
				vBox.add(hBox);
				vBox.add(Box.createVerticalStrut(strutSize));
			}
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			pack();
		}
	}
	private class PopPanel extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		private JTable table;
		public PopPanel( Vector<Vector<String>> data, Vector<String> colNames) {
			panel = new JPanel();
			add(panel);
			table = new JTable(data,colNames);
			panel.add(new JScrollPane(table));
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			pack();
		}
	}
	private class ReservationListPanel extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		private JTable table;
		public ReservationListPanel(String roomID, String start, String end) {
			panel = new JPanel();
			add(panel);
			table = new JTable(Owner.getRoomOccupancy("","",""),Owner.roomOccupancyColV);
			panel.add(new JScrollPane(table));
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			pack();
		}
	}
	
}
