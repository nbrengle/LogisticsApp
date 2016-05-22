package facility.inventory;

import java.util.HashMap;

import facility.exceptions.NoSuchInventoryException;
import facility.inventory.impl.InventoryRegImpl;
import facility.inventory.interfaces.Inventory;
import item.exceptions.NoSuchItemException;

public class InventoryFactory {
	
	private InventoryFactory() {}; // empty constructor as methods are static
	
	public static Inventory createInventory(String type, HashMap<String, Integer> invIn) throws NoSuchInventoryException, NoSuchItemException {
		if (type.equals("Regular")) 
			return new InventoryRegImpl(invIn); 
		//TODO create a real exception here!
		else throw new NoSuchInventoryException("Inventory type :" + type + " Does Not Exist");
	}
}
