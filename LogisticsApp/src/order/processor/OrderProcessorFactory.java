package order.processor;

import facility.DTO.FacilityDTO;
import order.DTO.OrderDTO;
import order.exceptions.NoSuchOrderProcessorException;
import order.processor.impl.OrderProcessorRegImpl;
import order.processor.interfaces.OrderProcessor;

public class OrderProcessorFactory {
	//TODO optimizations include creating an abstract factory that works smarter than this
	
	private OrderProcessorFactory() {}; // empty constructor as methods are static
	
	//OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items)
	public static OrderProcessor createOrderProcessor(String type,
													  FacilityDTO facility,
													  OrderDTO order
													  ) throws NoSuchOrderProcessorException {
		if (type.equals("Regular")) {
			return new OrderProcessorRegImpl(facility, order); 
		}
		else throw new NoSuchOrderProcessorException("OrderProcessor type :" + type + " does Not Exist");
	}
}
