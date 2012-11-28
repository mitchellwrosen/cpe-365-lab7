import javax.swing.JButton;
import javax.swing.JPanel;

public class OwnerPanel extends JPanel {
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
   //Label: Rooms
   private JLabel roomsLabel;
   //Field: clickable list of all rooms

   
   public OwnerPanel(DatabaseHandle handle) {
      this.handle = handle;
      
      
         //Middle Panel
         //Label: Selected Room
         //Text field: info on the room selected from field above
         //(Under text field) Button: Check Availability
      
         //Right Panel (Check Availability)
         //Label: Select Check-in Date (top of panel)
         //Spinner(All horizontal): months
         //Spinner: days
         //Spinner: years
      
         //Label: Select Check-out Date (under check-in Fields)
         //Spinner(All horizontal): months
         //Spinner: days
         //Spinner: years
      
         //(Under Check out dates)Text field: info on selected room's availability for each night
         //Button (At bottom of panel): Place a Reservation (lights up when all dates are valid)
   
      //Reservations Buttons & Fields
         //Left Panel
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
   }
}
