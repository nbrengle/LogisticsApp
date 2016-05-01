package facility.impl;

import java.util.ArrayList;
import java.util.HashMap;

import facility.exceptions.InvalidParameterException;
import facility.interfaces.Facility;
import facility.inventory.interfaces.Inventory;
import facility.loader.FacilityLoaderHelper;
import facility.schedule.interfaces.Schedule;


public class FacilityRegImpl implements Facility {
	
	private String city;
	private String state;
	private int itemsPerDay;
	private double costPerDay;
	//TODO This is definitely not an ArrayList
	private ArrayList<Facility> connectingFacilities;
	private Inventory inventory;
	private Schedule schedule;

	public FacilityRegImpl(String cityIn, String stateIn, int ipdIn, double cpdIn, 
							ArrayList<FacilityLoaderHelper> connectsIn, HashMap<String, Integer> invIn) { 
		try {
			this.city = setCity(cityIn);
			this.state = setState(stateIn);
			this.itemsPerDay = setItemsPerDay(ipdIn);
			this.costPerDay = setCostPerDay(cpdIn);
			this.inventory = setInventory(invIn);
			this.connectingFacilities = setConnectingFacilities(connectIn);
			this.schedule = setSchedule();
		}
		//TODO pass this exception back up to the Factory
		catch (InvalidParameterException e) {
			e.printStackTrace();
		}
	}
	

	
	
	//city;
	public String getCity() {
		return this.city;
	}
	
	private String setCity(String name) {
		//consider additional necessary validation
		return name;
	}
	
	//state;
	public String getState() {
		return this.state;
	}
	
	private String setState(String name) {
		//consider additional validation
		return name;
	}
	
	//itemsPerDay;
	public int getItemsPerDay() {
		return this.itemsPerDay;
	}
	
	private int setItemsPerDay(int valIn) throws InvalidParameterException {
		if (valIn < 0) { throw new InvalidParameterException("ItemsPerDay must be >= 0"); }
		return valIn;
	}
	
	//costPerDay;
	public double getCostPerDay() {
		return this.costPerDay;
	}
	
	private double setCostPerDay(double valIn) throws InvalidParameterException {
		if (valIn < 0.00) { throw new InvalidParameterException("CostPerDay must be >= 0"); }
		return valIn;
	}
	
	public Inventory getInventory() {
		//this abstraction is present for other potential implementations
		//functionally the inventory in this case is much like a public member
		//the inventory is expected to manage its own privacy appropriately
		return this.inventory;
	}
	
	private Inventory setInventory(HashMap<String, Integer> itemsIn) {
		 return InventoryFactory.createInventory("Regular", itemsIn);
	}
	
	public Schedule getSchedule() {
		//this abstraction is present for other potential implementations
		//functionally the schedule in this case is much like a public member
		//the schedule is expected to manage its own privacy appropriately
		return this.schedule;
	}
	
	private Schedule setSchedule() {
		 return ScheduleFactory.createSchedule(); // TODO Add args
	}

	@Override
	public void printReport() {
		// TODO Auto-generated method stub
		
	}
	
	//Add the method for reporting how much data I have
}
