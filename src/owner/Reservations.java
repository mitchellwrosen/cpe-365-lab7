package owner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import calpoly.OwnerPanel;

public class Reservations {
	static private JTabbedPane ReservationsTab;
	static private JTextField ReservationRoomNameText;
	static private JTextField ReservationStartText;
	static private JTextField ReservationStopText;
	static private JTable ReservationsTable;
	static private final int strutSize = 15;
	
	public static JTabbedPane createReservationsTab() {
		ReservationsTab = new JTabbedPane();
		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		ReservationsTable = createReservationsTable();

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
		ReservationsTab.add(vBox);

		return ReservationsTab;
	}
	static private void reservationsAction() {
		System.out.println("User Input->"+ReservationRoomNameText.getText() +" "
				+ ReservationStartText.getText() +" "
				+ ReservationStopText.getText());
	}
	
	static private JTable createReservationsTable() {
		JTable ret = new JTable(Owner.getReservations("","",""),Owner.ReservationColNameV);
		ret.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				//new OwnerPanel.DetailedReservationPanel("3").setVisible(true);
			}
		});
		return ret;
	}

}
