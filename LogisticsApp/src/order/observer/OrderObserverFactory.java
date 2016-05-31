package order.observer;

import facility.DTO.FacilityDTO;
import order.exceptions.NoSuchOrderException;
import order.exceptions.NoSuchOrderObserverException;
import order.impl.OrderRegImpl;
import order.interfaces.Order;

public class OrderObserverFactory {
	//TODO optimizations include creating an abstract factory that works smarter than this
	
	private OrderObserverFactory() {}; // empty constructor as methods are static
	
	//OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items)
	public static OrderObserver createOrder(String type, FacilityDTO facility) throws NoSuchOrderObserverException {
		if (type.equals("Regular")) {
			return new OrderObserverRegImpl(facility); 
		}
		else throw new NoSuchOrderException("OrderObserver type :" + type + " does Not Exist");
	}
}
