import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;

public class GuestPanel extends JPanel {
   private DatabaseHandle handle;

   //buttons across top
   //Panel: TopPanel
   private JPanel topPanel;
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
   
   //Reservations Buttons & Fields (If not using above vars)
   private JPanel resLeftPanel;
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
      
      leftPanel = new JPanel();
      rightPanel = new JPanel();
      resLeftPanel = new JPanel();
      resRightPanel = new JPanel();
      
      roomsAndRatesButton = new JButton("Rooms and Rates");
      roomsAndRatesButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            leftPanel.setVisible(true);
            rightPanel.setVisible(true);
            resLeftPanel.setVisible(false);
            resRightPanel.setVisible(false);
         }
      });
      
      reservationsButton = new JButton("Reservations");
      reservationsButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            leftPanel.setVisible(false);
            rightPanel.setVisible(false);
            resLeftPanel.setVisible(true);
            resRightPanel.setVisible(true);
         }
      });
      
      topPanel = new JPanel();
      topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
      topPanel.add(roomsAndRatesButton);
      topPanel.add(reservationsButton);
      topPanel.setVisible(true);
      
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      add(topPanel);
   }
}
