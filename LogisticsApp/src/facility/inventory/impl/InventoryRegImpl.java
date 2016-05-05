package facility.inventory.impl;

import item.ItemService;
import item.exceptions.NoSuchItemException;
import item.interfaces.Item;
import java.util.HashMap;
import java.util.Map;

import facility.exceptions.InvalidParameterException;
import facility.inventory.interfaces.Inventory;

public class InventoryRegImpl implements Inventory {
	
	private HashMap<String, Integer> active;
	private HashMap<String, Integer> depleted;
	
	public InventoryRegImpl(HashMap<String, Integer> itemsIn) {
		try {
			for (String key : itemsIn.keySet()) validateItem(key);
			active = new HashMap<>();
			itemsIn.forEach((k,v) -> active.put(k, v));
		} catch (NoSuchItemException e) { e.printStackTrace(); }
	}
	
	private void validateItem(String itemIn) throws NoSuchItemException {
		if (!ItemService.getInstance().isItem(itemIn)) throw new NoSuchItemException(itemIn + "is not an item in the catalog");
	}
	
	//TODO do you need me? Should I become public? 
	private boolean hasItem(String itemTest) throws NoSuchItemException {
		validateItem(itemTest);
		return active.containsKey(itemTest) || depleted.containsKey(itemTest);
	}
	
	private boolean itemIsActive(String itemTest) throws NoSuchItemException {
		validateItem(itemTest);
		return active.containsKey(itemTest);
	}
	
	//TODO do you need me? Should I become public? 
	private boolean itemIsDepleted(String itemTest) throws NoSuchItemException {
		validateItem(itemTest);
		return depleted.containsKey(itemTest);
	}
	
	public void consumeItem(String itemToConsume, int quantityToConsume) throws NoSuchItemException, InvalidParameterException {
		int currentQuantity = active.get(itemToConsume);
		if (itemIsActive(itemToConsume) && (currentQuantity > quantityToConsume)) {
			depleted.put(itemToConsume, quantityToConsume);
			active.replace(itemToConsume, (currentQuantity - quantityToConsume));
		}
		else if (itemIsActive(itemToConsume) && (currentQuantity == quantityToConsume)) {
			depleted.put(itemToConsume, quantityToConsume);
			active.remove(itemToConsume);
		}
		//TODO consider a more descriptive unique exception
		else throw new InvalidParameterException(itemToConsume + " has " + currentQuantity + " cannot consume " + quantityToConsume);
		 
	}

	@Override
	public void printReport() {
		
		System.out.print("Active Inventory:");
		if (active.isEmpty()) System.out.println(" None");
		else {
			System.out.println("\tItem ID\tQuantity");
			active.forEach((k,v) -> System.out.printf("\t%s\t%d\n",k,v));
		}
		System.out.print("Depleted (Used-Up) Inventory:"); 
		if (depleted.isEmpty()) System.out.println(" None");
		else {
			System.out.println("\tItem ID\tQuantity");
			depleted.forEach((k,v) -> System.out.printf("\t%s\t%d\n",k,v));
		}
	}
	
}
