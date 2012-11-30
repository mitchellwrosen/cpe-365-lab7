package calpoly.owner;

/* 
 * @author Matthew Tondreau (mmtondre) 
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import calpoly.DatabaseHandle;
import calpoly.OwnerPanel;

public class Occupancy {

	
	static private JTextField OccStartText;
	static private JTextField OccStopText;
	static private int strutSize = 15;
	static private final double MaxComponentHeight = 1.9;
	static private final int MaxComponentWidth = 120;
	static public Box createOccupancyTab(DatabaseHandle handle) {
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		OccupancyController.setHandle(handle);

		OccStartText = new JTextField();
		OccStartText.setMaximumSize(new Dimension(MaxComponentWidth,(int)(MaxComponentHeight*OccStartText.getFont().getSize())));
		OccStartText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				occupancyAction();
			}
		});
		OccStopText = new JTextField();
		OccStopText.setMaximumSize(new Dimension(MaxComponentWidth,(int)(MaxComponentHeight*OccStopText.getFont().getSize())));
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
		
		return vBox;
	}

	static private void occupancyAction() {
		String start = OccStartText.getText();
		String stop = OccStopText.getText();
		System.out.println("User input->" + start + " " + stop);

		try {
			if (start.length() != 0 && stop.length() != 0) {
				new ReservationListFrame(start,stop).setVisible(true);
			} else if (start.length() != 0) {
				new ReservationFrame(start).setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* For single date entries */
	static class ReservationFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		static final private int roomNameColPos = 0;
		private JPanel panel;
		private JTable table;
		private final String date; 
		public ReservationFrame(String date) throws SQLException {
			this.date=date;
			panel = new JPanel();
			Box vBox = Box.createVerticalBox();
			table = new JTable(OccupancyController.getOccupancy(date), OccupancyController.OccupancyColV);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Action(e);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			this.setTitle(date);
			vBox.add(new JScrollPane(table));
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			this.add(panel);
			pack();
		}
		/* Display detailed information about any selected reservation */
		public void Action(MouseEvent e) throws SQLException {
			JTable target = (JTable)e.getSource();
			int row = target.getSelectedRow();
			/* Display Selected Rows detailed info */
			new OwnerPanel.ReservationDetailedPopup((String)target.getValueAt(row, roomNameColPos),date).setVisible(true);
		}
	}

	/* For two date entries */
	static class ReservationListFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		static final private int roomNameColPos = 0;
		private JPanel panel;
		private JTable table;
		private final String start;
		private final String stop;
		public ReservationListFrame( String start, String stop) throws SQLException {
			this.start=start;
			this.stop=stop;
			panel = new JPanel();
			Box vBox = Box.createVerticalBox();
			table = new JTable(OccupancyController.getOccupancy(start, stop), OccupancyController.OccupancyColV);
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Action(e);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			this.setTitle(start+" - "+stop);
			vBox.add(new JScrollPane(table));
			panel.add(vBox);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			this.add(panel);
			pack();

		}
		
		private void Action( MouseEvent e ) throws SQLException {
			JTable target = (JTable)e.getSource();
			int row = target.getSelectedRow();
			/* Display Selected rows list of reservations in that interval */
			new OwnerPanel.ReservationListPopup((String)target.getValueAt(row, roomNameColPos),start, stop).setVisible(true);
		}
	}
	

	
}
