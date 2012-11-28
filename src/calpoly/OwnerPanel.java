package calpoly;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import owner.Occupancy;
import owner.Owner;
import owner.Reservations;
import owner.Revenue;
import owner.Rooms;

public class OwnerPanel extends JPanel {
	private DatabaseHandle handle;
	private static final long serialVersionUID = 1L;
	private final int strutSize = 15;
	private JTabbedPane ownerTabs;

	public OwnerPanel(DatabaseHandle handle) {
		this.handle = handle;
		Owner.setHandle(handle);
		createOwnerTabs();
	}

	private void createOwnerTabs() {
		ownerTabs = new JTabbedPane();
		ownerTabs.addTab("Occupancy", Occupancy.createOccupancyTab());
		ownerTabs.addTab("Revenue", Revenue.createRevenueTab());
		ownerTabs.addTab("Reservations", Reservations.createReservationsTab());
		ownerTabs.addTab("Rooms", Rooms.createRoomsTab());
		this.add(ownerTabs);
	}

}
