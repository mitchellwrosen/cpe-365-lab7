package calpoly;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Charlie
 */
public class GuestPanel extends JPanel
{
    private DatabaseHandle handle;
    private int checkInMonth;
    private int checkInDay;
    private int checkInYear;
    private int checkOutMonth;
    private int checkOutDay;
    private int checkOutYear;
    private String room;
    private double rate;
    ArrayList<String> freeRooms;
    ArrayList<Double> roomRate;
            
    /**
     * Creates new form GuestPanel
     */
    public GuestPanel(DatabaseHandle handle) {
      this.handle = handle;
      
      initComponents();
    }

    protected DatabaseHandle getHandle()
    {
        return handle;
    }
    
    protected String getRoomId()
    {
        return roomCodeLabel.getText();
    }
    
    protected String getAvailableRoom()
    {
        return (String)roomComboBox.getSelectedItem();
    }
    
    protected String getRoom()
    {
        return room;
    }
    
    protected void setRoom(String newRoom)
    {
        room = newRoom;
    }

    protected void setRate(double newRate)
    {
        rate = newRate;
    }
    
    protected double getPrice()
    {
        return Double.parseDouble(priceLabel.getText());
    }
    
    protected void updateCheckIn(int month, int day, int year)
    {
        checkInMonth = month;
        checkInDay = day;
        checkInYear = year;
    }
    
    protected void updateCheckOut(int month, int day, int year)
    {
        checkOutMonth = month;
        checkOutDay = day;
        checkOutYear = year;
    }
    
