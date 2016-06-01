package facility.inventory.interfaces;

import java.util.HashMap;

public interface Inventory {
	
	public void printReport();
	public HashMap<String, Integer> getActiveItems();
	public HashMap<String, Integer> getDepletedItems();

}
