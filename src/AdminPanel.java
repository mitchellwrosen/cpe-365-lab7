import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminPanel extends JPanel {
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
      DefaultTableModel model = new DefaultTableModel(
            DatabaseConstants.ROOMS_ATTRS, 20);
      JTable table = new JTable(model);

      table.setFillsViewportHeight(true);
      return table;
   }

   private JTable CreateReservationsTable() {
      DefaultTableModel model = new DefaultTableModel(
            DatabaseConstants.RESERVATIONS_ATTRS, 20);
      JTable table = new JTable(model);

      table.setFillsViewportHeight(true);
      return table;
   }
}
