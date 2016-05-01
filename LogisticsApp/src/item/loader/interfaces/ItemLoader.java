package item.loader.interfaces;

import java.util.ArrayList;

import item.interfaces.Item;

public interface ItemLoader {
	
	public ArrayList<Item> loadItems(String inputFile);

}
