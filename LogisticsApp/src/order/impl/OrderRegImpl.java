package order.impl;

import java.security.InvalidParameterException;
import java.util.HashMap;
import order.interfaces.Order;

public class OrderRegImpl implements Order {

	//data members
	private String id;
	private int startDay;
	private String destinationCity;
	private String destinationState;
	private String destinationUniqueIdentifier;
	private HashMap<String, Integer> items = null;
	
	public OrderRegImpl(String idIn, 
						int startDayIn, 
						String destCityIn, 
						String destStateIn, 
						HashMap<String, Integer> itemsIn) {
		setId(idIn);
		setStartDay(startDayIn);
		setDestinationCity(destCityIn);
		setDestinationState(destStateIn);
		setDestinationUniqueIdentifier(destCityIn, destStateIn);
		setItems(itemsIn);
		
	}

	private void validateStringInput(String input) {
		if (input.equals(null)) { throw new InvalidParameterException("An Order's " + input + " cannot be null"); }
		if (input.equals("")) { throw new NullPointerException("An Order's " + input + " cannot be empty"); }
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		validateStringInput(id);
		this.id = id;
	}

	public int getStartDay() {
		return startDay;
	}

	private void setStartDay(int startDay) {
		if (startDay <= 0) { throw new InvalidParameterException("startDay must be greater than 0"); }
		this.startDay = startDay;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	private void setDestinationCity(String destinationCity) {
		validateStringInput(destinationCity);
		this.destinationCity = destinationCity;
	}

	public String getDestinationState() {
		return destinationState;
	}

	private void setDestinationState(String destinationState) {
		validateStringInput(destinationState);
		this.destinationState = destinationState;
	}

	public HashMap<String, Integer> getItems() {
		HashMap<String, Integer> returnList = new HashMap<>();
		items.forEach((k,v) -> returnList.put(k,v));
		return returnList;
	}

	private void setItems(HashMap<String, Integer> items) {
		if (items.isEmpty()) {throw new InvalidParameterException("An Order must contain items"); }
		if (items.equals(null)) {throw new NullPointerException("An Order cannot have null items"); }
		this.items = items;
	}
	
	public String getDestinationUniqueIdentifier() {
		return destinationUniqueIdentifier;
	}
	
	private void setDestinationUniqueIdentifier(String destinationCity, String destinationState) {
		validateStringInput(destinationCity); 
		validateStringInput(destinationState); 
		this.destinationUniqueIdentifier = String.format("%s, %s", destinationCity, destinationState);
	}
	//Top half of the mandatory report, there's another half that exists
	/*
	----------------------------------------------------------------------------------
	Order #1
		Order Id: TO-001
		Order Time: Day 1
		Destination: Miami, FL
		List of Order Items:
			1) Item ID: 	ABC123, 	Quantity: 180
			2) Item ID: 	CR2032, 	Quantity: 320
	*/
	
	@Override
	public void printReport(int index) {
		System.out.println("----------------------------------------------------------------------------------");
		System.out.printf("\tOrder #" + index);
		System.out.printf("\tOrder Id: %s %n", id);
		System.out.printf("\tOrder Time: Day %d %n", startDay);
		System.out.printf("\tDestination: %s %n", destinationUniqueIdentifier);
		System.out.printf("\tList of Order Items:%n");
    	int i = 0; //counter for print format below. 
		items.forEach((k,v) -> {
    		System.out.printf("\t\t%d) Item ID:\t %s,\tQuantity: %d",i,k,v);
    		
    	});
	}
}
