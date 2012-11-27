import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class InnReservations {
   public static DatabaseHandle handle = null;

   public static void main(String args[]) {
      String server = null;
      String username = null;
      String password = null;

      // Read ServerSettings.txt.
      try {
         FileInputStream in = new FileInputStream("ServerSettings.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(in));

         server = br.readLine();
         username = br.readLine();
         password = br.readLine();

         br.close();
      } catch (Exception e) {
         System.out.println("Error reading ServerSettings.txt.");
         System.exit(1);
      }

      // Load the database driver.
      try {
         System.out.println("Loading driver.");
         Class.forName("oracle.jdbc.OracleDriver");
      } catch (ClassNotFoundException e) {
         System.out.println("Driver not found.");
         System.exit(1);
      }

      // Establish connection.
      try {
         System.out.println("Establishing connection.");
         handle = new DatabaseHandle(
               DriverManager.getConnection(server, username, password));
      } catch (Exception e) {
         System.out.println("Could not open connection to database.");
         System.exit(1);
      }

      // Check for existence of Rooms and Reservations tables.
      if (handle.executeQuery("SELECT * FROM " +
            DatabaseConstants.ROOMS_TABLENAME) == null) {
         handle.executeStatement(DatabaseConstants.ROOMS_TABLE_CREATE);
      }

      if (handle.executeQuery("SELECT * FROM " +
            DatabaseConstants.RESERVATIONS_TABLENAME) == null) {
         handle.executeStatement(DatabaseConstants.RESERVATIONS_TABLE_CREATE);
      }

      System.out.println("Showing GUI.");
      createAndShowGUI();
   }

   public static void createAndShowGUI() {
      JFrame frame = new JFrame("Inn Reservations");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel adminPanel = new AdminPanel(handle);
      JPanel ownerPanel = new OwnerPanel(handle);
      JPanel guestPanel = new GuestPanel(handle);

      JTabbedPane tabs = new JTabbedPane();
      tabs.addTab("Admin", adminPanel);
      tabs.addTab("Owner", ownerPanel);
      tabs.addTab("Guest", guestPanel);

      frame.setSize(500, 500);
      frame.add(tabs);
      frame.pack();
      frame.setVisible(true);
   }
}
