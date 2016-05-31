package order;

import java.util.ArrayList;
import java.util.Observable;

import facility.FacilityService;
import facility.DTO.FacilityDTO;
import order.exceptions.NoSuchOrderObserverException;
import order.interfaces.Order;
import order.observer.OrderObserverFactory;
import order.processor.OrderProcessorFactory;

public class OrderProcessorService extends Observable {

	//Singleton Facade for a Catalog of Orders
	private volatile static OrderProcessorService ourInstance;
	private ArrayList<OrderDTO> orders = new ArrayList<>();

	private OrderProcessorService() {	
		ArrayList<FacilityDTO> facilities = FacilityService.getInstance().getFacilities();
		for (FacilityDTO f : facilities) {
			try {
				addObserver(OrderObserverFactory.createOrderObserver("Regular", f));
			} catch (NoSuchOrderObserverException e) {
				e.printStackTrace();
			}
		}
		orders.addAll(OrderService.getInstance().getOrders()); //TODO this needs a little thinking through
	}

	public static OrderProcessorService getInstance() {
		if (ourInstance == null) {
			synchronized (OrderService.class) {
				if (ourInstance == null) //Double Check
					ourInstance = new OrderProcessorService();
			}
		}
		return ourInstance;
	}
	

}
