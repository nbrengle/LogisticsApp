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
	public QuoteDTO getQuote(String targetItem) throws InvalidParameterException {
		if (targetItem.equals(null)) {throw new NullPointerException("OrderProcessor Cannot Have a Null targetItem");}
		if (targetItem.equals("")) {throw new InvalidParameterException("OrderProcessor Cannot Have an Empty targetItem");}
		
		//a) Calculate the shortest path (in days) from the facility to the destination.
		int distance = FacilityService.getInstance().getBestPathLength(facility.getUniqueIdentifier(), 
																	   order.getDestinationUniqueIdentifier());
		
		//b) Determine the days needed to process the items located at the facility.
		int itemsAtFacility = facility.getActiveItems().get(targetItem);
		int itemsRequested = order.getItems().get(targetItem);
		int itemQuantity =  (itemsAtFacility < itemsRequested) ? itemsAtFacility : itemsRequested;
		int daysNecessary = (int) Math.ceil((itemQuantity / facility.getItemsPerDay()));
		int endDay = order.getStartDay() + daysNecessary;
		
		//c) Add the travel time to the previously calculated processing end day to generate the “Arrival Day”
		int travelTime = (int) Math.ceil(distance / (AVERAGE_MILES_PER_HOUR * DRIVING_HOURS_PER_DAY));
		int startDay = order.getStartDay();
		int arrivalDay = startDay + travelTime;

		
		//d) Save this information as a Facility Record (described later) – a potential solution
		
		//These are what I actually need back from the Quote!
		//Source: Norfolk, VA
		//Number of Items: 50
		//Processing End Day: 5
		//Travel Time: 4d
		//Arrival Day: 9
		
		String name = facility.getUniqueIdentifier();
		
		return new QuoteDTO(name, targetItem, itemQuantity, startDay, endDay, travelTime, arrivalDay);
		
		
	}
}
