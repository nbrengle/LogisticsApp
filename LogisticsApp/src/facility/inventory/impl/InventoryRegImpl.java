package facility.inventory.impl;

import item.ItemService;
import item.exceptions.NoSuchItemException;
import item.interfaces.Item;
import java.util.HashMap;

import facility.inventory.interfaces.Inventory;

public class InventoryRegImpl implements Inventory {
	
	private HashMap<String, Integer> active;
	private HashMap<String, Integer> depleted;
	
	public InventoryRegImpl(HashMap<String, Integer> itemsIn) {
		for (String key : itemsIn.keySet() ) {
			validate
		}
	}
	
	private void validateItem(String itemIn) throws NoSuchItemException {
		if (!ItemService.getInstance().isItem(itemIn)) throw new NoSuchItemException(itemIn + "is not an item in the catalog");
	}
	
	private void addItem(Item itemIn, int quantity) throws NoSuchItemException {
		validateItem(itemIn);
		active.put(itemIn, quantity);
	}
	
}
