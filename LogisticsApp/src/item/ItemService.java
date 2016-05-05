package item;

import item.interfaces.Item;
import item.loader.ItemLoaderFactory;
import item.exceptions.InvalidPriceException;
import item.exceptions.NoSuchItemException;
import item.exceptions.NoSuchItemLoaderException;

import java.util.ArrayList;

public final class ItemService {
	private ArrayList<Item> items = new ArrayList<Item>();
	
	//Singleton Facade for a Catalog of Items
	private volatile static ItemService ourInstance;
	
	private ItemService() {	
		String filePath = "data/ItemCatalog.xml";
		try {
			items.addAll( ItemLoaderFactory.createItemLoader("XML").loadItems(filePath) );
		}
		catch (NoSuchItemLoaderException e) {
			e.printStackTrace();
		}
	}
	
	public static ItemService getInstance() {
		if (ourInstance == null) {
			synchronized (ItemService.class) {
				if (ourInstance == null) //Double Check
					ourInstance = new ItemService();
			}
		}
		return ourInstance;
	}
	
	public boolean isItem(String itemName) {
		for (Item i : items) {
			if (i.getName().equals(itemName)) return true;
		}
		//if a matching name isn't found:
		return false;
	}
	
	public double getPrice(String itemName) throws NoSuchItemException, InvalidPriceException {
		if (!isItem(itemName)) 
			throw new NoSuchItemException("Cannot check price on" + itemName + "it is not an item");
		for (Item i: items) {
			if(i.getName().equals(itemName)) return i.getPrice();
		}
		//TODO consider a less specific exception
		throw new InvalidPriceException(itemName + "does not have a valid price.");
	}
	
	public void printReport() {
		System.out.println("Item Catalog:");
		for (int i = 0; i < items.size(); i++) {
			if ( (i +1) % 4 == 0)
				System.out.printf("%-8s: $%-,6.0f%n" , items.get(i).getName(), items.get(i).getPrice());
			else
				System.out.printf("%-8s: $%-,6.0f\t" , items.get(i).getName(), items.get(i).getPrice());
		}
	}

}
