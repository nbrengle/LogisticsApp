package order;

import java.util.ArrayList;
import java.util.Observable;

import facility.FacilityService;
import facility.DTO.FacilityDTO;

public class OrderProcessorService extends Observable {

	//Singleton Facade for a Catalog of Orders
	private volatile static OrderProcessorService ourInstance;

	private OrderProcessorService() {	
		ArrayList<FacilityDTO> facilities = FacilityService.getInstance().getFacilities();
		for (FacilityDTO f : facilities) {
			addObserver(new OrderObserver(f));
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
