package calpoly.owner;
/* 
 * @author Matthew Tondreau (mmtondre) 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import calpoly.DatabaseHandle;
import calpoly.OwnerPanel;

public class Reservations {
	static private JTextField ReservationRoomNameText;
	static private JTextField ReservationStartText;
	static private JTextField ReservationStopText;
	static private JTable ReservationsTable;
	static private DefaultTableModel model;
	static private final int strutSize = 15;
	
	public static Box createReservationsTab(DatabaseHandle handle) {
		ReservationController.setHandle(handle);
		
		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		try {
			ReservationsTable = createReservationsTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

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
		

		return vBox;
	}
	static private void reservationsAction() {
		String room = ReservationRoomNameText.getText();
		String start = ReservationStartText.getText();
		String stop = ReservationStopText.getText();
		System.out.println("User Input->"+room +" "+ start +" "+ stop);
		try {
			model.setDataVector(ReservationController.getReservations(start,stop,room), ReservationController.ReservationColNameV);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static private JTable createReservationsTable() throws SQLException {
		model = new DefaultTableModel(ReservationController.getReservations("","",""),ReservationController.ReservationColNameV);
		JTable ret = new JTable(model);
		ret.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				String name = (String)target.getValueAt(row, 0); /*TODO: MAGIC */
				try {
					new OwnerPanel.ReservationDetailedPopup(name).setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		return ret;
	}
	
	
}
