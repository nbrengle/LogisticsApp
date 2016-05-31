package order;

import java.util.HashMap;

import order.execeptions.NoSuchOrderException;
import order.impl.OrderRegImpl;
import order.interfaces.Order;


public class OrderFactory {
	//TODO optimizations include creating an abstract factory that works smarter than this
	
	private OrderFactory() {}; // empty constructor as methods are static
	
	//OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items)
	public static Order createOrder(
										  String type, 
										  String orderId, 
										  int startDay, 
										  String destCity, 
										  String destState, 
										  HashMap<String, Integer> items
										  ) throws NoSuchOrderException {
		if (type.equals("Regular")) {
			return new OrderRegImpl(orderId, startDay, destCity, destState, items); 
		}
		else throw new NoSuchOrderException("Order type :" + type + " does Not Exist");
	}
}
