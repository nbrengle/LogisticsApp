package order.observer;

import facility.DTO.FacilityDTO;
import order.exceptions.NoSuchOrderObserverException;
import order.observer.impl.OrderObserverRegImpl;
import order.observer.interfaces.OrderObserver;

public class OrderObserverFactory {
	//TODO optimizations include creating an abstract factory that works smarter than this
	
	private OrderObserverFactory() {}; // empty constructor as methods are static
	
	//OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items)
	public static OrderObserver createOrderObserver(String type, FacilityDTO facility) throws NoSuchOrderObserverException {
		if (type.equals("Regular")) {
			return new OrderObserverRegImpl(facility); 
		}
		else throw new NoSuchOrderObserverException("OrderObserver type :" + type + " does Not Exist");
	}
}
