package order.processor;

import java.util.HashMap;

import order.exceptions.NoSuchOrderProcessorException;
import order.impl.OrderRegImpl;
import order.interfaces.Order;

public class OrderProcessorFactory {
	//TODO optimizations include creating an abstract factory that works smarter than this
	
	private OrderProcessorFactory() {}; // empty constructor as methods are static
	
	//OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items)
	public static OrderProcessor createOrderProcessor(String type) throws NoSuchOrderProcessorException {
		if (type.equals("Regular")) {
			return new OrderProcessorRegImpl(); 
		}
		else throw new NoSuchOrderProcessorException("OrderProcessor type :" + type + " does Not Exist");
	}
}
