package owner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Occupancy {

	static private JTabbedPane OccupancyTab;
	static private JTable OccupancyTable;
	static private JTextField OccStartText;
	static private JTextField OccStopText;
	static private int strutSize = 15;

	static public JTabbedPane createOccupancyTab() {
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();

		OccStartText = new JTextField();
		OccStartText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				occupancyAction();
			}
		});
		OccStopText = new JTextField();
		OccStopText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				occupancyAction();
			}
		});

		hBox.add(new JLabel("Start Date:"));
		hBox.add(OccStartText);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(new JLabel("Stop Date:"));
		hBox.add(OccStopText);
		hBox.add(Box.createHorizontalStrut(strutSize));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		OccupancyTab = new JTabbedPane();
		OccupancyTab.add(vBox);
		return OccupancyTab;
	}

	static private void occupancyAction() {
		String start = OccStartText.getText();
		String stop = OccStopText.getText();
		System.out.println("User input->" + start + " " + stop);
		if (start.length() != 0 && stop.length() != 0) {
			/*
			 * PopPanel pp = new PopPanel(Owner.getOccupancy(start,stop),
			 * Owner.OccupancyColV); pp.addMouseAction(new MouseAdapter(){
			 * public void mouseClicked(MouseEvent e) { JTable target =
			 * (JTable)e.getSource(); int row = target.getSelectedRow(); String
			 * room = (String) target.getValueAt(row, 0); new
			 * OwnerPanel.ReservationListPanel
			 * (room,start,stop).setVisible(true); } }); pp.setVisible(true);
			 */
		} else if (OccStartText.getText().length() != 0) {
			/*
			 * new PopPanel(Owner.getOccupancy(OccStartText.getText()),Owner.
			 * OccupancyColV).setVisible(true);
			 */
		} else
			System.out.print("POOP\n");

	}

	/*
	 * private JTable createOccupancyTable() { //JTable ret = new JTable(new
	 * DefaultTableModel(Owner.OccupancyCol, 12)); JTable ret = new
	 * JTable(Owner.getOccupancy("", ""),Owner.OccupancyColV);
	 * ret.addMouseListener(new MouseAdapter(){ public void
	 * mouseClicked(MouseEvent e) { JTable target = (JTable)e.getSource(); int
	 * row = target.getSelectedRow(); new
	 * OwnerPanel.ReservationListPanel("2","29-MAR-2010"
	 * ,"01-DEC-2020").setVisible(true); } }); return ret; }
	 */

}
