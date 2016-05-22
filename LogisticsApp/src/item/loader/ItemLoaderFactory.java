package item.loader;

import item.exceptions.NoSuchItemLoaderException;
import item.loader.impl.ItemXmlLoader;
import item.loader.interfaces.ItemLoader;

public class ItemLoaderFactory {

	private ItemLoaderFactory() {}; // empty constructor as methods are static
	
	public static ItemLoader createItemLoader(String type) throws NoSuchItemLoaderException {
		if (type.equals("XML")) 
			return new ItemXmlLoader(); 
		else throw new NoSuchItemLoaderException("ItemLoader type :" + type + " Does Not Exist");
	}
}
