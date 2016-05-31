package order;

import java.util.ArrayList;
import java.util.Observable;

import facility.FacilityService;
import facility.DTO.FacilityDTO;
import order.exceptions.NoSuchOrderObserverException;
import order.observer.OrderObserverFactory;

public class OrderProcessorService extends Observable {

	//Singleton Facade for a Catalog of Orders
	private volatile static OrderProcessorService ourInstance;

	private OrderProcessorService() {	
		ArrayList<FacilityDTO> facilities = FacilityService.getInstance().getFacilities();
		for (FacilityDTO f : facilities) {
			try {
				addObserver(OrderObserverFactory.createOrderObserver("Regular", f));
			} catch (NoSuchOrderObserverException e) {
				e.printStackTrace();
			}
		}

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
