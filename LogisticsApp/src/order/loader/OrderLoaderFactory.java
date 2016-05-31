package order.loader;

import order.exceptions.NoSuchOrderLoaderException;
import order.loader.impl.OrderXmlLoader;
import order.loader.interfaces.OrderLoader;

public class OrderLoaderFactory {
	private OrderLoaderFactory() {}; // empty constructor as methods are static
	
	public static OrderLoader createOrderLoader(String type) throws NoSuchOrderLoaderException {
		if (type.equals("XML")) 
			return new OrderXmlLoader(); 
		else throw new NoSuchOrderLoaderException("OrderLoader type :" + type + " Does Not Exist");
	}
}
