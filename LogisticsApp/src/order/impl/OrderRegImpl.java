package order.impl;

import java.util.HashMap;

import order.interfaces.Order;

public class OrderRegImpl implements Order {

	//data members
	private String id;
	private int startDay;
	private String destinationCity;
	private String destinationState;
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
		setItems(itemsIn);
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDestinationState() {
		return destinationState;
	}

	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}

	public HashMap<String, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Integer> items) {
		this.items = items;
	}
}
