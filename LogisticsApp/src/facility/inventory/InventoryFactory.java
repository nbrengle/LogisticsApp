package facility.inventory;

import facility.inventory.interfaces.Inventory;

public class InventoryFactory {
	
	private InventoryFactory() {}; // empty constructor as methods are static
	
	public static Inventory createInventory(String type, HashMap<String, Integer> invIn) throws NoSuchItemException{
		if (type.equals("Regular")) 
			return new InventoryRegImpl(invIn); 
		else throw new NoSuchItemException("Item type :" + type + " Does Not Exist");
	}
}
