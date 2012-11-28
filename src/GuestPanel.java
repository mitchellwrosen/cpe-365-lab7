import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GuestPanel extends JPanel {
   private DatabaseHandle handle;

   //buttons across top
   //Panel: TopPanel
   private JPanel topPanel;
   private JPanel botPanel;
   private JPanel roomPanel;
   private JPanel resPanel;
   private JPanel roomsListPanel;
   //Button: Rooms and Rates
   private JButton roomsAndRatesButton;
   //Button: Reservations
   private JButton reservationsButton;

   //Rooms and Rates Buttons & Fields
   //Left Panel
   private JPanel leftPanel;
   private JLabel roomsLabel;
   private JComboBox roomsCombo;
   private JLabel selectedRoomLabel;
   private JTable roomInfoTable;
   private JButton checkAvailabilityButton;

   //Right Panel
   private JPanel rightPanel;
   private JLabel checkInLabel;
   private JLabel checkOutLabel;
   private JSpinner checkInMonthSpinner;
   private JSpinner checkInDaySpinner;
   private JSpinner checkInYearSpinner;
   private JSpinner checkOutMonthSpinner;
   private JSpinner checkOutDaySpinner;
   private JSpinner checkOutYearSpinner;
   private JTable datesAvailableTable;
   private JButton placeReservationButton;
   
   //Reservations Buttons & Fields
   private JPanel resLeftPanel;
   private JLabel resCheckInLabel;
   private JLabel resCheckOutLabel;
   //Label: Select Check-in Date (top of panel)
   //Spinner(All horizontal): months
   //Spinner: days
   //Spinner: years

   //Label: Select Check-out Date (under check-in Fields)
   //Spinner(All horizontal): months
   //Spinner: days
   //Spinner: years
   //(Under Check out dates)Text field: info on selected room's availability for each night

   //Right Panel
   private JPanel resRightPanel;
   //Label: Selected Room
   //Text field: info on the room selected from field above
   //Button (At bottom of Panel): Make a Reservation
   
   //Reservation Completion Pop-Up
   //Label: Check-in Date and Check-out Date
   //Label: Please enter your name
   //Text Field: Last Name of Guest
   //Text Field: First Name of Guest
   //Label: Number of Adults
   //Spinner: Number of adults staying in room
   //Label: Number of Children
   //Spinner: Number of kids staying in room
   //Label: Discounts
   //Combo Box for Discounts: No discounts, AARP, or AAA Discount
   //Button: Place Reservation

   public GuestPanel(DatabaseHandle handle) {
      this.handle = handle;
      
      //Left and Right Panel setup
      leftPanel = new JPanel();
      leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.LINE_AXIS));
      rightPanel = new JPanel();
      rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.LINE_AXIS));
      resLeftPanel = new JPanel();
      resLeftPanel.setLayout(new BoxLayout(resLeftPanel, BoxLayout.LINE_AXIS));
      resRightPanel = new JPanel();
      resRightPanel.setLayout(new BoxLayout(resRightPanel, BoxLayout.LINE_AXIS));
      
      //Label Setup
      checkInLabel = new JLabel("Check In");
      checkOutLabel = new JLabel("Check Out");
      resCheckInLabel = new JLabel("Check In");
      resCheckOutLabel = new JLabel("Check Out");
      roomsLabel = new JLabel("Please Select a Room:");
      selectedRoomLabel = new JLabel("Room Information");
      
      //Buttons Setup
      roomsAndRatesButton = new JButton("Rooms and Rates");
      roomsAndRatesButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            roomPanel.setVisible(true);
            resPanel.setVisible(false);
         }
      });
      
      reservationsButton = new JButton("Reservations");
      reservationsButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            roomPanel.setVisible(false);
            resPanel.setVisible(true);
         }
      });
      
      //Create Combo Box
      createComboBox();
      roomsListPanel = new JPanel();
      roomsListPanel.add(roomsCombo);
      
      //Create JTables
      createRoomInfoTable();
      
      //Create layout
      topPanel = new JPanel();
      topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
      topPanel.add(roomsAndRatesButton);
      topPanel.add(reservationsButton);
      topPanel.setVisible(true);
      
      leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
      leftPanel.setAlignmentX(LEFT_ALIGNMENT);
      leftPanel.setAlignmentY(LEFT_ALIGNMENT);
      leftPanel.add(roomsLabel);
      leftPanel.add(roomsListPanel);
      leftPanel.add(selectedRoomLabel);
      leftPanel.add(roomInfoTable);
      leftPanel.setBackground(Color.BLUE);
      roomsListPanel.setBackground(Color.RED);
      
      rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
      rightPanel.setAlignmentX(LEFT_ALIGNMENT);
      rightPanel.add(checkInLabel);
      rightPanel.add(checkOutLabel);
      
      roomPanel = new JPanel();
      roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.LINE_AXIS));
      roomPanel.add(leftPanel);
      roomPanel.add(rightPanel);
      
      resPanel = new JPanel();
      resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.LINE_AXIS));
      resPanel.add(resLeftPanel);
      resPanel.add(resRightPanel);
      resPanel.setVisible(false);
      
      botPanel = new JPanel();
      botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.LINE_AXIS));
      botPanel.add(roomPanel);
      botPanel.add(resPanel);
      
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      add(topPanel);
      add(botPanel);
      
   }
   
   private void createComboBox()
   {
      String[] roomList = {"1", "2", "3", "4", "5"};
      roomsCombo = new JComboBox(roomList);
   }
   
   private void createRoomInfoTable()
   {
      TableModel model = new DefaultTableModel(
            DatabaseConstants.ROOMS_ATTRS, 5);
      roomInfoTable = new JTable(model);
      roomInfoTable.setFillsViewportHeight(true);
   }
}
