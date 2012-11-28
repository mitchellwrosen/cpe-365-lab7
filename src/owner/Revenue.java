package owner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class Revenue {
	static private JTabbedPane RevenueTab;
	static private JButton RevenueRevenueButton;
	static private JButton RevenueReservationsButton;
	static private JButton RevenueOccupiedButton;
	static private JTable RevenueTable;
	static private final int strutSize = 15;

	public static JTabbedPane createRevenueTab() {

		Box hBox = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();

		RevenueTab = new JTabbedPane();
		RevenueTable = createRevenueTable();
		RevenueReservationsButton = new JButton("Reservations");
		RevenueReservationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RevenueReservationsAction();
			}
		});
		RevenueOccupiedButton = new JButton("Days Occupied");
		RevenueOccupiedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RevenueOccupiedAction();
			}
		});
		RevenueRevenueButton = new JButton("Monthly Revenue");
		RevenueRevenueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				RevenueRevenueAction();
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
		RevenueTab.add(vBox);
		return RevenueTab;
	}

	static private void RevenueRevenueAction() {
		System.out.println("Revenue->Revnue Pressed");
	}

	static private void RevenueOccupiedAction() {
		System.out.println("Revenue->Occupied Pressed");
	}

	static private void RevenueReservationsAction() {
		System.out.println("Revenue->Reservations Pressed");
	}

	static private JTable createRevenueTable() {
		return new JTable(Owner.getMonthlyRevenueReservations("", ""),
				Owner.RevenueColNameV);
	}

}
