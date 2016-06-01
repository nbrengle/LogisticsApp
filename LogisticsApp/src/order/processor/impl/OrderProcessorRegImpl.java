package order.processor.impl;

import facility.FacilityService;
import order.processor.interfaces.OrderProcessor;
import facility.SystemValues;

public class OrderProcessorRegImpl implements OrderProcessor {
	
	//TODO Actually expand this into something worthwhile
	public OrderProcessorRegImpl() {}
	
	public static QuoteDTO getQuote(FacilityDTO fac1, OrderDTO order) {
	//a) Calculate the shortest path (in days) from the facility to the destination.
		String fac2 = 
		int distance = FacilityService.getInstance().getBestPathLength(fac1, fac2);
	//b) Determine the days needed to process the items located at the facility.
		int daysNecessary = (int) Math.ceil((itemsIn / fac1.getItemsPerDay));
	//c) Add the travel time to the previously calculated processing end day to generate the “Arrival Day”
		//TODO move these farther up the chain, hardcoded'll work, but it's inomptimum
		int averageMilesPerHour = 50;
		int drivingHoursPerDay = 8;
		int daysOfTravel = (int) Math.ceil(distance / (averageMilesPerHour * drivingHoursPerDay));
		int arrivalDay = fac1.getStartDay() + (daysOfTravel);
	//d) Save this information as a Facility Record (described later) – a potential solution
		
	}
}
