package facility.inventory.impl;

import item.ItemService;
import item.exceptions.NoSuchItemException;

import java.util.ArrayList;
import java.util.HashMap;

import facility.exceptions.InvalidParameterException;
import facility.inventory.interfaces.Inventory;

public class InventoryRegImpl implements Inventory {
	
	private HashMap<String, Integer> active;
	private HashMap<String, Integer> depleted;
	
	public InventoryRegImpl(HashMap<String, Integer> itemsIn) throws NoSuchItemException {
			for (String key : itemsIn.keySet()) validateItem(key);
			active = new HashMap<>();
			itemsIn.forEach((k,v) -> active.put(k, v));
			depleted = new HashMap<>();
	}
	
	private void validateItem(String itemIn) throws NoSuchItemException {
		if (!ItemService.getInstance().isItem(itemIn)) throw new NoSuchItemException(itemIn + "is not an item in the catalog");
	}
	
	public boolean hasItem(String itemTest) throws NoSuchItemException {
		validateItem(itemTest);
		return active.containsKey(itemTest) || depleted.containsKey(itemTest);
	}
	
	private boolean itemIsActive(String itemTest) throws NoSuchItemException {
		validateItem(itemTest);
		return active.containsKey(itemTest);
	}
	
	public boolean itemIsDepleted(String itemTest) throws NoSuchItemException {
		validateItem(itemTest);
		return depleted.containsKey(itemTest);
	}
	
	public HashMap<String, Integer> getActiveItems() {
		HashMap<String, Integer> returnList = new HashMap<>();
		active.forEach((k,v) -> returnList.put(k,v));
		return returnList;
	}
	
	public HashMap<String, Integer> getDepletedItems() {
		HashMap<String, Integer> returnList = new HashMap<>();
		depleted.forEach((k,v) -> returnList.put(k,v));
		return returnList;
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
			System.out.printf("%n");
			System.out.printf("\t%-8s\t%-8s%n","Item ID","Quantity");
			active.forEach((k,v) -> System.out.printf("\t%-8s\t%d%n",k,v));
		}
		System.out.printf("%n");
		System.out.print("Depleted (Used-Up) Inventory:"); 
		if (depleted.isEmpty()) System.out.println(" None");
		else {
			System.out.println("\tItem ID\tQuantity");
			depleted.forEach((k,v) -> System.out.printf("\t%-8s\t%d%n",k,v));
		}
	}
	
}
