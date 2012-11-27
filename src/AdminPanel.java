import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
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

   private JPanel labelsPanel;
   private JLabel statusLabel;
   private JLabel roomsLabel;
   private JLabel reservationsLabel;

   private JPanel buttonsPanel;
   private JButton clearButton;
   private JButton loadButton;
   private JButton destroyButton;

   private JTabbedPane tablesTabbedPane;
   private JTable roomsTable;
   private JTable reservationsTable;

   public AdminPanel(final DatabaseHandle handle) {
      assert(handle != null);
      this.handle = handle;

      statusLabel = new JLabel("Status: ");
      reservationsLabel = new JLabel("Reservations: ");
      roomsLabel = new JLabel("Rooms: ");

      updateStatus();

      clearButton = new JButton("Clear");
      clearButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            handle.executeStatement("DELETE FROM " +
                  DatabaseConstants.ROOMS_TABLENAME);
            handle.executeStatement("DELETE FROM " +
                  DatabaseConstants.RESERVATIONS_TABLENAME);

            updateStatus();
         }
      });

      loadButton = new JButton("Load");
      loadButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Integer numRooms = InnDatabaseUtils.GetNumRooms(handle);
            Integer numReservations = InnDatabaseUtils.GetNumReservations(
                  handle);

            if (numRooms == null || numReservations == null) {
               InnDatabaseUtils.CreateRoomsTable(handle);
               InnDatabaseUtils.CreateReservationsTable(handle);

               InnDatabaseUtils.PopulateRoomsTable(handle);
               InnDatabaseUtils.PopulateReservationsTable(handle);
            }

            clearButton.setEnabled(true);
            destroyButton.setEnabled(true);
            tablesTabbedPane.setEnabled(true);
            updateStatus();
         }
      });

      destroyButton = new JButton("Destroy");
      destroyButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            handle.executeStatement("DROP TABLE " +
                  DatabaseConstants.RESERVATIONS_TABLENAME);
            handle.executeStatement("DROP TABLE " +
                  DatabaseConstants.ROOMS_TABLENAME);

            clearButton.setEnabled(false);
            destroyButton.setEnabled(false);
            tablesTabbedPane.setEnabled(false);
            updateStatus();
         }
      });

      roomsTable = createRoomsJTable();
      reservationsTable = createReservationsJTable();

      tablesTabbedPane = new JTabbedPane();
      tablesTabbedPane.addTab("Rooms", new JScrollPane(roomsTable));
      tablesTabbedPane.addTab("Reservations",
                              new JScrollPane(reservationsTable));
      tablesTabbedPane.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            int index = ((JTabbedPane) e.getSource()).getSelectedIndex();

            if (index == TAB_INDEX_ROOMS)
               populateRoomsJTable();
            else
               populateReservationsJTable();

            updateStatus();
         }
      });

      labelsPanel = new JPanel();
      labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.LINE_AXIS));
      labelsPanel.add(statusLabel);
      labelsPanel.add(Box.createRigidArea(new Dimension(15, 0)));
      labelsPanel.add(roomsLabel);
      labelsPanel.add(Box.createRigidArea(new Dimension(15, 0)));
      labelsPanel.add(reservationsLabel);

      buttonsPanel = new JPanel();
      buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
      buttonsPanel.add(clearButton);
      buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
      buttonsPanel.add(loadButton);
      buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
      buttonsPanel.add(destroyButton);

      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      add(labelsPanel);
      add(buttonsPanel);
      add(tablesTabbedPane);
   }

   private void updateStatus() {
      Integer numRooms = InnDatabaseUtils.GetNumRooms(handle);
      Integer numReservations = InnDatabaseUtils.GetNumReservations(handle);

      if (numRooms == null || numReservations == null) {
         statusLabel.setText("Status: no database");
         roomsLabel.setText("Rooms: N/A");
         reservationsLabel.setText("Reservations: N/A");
      } else {
         roomsLabel.setText("Rooms: " + numRooms);
         reservationsLabel.setText("Reservations: " + numReservations);
         if (numRooms == 0 && numReservations == 0)
            statusLabel.setText("Status: empty");
         else
            statusLabel.setText("Status: full");
      }
   }

   private JTable createRoomsJTable() {
      TableModel model = new DefaultTableModel(
            DatabaseConstants.ROOMS_ATTRS, 20);
      JTable table = new JTable(model);

      table.setFillsViewportHeight(true);
      return table;
   }

   private JTable createReservationsJTable() {
      TableModel model = new DefaultTableModel(
            DatabaseConstants.RESERVATIONS_ATTRS, 20);
      JTable table = new JTable(model);

      table.setFillsViewportHeight(true);
      return table;
   }

   private void populateRoomsJTable() {
      DefaultTableModel model = (DefaultTableModel) roomsTable.getModel();
      model.setRowCount(0);

      try {
         ResultSet results = handle.executeQuery("SELECT * FROM Rooms");

         while (results.next()) {
            model.addRow(new Object[]{
                  results.getInt(0),      // Id
                  results.getString(1),   // Name
                  results.getInt(2),      // NumBeds
                  results.getString(3),   // BedType
                  results.getInt(4),      // MaxOccupancy
                  results.getInt(5),      // Price
                  results.getString(6)    // Decor
            });
         }
      } catch (java.sql.SQLException e) {
         System.out.println(e);
      }
   }

   private void populateReservationsJTable() {
      DefaultTableModel model =
            (DefaultTableModel) reservationsTable.getModel();
      model.setRowCount(0);

      try {
         ResultSet results = handle.executeQuery("SELECT * FROM Reservations");

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
      } catch (java.sql.SQLException e) {
         System.out.println(e);
      }

   }
}
