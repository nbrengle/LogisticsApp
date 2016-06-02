package order.observer;

import java.util.Observable;

import facility.DTO.FacilityDTO;
import order.exceptions.NoSuchOrderObserverException;
import order.exceptions.NoSuchOrderProcessorException;
import order.observer.impl.OrderObserverRegImpl;
import order.observer.interfaces.OrderObserver;

public class OrderObserverFactory {
	//TODO optimizations include creating an abstract factory that works smarter than this
	
	private OrderObserverFactory() {}; // empty constructor as methods are static
	
	//OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items)
	public static OrderObserver createOrderObserver(String type, Observable subject, FacilityDTO facility) throws NoSuchOrderObserverException, NoSuchOrderProcessorException {
		if (type.equals("Regular")) {
			return new OrderObserverRegImpl (subject, facility, "Regular"); 
		}
		else throw new NoSuchOrderObserverException("OrderObserver type :" + type + " does Not Exist");
	}
}
