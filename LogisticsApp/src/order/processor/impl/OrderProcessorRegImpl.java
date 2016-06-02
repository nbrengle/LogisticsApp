package order.processor.impl;

import java.security.InvalidParameterException;

import facility.FacilityService;
import order.DTO.OrderDTO;
import order.DTO.QuoteDTO;
import order.processor.interfaces.OrderProcessor;
import facility.DTO.FacilityDTO;

public class OrderProcessorRegImpl implements OrderProcessor {
	
	private	final static int 	AVERAGE_MILES_PER_HOUR = 50;
	private	final static int 	DRIVING_HOURS_PER_DAY = 8;
	
	private FacilityDTO facility;
	private OrderDTO order;
	
	//TODO Actually expand this into something worthwhile
	public OrderProcessorRegImpl(FacilityDTO facilityIn, OrderDTO orderIn) {
		setFacility(facilityIn);
		setOrder(orderIn);
	}
	
	private void setOrder(OrderDTO orderIn) {
		if (orderIn.equals(null)) {throw new NullPointerException("OrderProcessor Cannot Have a Null Order");}
		this.order = orderIn;
	}

	private void setFacility(FacilityDTO facilityIn) {
		if (facilityIn.equals(null)) {throw new NullPointerException("OrderProcessor Cannot Have a Null Facility");}
		this.facility = facilityIn;
	}

	@Override
	public QuoteDTO getQuote() throws InvalidParameterException {
	//a) Calculate the shortest path (in days) from the facility to the destination.
		int distance = FacilityService.getInstance().getBestPathLength(facility.getUniqueIdentifier(), 
																	   order.getDestinationUniqueIdentifier());
		
	//b) Determine the days needed to process the items located at the facility.
		int daysNecessary = (int) Math.ceil((order.getItems().size() / facility.getItemsPerDay()));
		
	//c) Add the travel time to the previously calculated processing end day to generate the “Arrival Day”
		int daysOfTravel = (int) Math.ceil(distance / (AVERAGE_MILES_PER_HOUR * DRIVING_HOURS_PER_DAY));
		int arrivalDay = order.getStartDay() + daysOfTravel;
		
	//d) Save this information as a Facility Record (described later) – a potential solution
		return new QuoteDTO(distance, daysNecessary, daysOfTravel, arrivalDay);
		
		
	}
}
