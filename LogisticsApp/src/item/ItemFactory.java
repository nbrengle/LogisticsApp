package item;
import item.exceptions.NoSuchItemException;
import item.impl.ItemRegImpl;
import item.interfaces.Item;

public class ItemFactory {

	private ItemFactory() {}; // empty constructor as methods are static
	
	public static Item createItem(String type, String name, Double price) throws NoSuchItemException{
		if (type.equals("Regular")) 
			return new ItemRegImpl(name,price); 
		else throw new NoSuchItemException("Item type :" + type + " Does Not Exist");
	}
}
