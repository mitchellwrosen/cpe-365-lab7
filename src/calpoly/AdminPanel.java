package calpoly;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.lang.Thread;
import java.lang.Runnable;

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
import javax.swing.SwingUtilities;
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

      clearButton = new JButton("Clear");
      clearButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new Thread(new Runnable() {
               public void run() {
                  setLabelTextOnEDT(statusLabel,
                                    "Status: clearing database");
                  setComponentsEnabledOnEDT(false, clearButton, loadButton,
                                            destroyButton, tablesTabbedPane);

                  handle.createStatement();
                  handle.executeStatement("DELETE FROM " +
                        DatabaseConstants.RESERVATIONS_TABLENAME);
                  handle.executeStatement("DELETE FROM " +
                        DatabaseConstants.ROOMS_TABLENAME);

                  updateStatus();
               }
            }).start();
         }
      });

      loadButton = new JButton("Load");
      loadButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new Thread(new Runnable() {
               public void run() {
                  setComponentsEnabledOnEDT(false, clearButton, loadButton,
                                            destroyButton, tablesTabbedPane);

                  setLabelTextOnEDT(statusLabel,
                                    "Status: determining database status");

                  Integer numRooms = InnDatabaseUtils.GetNumRooms(handle);
                  Integer numReservations = InnDatabaseUtils.GetNumReservations(
                        handle);

                  if (numRooms != null && numReservations != null &&
                        numRooms > 0 && numReservations > 0) {
                     setLabelTextOnEDT(statusLabel,
                                       "Status: full (already loaded)");

                     setComponentsEnabledOnEDT(true, clearButton, loadButton,
                                               destroyButton, tablesTabbedPane);
                  } else {
                     if (numRooms == null || numReservations == null) {
                        setLabelTextOnEDT(statusLabel,
                                          "Status: creating tables");

                        InnDatabaseUtils.CreateTables(handle);
                     }

                     setLabelTextOnEDT(statusLabel,
                                       "Status: populating tables");
                     InnDatabaseUtils.PopulateTables(handle);

                     updateStatus();
                  }
               }
            }).start();
         }
      });

      destroyButton = new JButton("Destroy");
      destroyButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new Thread(new Runnable() {
               public void run() {
                  setComponentsEnabledOnEDT(false, clearButton, loadButton,
                                            destroyButton, tablesTabbedPane);

                  setLabelTextOnEDT(statusLabel,
                                    "Status: dropping tables");

                  handle.createStatement();
                  handle.executeStatement("DROP TABLE " +
                        DatabaseConstants.RESERVATIONS_TABLENAME);
                  handle.executeStatement("DROP TABLE " +
                        DatabaseConstants.ROOMS_TABLENAME);

                  updateStatus();
               }
            }).start();
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

      updateStatus();

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

   private void setLabelTextOnEDT(final JLabel label, final String status) {
      assert(!EventQueue.isDispatchThread());

      try {
         SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
               label.setText(status);
            }
         });
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   private void setComponentsEnabledOnEDT(final boolean enabled,
                                          final Component... components) {
      try {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               for (Component component : components)
                  component.setEnabled(enabled);
            }
         });
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   private void updateStatus() {
      if (EventQueue.isDispatchThread()) {
         new Thread(new Runnable() {
            public void run() {
               updateStatus_();
            }
         });
      } else {
         updateStatus_();
      }
   }

   private void updateStatus_() {
      assert(!EventQueue.isDispatchThread());

      setLabelTextOnEDT(statusLabel,
                        "Status: determining database status");

      Integer numRooms = InnDatabaseUtils.GetNumRooms(handle);
      Integer numReservations = InnDatabaseUtils.GetNumReservations(handle);

      // Populate the currently selected table.
      if (tablesTabbedPane.getSelectedIndex() == TAB_INDEX_ROOMS)
         populateRoomsJTable();
      else
         populateReservationsJTable();


      if (numRooms == null || numReservations == null) {
         setLabelTextOnEDT(statusLabel, "Status: no database");
         setLabelTextOnEDT(roomsLabel, "Rooms: N/A");
         setLabelTextOnEDT(reservationsLabel, "Reservations: N/A");

         setComponentsEnabledOnEDT(true, loadButton);
      } else {
         setLabelTextOnEDT(roomsLabel, "Rooms: " + numRooms);
         setLabelTextOnEDT(reservationsLabel,
                           "Reservations: " + numReservations);

         if (numRooms == 0 && numReservations == 0) {
            setLabelTextOnEDT(statusLabel, "Status: empty");
            setComponentsEnabledOnEDT(true, loadButton, destroyButton,
                                      tablesTabbedPane);
         } else {
            setLabelTextOnEDT(statusLabel, "Status: full");

            setComponentsEnabledOnEDT(true, clearButton, loadButton,
                                      destroyButton, tablesTabbedPane);
         }
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
      if (EventQueue.isDispatchThread()) {
         new Thread(new Runnable() {
            public void run() {
               populateRoomsJTable_();
            }
         }).start();
      } else {
         populateRoomsJTable_();
      }
   }

   private void populateRoomsJTable_() {
      setLabelTextOnEDT(statusLabel, "Status: filling rooms table");

      final DefaultTableModel model = (DefaultTableModel) roomsTable.getModel();
      model.setRowCount(0);

      handle.createStatement();
      final ResultSet results = handle.executeQuery("SELECT * FROM Rooms");

      if (results != null) {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               try {
                  while (results.next()) {
                     model.addRow(new Object[]{
                           results.getString("Id"),
                           results.getString("Name"),
                           results.getInt("NumBeds"),
                           results.getString("BedType"),
                           results.getInt("MaxOccupancy"),
                           results.getInt("Price"),
                           results.getString("Decor")
                     });
                  }

                  statusLabel.setText("Status: done");
               } catch (java.sql.SQLException e) {
                  System.out.println(e);
               }
            }
         });
      }
   }

   private void populateReservationsJTable() {
      if (EventQueue.isDispatchThread()) {
         new Thread(new Runnable() {
            public void run() {
               populateReservationsJTable_();
            }
         }).start();
      } else {
         populateReservationsJTable_();
      }
   }

   private void populateReservationsJTable_() {
      setLabelTextOnEDT(statusLabel, "Status: filling reservations table");

      final DefaultTableModel model =
            (DefaultTableModel) reservationsTable.getModel();
      model.setRowCount(0);

      handle.createStatement();
      final ResultSet results = handle.executeQuery(
            "SELECT * FROM Reservations");

      if (results != null) {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               try {
                  while (results.next()) {
                     model.addRow(new Object[]{
                           results.getInt("Code"),
                           results.getString("RoomId"),
                           results.getDate("CheckInDate"),
                           results.getDate("CheckOutDate"),
                           results.getFloat("Rate"),
                           results.getString("LastName"),
                           results.getString("FirstName"),
                           results.getInt("NumAdults"),
                           results.getInt("NumKids")
                     });
                  }

                  statusLabel.setText("Status: done");
               } catch (java.sql.SQLException e) {
                  System.out.println(e);
               }
            }
         });
      }
   }
}
