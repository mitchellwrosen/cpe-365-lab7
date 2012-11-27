import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

public class AdminPanel extends JPanel {
   private static int TAB_INDEX_ROOMS = 0;
   private static int TAB_INDEX_RESERVATIONS = 1;

   private DatabaseHandle handle;

   private JLabel statusLabel;
   private JLabel roomsLabel;
   private JLabel reservationsLabel;

   private JButton clearButton;
   private JButton reloadButton;
   private JButton destroyButton;

   private JTable roomsTable;
   private JTable reservationsTable;

   public AdminPanel(DatabaseHandle handle) {
      //assert(handle != null);
      this.handle = handle;

      statusLabel = new JLabel("Status: ");
      reservationsLabel = new JLabel("Reservations: ");
      roomsLabel = new JLabel("Rooms: ");

      UpdateStatus();

      clearButton = new JButton("Clear");
      reloadButton = new JButton("Reload");
      destroyButton = new JButton("Destroy");

      roomsTable = CreateRoomsTable();
      reservationsTable = CreateReservationsTable();

      JTabbedPane tabs = new JTabbedPane();
      tabs.addTab("Rooms", new JScrollPane(roomsTable));
      tabs.addTab("Reservations", new JScrollPane(reservationsTable));
      tabs.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            int index = ((JTabbedPane) e.getSource()).getSelectedIndex();

            if (index == TAB_INDEX_ROOMS)
               PopulateRoomsTable();
            else
               PopulateReservationsTable();
         }
      });

      add(statusLabel);
      add(clearButton);
      add(reloadButton);
      add(destroyButton);
      add(tabs);
   }

   private void UpdateStatus() {
      //ResultSet results = handle.ExecuteQuery("SELECT * FROM Rooms");

      statusLabel.setText("Status: " + "full");
      roomsLabel.setText("Rooms: " + 5);
      reservationsLabel.setText("Reservations: " + 3);
   }

   private JTable CreateRoomsTable() {
      TableModel model = new DefaultTableModel(
            DatabaseConstants.ROOMS_ATTRS, 20);
      JTable table = new JTable(model);

      table.setFillsViewportHeight(true);
      return table;
   }

   private JTable CreateReservationsTable() {
      TableModel model = new DefaultTableModel(
            DatabaseConstants.RESERVATIONS_ATTRS, 20);
      JTable table = new JTable(model);

      table.setFillsViewportHeight(true);
      return table;
   }

   private void PopulateRoomsTable() {
      DefaultTableModel model = (DefaultTableModel) roomsTable.getModel();
      model.setRowCount(0);

      try {
         ResultSet results = handle.ExecuteQuery("SELECT * FROM Rooms");

         boolean more = results.next();
         while (more) {
            model.addRow(new Object[]{
                  results.getInt(0),      // Id
                  results.getString(1),   // Name
                  results.getInt(2),      // NumBeds
                  results.getString(3),   // BedType
                  results.getInt(4),      // MaxOccupancy
                  results.getInt(5),      // Price
                  results.getString(6)    // Decor
            });

            more = results.next();
         }
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   private void PopulateReservationsTable() {
      DefaultTableModel model =
            (DefaultTableModel) reservationsTable.getModel();
      model.setRowCount(0);

      try {
         ResultSet results = handle.ExecuteQuery("SELECT * FROM Reservations");

         boolean more = results.next();
         while (more) {
            model.addRow(new Object[]{
                  results.getInt(0),      // Code
                  results.getString(1),   // RoomId
                  results.getDate(2),     // CheckInDate
                  results.getDate(3),     // CheckOutDate
                  results.getFloat(4),    // Rate
                  results.getString(5),   // LastName
                  results.getString(6),   // FirstName
                  results.getInt(7),      // NumAdults
                  results.getInt(8)       // NumKids
            });

            more = results.next();
         }
      } catch (Exception e) {
         System.out.println(e);
      }

   }
}
