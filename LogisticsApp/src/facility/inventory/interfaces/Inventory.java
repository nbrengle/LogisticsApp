package facility.inventory.interfaces;

import java.security.InvalidParameterException;
import java.util.HashMap;

import item.exceptions.NoSuchItemException;

public interface Inventory {
	
	public void printReport();
	public HashMap<String, Integer> getActiveItems();
	public HashMap<String, Integer> getDepletedItems();
	public void consumeItem(String itemName, int numItems) throws NoSuchItemException, InvalidParameterException;

}
