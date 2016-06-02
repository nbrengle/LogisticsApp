package order;

import java.util.ArrayList;
import java.util.Observable;

import facility.FacilityService;
import facility.DTO.FacilityDTO;
import order.DTO.OrderDTO;
import order.exceptions.NoSuchOrderObserverException;
import order.exceptions.NoSuchOrderProcessorException;
import order.observer.OrderObserverFactory;

public class OrderProcessorService extends Observable {

	//Singleton Facade for a Catalog of Orders
	private volatile static OrderProcessorService ourInstance;
	private ArrayList<OrderDTO> orders = new ArrayList<>();

	private OrderProcessorService() {	
		ArrayList<FacilityDTO> facilities = FacilityService.getInstance().getFacilities();
		for (FacilityDTO f : facilities) {
			try {
				OrderObserverFactory.createOrderObserver("Regular", this, f);
			} catch (NoSuchOrderObserverException | NoSuchOrderProcessorException e) {
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