    public boolean checkDates(int month, int day, int year, JLabel datesLabel)
    {
        if (month == 2 && day > 28)
        {
            if (day == 29  && (year % 4) != 0)
            {
                datesLabel.setText("This isn't a leap year!");
                return false;
            }
            else if (day > 29)
            {
                datesLabel.setText("Invalid check-in day.");
                return false;
            }
        }
                
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) && day > 31)
        {
            return false;
        }
        
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
        {
            return false;
        }

        return true;
    }
    
    private boolean checkInBeforeCheckOut(int checkDay, int checkMon, int checkYear,
            int checkOutMon, int checkOutDay, int checkOutYear)
    {
        if (checkMon > checkOutMon && checkYear == checkOutYear)
        {
            return false;
        }
        else if (checkDay > checkOutDay && checkMon == checkOutMon)
        {
            return false;
        }
        else if(checkYear > checkOutYear)
        {
            return false;
        }
        else if (checkMon == checkOutMon && checkYear == checkOutYear && 
                checkDay == checkOutDay)
        {
            return false;
        }
        
        return true;
    }
    
    private String createDate(int numMonth, int numDay, int numYear)
    {
        String month = Integer.toString(numMonth);
        String day = Integer.toString(numDay);
        String year = Integer.toString(numYear);
        
        if (numMonth < 10)
        {
          //  month = "0" + month;
        }
                
        return "TO_DATE(" + "'"+ month + "-" + day + "-" + year + "'" + " , 'MM-DD-YYYY')";
    }
    
    double truncateDouble(double number)
    {
        double result = number;
        String arg = "" + number;
        int idx = arg.indexOf('.');
        if (idx!=-1) {
            if (arg.length() > idx+2) {
                arg = arg.substring(0,idx+2+1);
                result  = Double.parseDouble(arg);
            }
        }
        return result;
    }
    
    private double getRate(double basePrice)
    {
        rate = 0;
        Calendar c = Calendar.getInstance();
        int dayOfWeek, dayOfMonth;
        c.set(checkInYear, checkInMonth-1, checkInDay);

        while(checkInBeforeCheckOut(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1,
                c.get(Calendar.YEAR), checkOutMonth, checkOutDay, checkOutYear))
        {
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        
            if ((dayOfMonth == 1 && c.get(Calendar.MONTH)+1 == 1)
                    || (dayOfMonth == 4 && c.get(Calendar.MONTH)+1 == 7)
                    || (dayOfMonth == 6 && c.get(Calendar.MONTH)+1 == 9)
                    || (dayOfMonth == 30 && c.get(Calendar.MONTH)+1 == 10))
            {
                 rate = truncateDouble(basePrice * 1.25);
                 return rate;
            }

            else if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
            {
                if (basePrice* 1.1 > rate)
                {
                    rate = truncateDouble(basePrice * 1.1);
                }
            }
            
            else if (rate < basePrice)
            {
                    rate = basePrice;
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return rate;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        roomComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        numBedsLabel = new javax.swing.JLabel();
        roomCodeLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bedTypeLabel = new javax.swing.JLabel();
        maxOccupantsLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        decorLabel = new javax.swing.JLabel();
        checkAvailabilityButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        checkInYearSpinner = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        checkOutYearSpinner = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        reservationTable = new javax.swing.JTable();
        reservationButton = new javax.swing.JButton();
        checkInDaySpinner = new javax.swing.JSpinner();
        checkOutDaySpinner = new javax.swing.JSpinner();
        checkButton = new javax.swing.JButton();
        checkInMonthSpinner = new javax.swing.JSpinner();
        checkOutMonthSpinner = new javax.swing.JSpinner();
        validDatesLabel = new javax.swing.JLabel();

        jLabel1.setText("Select a Room:");

        ArrayList<String> listRooms = new ArrayList<String>();

        handle.createStatement();
        final ResultSet results = handle.executeQuery("SELECT Name FROM Rooms");

        try
        {
            while (results.next())
            {
                listRooms.add(results.getString("Name"));

            }
        }
        catch (java.sql.SQLException e)
        {
            System.out.println(e);
        }
        roomComboBox.setModel(new javax.swing.DefaultComboBoxModel(listRooms.toArray()));
        roomComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                roomComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Room Information:");

        jLabel3.setText("Room Code:");

        jLabel4.setText("Number of Beds:");

        numBedsLabel.setText("NumBeds");

        roomCodeLabel.setText("Room Code");

        jLabel7.setText("Bed Type:");

        jLabel8.setText("Max Occupants:");

        jLabel9.setText("Price:");

        jLabel10.setText("Decor:");

        bedTypeLabel.setText("BedType");

        maxOccupantsLabel.setText("Max Occ");

        priceLabel.setText("Price");

        decorLabel.setText("Decor");

        checkAvailabilityButton.setText("Check Availability");
        checkAvailabilityButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                checkAvailabilityButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numBedsLabel)
                            .addComponent(bedTypeLabel)
                            .addComponent(maxOccupantsLabel)
                            .addComponent(priceLabel)
                            .addComponent(roomCodeLabel)
                            .addComponent(decorLabel)))
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkAvailabilityButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roomComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(366, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(roomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(roomCodeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(numBedsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(bedTypeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(maxOccupantsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(priceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(decorLabel))
                .addGap(18, 18, 18)
                .addComponent(checkAvailabilityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
        );

        String firstName = (String)roomComboBox.getSelectedItem();

        handle.createStatement();
        final ResultSet firstResults = handle.executeQuery("SELECT * FROM Rooms");

        try
        {
            while (firstResults.next())
            {
                if (firstResults.getString("Name").equals(firstName))
                {
                    roomCodeLabel.setText(firstResults.getString("Id"));
                    numBedsLabel.setText(firstResults.getString("NumBeds"));
                    bedTypeLabel.setText(firstResults.getString("BedType"));
                    maxOccupantsLabel.setText(firstResults.getString("MaxOccupancy"));
                    priceLabel.setText(firstResults.getString("Price"));
                    decorLabel.setText(firstResults.getString("Decor"));
                }
            }

        } catch (java.sql.SQLException e)
        {
            System.out.println(e);
        }

        jTabbedPane1.addTab("Rooms and Rates", jPanel1);

        jLabel5.setText("Check In");

        jLabel6.setText("Check Out");

        jLabel11.setText("Mon:");

        jLabel12.setText("Mon:");

        jLabel13.setText("Day:");

        checkInYearSpinner.setModel(new javax.swing.SpinnerListModel(new String[] {"2009", "2010", "2011", "2012", "2013", "2014"}));
        checkInYearSpinner.setMaximumSize(new java.awt.Dimension(47, 20));

        jLabel14.setText("Year:");

        jLabel15.setText("Day:");

        jLabel16.setText("Year:");

        checkOutYearSpinner.setModel(new javax.swing.SpinnerListModel(new String[] {"2009", "2010", "2011", "2012", "2013", "2014"}));

        reservationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String []
            {
                "Room", "Rate"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        reservationTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        reservationTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(reservationTable);
        reservationTable.getColumnModel().getColumn(0).setResizable(false);
        reservationTable.getColumnModel().getColumn(1).setResizable(false);

        reservationButton.setText("Place Reservation");
        reservationButton.setEnabled(false);
        reservationButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                reservationButtonActionPerformed(evt);
            }
        });

        checkInDaySpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        checkOutDaySpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        checkButton.setText("Check Dates");
        checkButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                checkButtonActionPerformed(evt);
            }
        });

        checkInMonthSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        checkOutMonthSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        validDatesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        validDatesLabel.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(checkInYearSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(checkInDaySpinner)
                                    .addComponent(checkInMonthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(validDatesLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel16))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(checkOutYearSpinner)
                                            .addComponent(checkOutDaySpinner)
                                            .addComponent(checkOutMonthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(reservationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(checkInYearSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(checkInMonthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(checkInDaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(checkOutMonthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(checkOutDaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(checkOutYearSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(checkButton)))
                            .addComponent(validDatesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reservationButton)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jTabbedPane1.addTab("Reservations", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void checkAvailabilityButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkAvailabilityButtonActionPerformed
    {//GEN-HEADEREND:event_checkAvailabilityButtonActionPerformed
        CheckAvailability checkAvail = new CheckAvailability(null, true, this);
        checkAvail.setVisible(true);
    }//GEN-LAST:event_checkAvailabilityButtonActionPerformed

    private void reservationButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_reservationButtonActionPerformed
    {//GEN-HEADEREND:event_reservationButtonActionPerformed
        if (reservationTable.getSelectedRow() == -1)
        {
            validDatesLabel.setText("Please select a room");
            return;
        }
        
        room = freeRooms.get(reservationTable.getSelectedRow());
        rate = roomRate.get(reservationTable.getSelectedRow());
        
        CompleteReservation reservation = new CompleteReservation(null, true, this);
        reservation.setVisible(true);
    }//GEN-LAST:event_reservationButtonActionPerformed

    private void roomComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_roomComboBoxActionPerformed
    {//GEN-HEADEREND:event_roomComboBoxActionPerformed
        String name = (String)roomComboBox.getSelectedItem();
        
        handle.createStatement();
        final ResultSet results = handle.executeQuery("SELECT * FROM Rooms");
        
        try {
            while (results.next()) {
                if (results.getString("Name").equals(name))
                {
                    roomCodeLabel.setText(results.getString("Id"));
                    numBedsLabel.setText(results.getString("NumBeds"));
                    bedTypeLabel.setText(results.getString("BedType"));
                    maxOccupantsLabel.setText(results.getString("MaxOccupancy"));
                    priceLabel.setText(results.getString("Price"));
                    decorLabel.setText(results.getString("Decor"));
                }
            }

        } catch (java.sql.SQLException e) {
                  System.out.println(e);
        }
    }//GEN-LAST:event_roomComboBoxActionPerformed

    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkButtonActionPerformed
    {//GEN-HEADEREND:event_checkButtonActionPerformed
        boolean setReservation = true;
        freeRooms = new ArrayList<String>();
        roomRate = new ArrayList<Double>();
        
        checkInMonth = (Integer)checkInMonthSpinner.getValue();
        checkInDay = (Integer)checkInDaySpinner.getValue();
        checkInYear = Integer.parseInt((String)checkInYearSpinner.getValue());
        checkOutMonth = (Integer)checkOutMonthSpinner.getValue();
        checkOutDay = (Integer)checkOutDaySpinner.getValue();
        checkOutYear = Integer.parseInt((String)checkOutYearSpinner.getValue());
        rate = 0;
        
        final DefaultTableModel model = (DefaultTableModel)reservationTable.getModel();
        model.setRowCount(0);
        
        if (!checkInBeforeCheckOut(checkInDay, checkInMonth, checkInYear, checkOutMonth, checkOutDay, checkOutYear))
        {
            validDatesLabel.setText("Check-out must be after check-in.");
            reservationButton.setEnabled(false);
            return;
        }
        
        if (!checkDates(checkInMonth, checkInDay, checkInYear, validDatesLabel)
            || !checkDates(checkOutMonth, checkOutDay, checkOutYear, validDatesLabel))
        {
            reservationButton.setEnabled(false);
            return;
        }

        validDatesLabel.setText("");
        String checkInDate = createDate(checkInMonth, checkInDay, checkInYear);
        String checkOutDate = createDate(checkOutMonth, checkOutDay, checkOutYear);
        
        //Query to check if each room is available during the selected stay
        handle.createStatement();
        ResultSet resultSet =
                handle.executeQuery(
                    ("(SELECT distinct Name, Price from Rooms) MINUS (" +
                    "SELECT R.Name, R.Price from Rooms R, Reservations Res " + 
                    "where Res.RoomId = R.Id " + 
                    "AND (" + 
                    "(Res.CheckInDate > " + checkInDate + "AND Res.CheckOutDate < " + checkOutDate + ")"
                    + "OR (Res.CheckInDate < " + checkInDate + "AND Res.CheckOutDate > " + checkOutDate + ")"
                    + "OR (Res.CheckInDate < " + checkInDate + "AND Res.CheckOutDate < " + checkOutDate + " AND Res.CheckOutDate > " + checkInDate + ")"
                    + "OR (Res.CheckInDate > " + checkInDate + "AND Res.CheckOutDate > " + checkOutDate + " AND Res.CheckInDate < " + checkOutDate + ")"
                    + ")" +
                    " GROUP BY R.Name, R.Price)"
                ));
        
        try
        {
            //The occupied rooms for the duration of the selected period
            while (resultSet.next())
            {
                freeRooms.add(resultSet.getString("Name"));
                roomRate.add(resultSet.getDouble("Price"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
        
        for(int i = 0; i < freeRooms.size(); i++)
        {
            model.addRow(new Object[] {freeRooms.get(i), roomRate.get(i)});
        }
        
        if(setReservation)
        {
            reservationButton.setEnabled(true);
        }
        else
        {
            reservationButton.setEnabled(false);
        }
    }//GEN-LAST:event_checkButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bedTypeLabel;
    private javax.swing.JButton checkAvailabilityButton;
    private javax.swing.JButton checkButton;
    private javax.swing.JSpinner checkInDaySpinner;
    private javax.swing.JSpinner checkInMonthSpinner;
    private javax.swing.JSpinner checkInYearSpinner;
    private javax.swing.JSpinner checkOutDaySpinner;
    private javax.swing.JSpinner checkOutMonthSpinner;
    private javax.swing.JSpinner checkOutYearSpinner;
    private javax.swing.JLabel decorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel maxOccupantsLabel;
    private javax.swing.JLabel numBedsLabel;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JButton reservationButton;
    private javax.swing.JTable reservationTable;
    private javax.swing.JLabel roomCodeLabel;
    private javax.swing.JComboBox roomComboBox;
    private javax.swing.JLabel validDatesLabel;
    // End of variables declaration//GEN-END:variables
}
