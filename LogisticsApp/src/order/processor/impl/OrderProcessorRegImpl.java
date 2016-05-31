package order.processor.impl;

import facility.FacilityService;
import order.processor.interfaces.OrderProcessor;

public class OrderProcessorRegImpl implements OrderProcessor {
	
	//TODO Actually exand this into something worthwhile
	public OrderProcessorRegImpl() {
	//a) Calculate the shortest path (in days) from the facility to the destination.
		FacilityService.getInstance().getBestPathLength(fac1, fac2)
	//b) Determine the days needed to process the items located at the facility.
	//c) Add the travel time to the previously calculated processing end day to generate the “Arrival Day” 
	//d) Save this information as a Facility Record (described later) – a potential solution
	}
}
