package item.loader.interfaces;

import java.util.ArrayList;

import item.exceptions.NoSuchItemException;
import item.interfaces.Item;

public interface ItemLoader {
	
	public ArrayList<Item> loadItems(String inputFile) throws NoSuchItemException;

}
