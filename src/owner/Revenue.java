package owner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import calpoly.DatabaseHandle;

public class Revenue {
	static private JButton RevenueRevenueButton;
	static private JButton RevenueReservationsButton;
	static private JButton RevenueOccupiedButton;
	static private JTable RevenueTable;
	static private DefaultTableModel model;
	static private final int strutSize = 15;

	public static Box createRevenueTab(DatabaseHandle handle) {
		RevenueModel.setHandle(handle);
		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		try {
			RevenueTable = createRevenueTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		RevenueReservationsButton = new JButton("Reservations");
		RevenueReservationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					RevenueReservationsAction();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		RevenueOccupiedButton = new JButton("Days Occupied");
		RevenueOccupiedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					RevenueOccupiedAction();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		RevenueRevenueButton = new JButton("Monthly Revenue");
		RevenueRevenueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					RevenueRevenueAction();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RevenueReservationsButton);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RevenueOccupiedButton);
		hBox.add(Box.createHorizontalStrut(strutSize));
		hBox.add(RevenueRevenueButton);
		hBox.add(Box.createHorizontalStrut(strutSize));

		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(strutSize));
		vBox.add(new JScrollPane(RevenueTable));
		return vBox;
	}

	static private void RevenueRevenueAction() throws SQLException {
		System.out.println("Revenue->Revnue Pressed");
		UpdateRevenue();
	}

	static private void RevenueOccupiedAction() throws SQLException {
		System.out.println("Revenue->Occupied Pressed");
		UpdateOccupied();
	}

	static private void RevenueReservationsAction() throws SQLException {
		System.out.println("Revenue->Reservations Pressed");
		UpdateReservations();
	}

	static private JTable createRevenueTable() throws SQLException {
		model = new DefaultTableModel(RevenueModel.getMonthlyRevenueReservations(), RevenueModel.RevenueColNameV);
		return new JTable(model);
	}
	static private void UpdateReservations() throws SQLException {
		model.setDataVector(RevenueModel.getMonthlyRevenueReservations(), RevenueModel.RevenueColNameV);
	}
	static private void UpdateOccupied() throws SQLException {
		model.setDataVector(RevenueModel.getMonthlyDaysOccupied(), RevenueModel.RevenueColNameV);
	}
	static private void UpdateRevenue() throws SQLException {
		model.setDataVector(RevenueModel.getMonthlyRevenue(), RevenueModel.RevenueColNameV);
	}

}
