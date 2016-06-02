package order;

import java.util.ArrayList;
import java.util.Observable;

import facility.FacilityService;
import facility.DTO.FacilityDTO;
import order.DTO.OrderDTO;
import order.DTO.QuoteDTO;
import order.exceptions.NoSuchOrderObserverException;
import order.exceptions.NoSuchOrderProcessorException;
import order.observer.OrderObserverFactory;

public class OrderProcessorService extends Observable {

	//Singleton Facade for Order Processing
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
		orders.addAll(OrderService.getInstance().getOrders());
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
	
	//Step 3: Sort the Quotes developed by earliest (lowest) Arrival Day
	//Step 4: Select the facility with the earliest (lowest) Arrival Day:
	
	/*
	 * 	Processing Solution:
			Total Cost: 	  $94,355
			1st Delivery Day: 3
			Last Delivery Day:	16
			Order Items:
				   Item ID 	Quantity	Cost 		# Sources Used 	First Day	Last Day
				1) ABC123	180			$67,705		4				4			16
				2) CR2032	320			$26,650		1				3			6
		----------------------------------------------------------------------------------
	 */

}
