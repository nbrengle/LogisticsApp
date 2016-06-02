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
	
	//Step 1: Identify all facilities with the desired item 
	//NOTE: Destinations cannot be their own source.
	//Step 2: For each Facility Identified, get a Quote
	//Step 3: Sort the (4) records developed in step “2d” above by earliest (lowest) Arrival Day
	//Step 4: Select the facility with the earliest (lowest) Arrival Day and do the following:
	//Reduce the inventory of the item at that facility by the number of items taken
	//Reduce the quantity of the item that is needed for the order by the amount taken from the selected facility
	//Update the schedule of the selected site (book the days needed to process the items)
	//Save this as part of your solution
	//Get fresh data from the facility!
	//Step 5: If there is still more quantity of the item needed, go back to step 4 and continue the process. 
	//Step 6: Compute the total cost of this item.
	//The logistics costs of an Order Item consist of: (Total Item Cost + Total Facility Processing Cost + Total Transport Cost)
	//Step 7: Generate the complete logistics record for this order-item (your set of solutions from step 4).
	//Step 8: If there are more items to process in this order, go back and repeat this process from step 1 with the next item
	//Step 9: Generate output. 
	// Order Report Info + this info:
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
