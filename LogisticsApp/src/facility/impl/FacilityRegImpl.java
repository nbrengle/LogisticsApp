package facility.impl;

import java.util.ArrayList;
import java.util.HashMap;

import facility.FacilityNeighborHelper;
import facility.exceptions.InvalidParameterException;
import facility.exceptions.NoSuchInventoryException;
import facility.exceptions.NoSuchScheduleException;
import facility.graph.FacilityGraph;
import facility.interfaces.Facility;
import facility.inventory.InventoryFactory;
import facility.inventory.interfaces.Inventory;
import facility.schedule.ScheduleFactory;
import facility.schedule.interfaces.Schedule;

public class FacilityRegImpl implements Facility {
	
	private String city;
	private String state;
	private String uniqueIdentifier;
	private int itemsPerDay;
	private double costPerDay;
	private ArrayList<FacilityNeighborHelper> connectingFacilities;
	private Inventory inventory;
	private Schedule schedule;

	public FacilityRegImpl(String cityIn, String stateIn, int ipdIn, double cpdIn, 
							ArrayList<FacilityNeighborHelper> connectsIn, HashMap<String, Integer> invIn) { 
		try {
			this.city = setCity(cityIn);
			this.state = setState(stateIn);
			this.uniqueIdentifier = setUniqueIdentifier(cityIn, stateIn);
			this.itemsPerDay = setItemsPerDay(ipdIn);
			this.costPerDay = setCostPerDay(cpdIn);
			this.inventory = setInventory(invIn);
			this.connectingFacilities = setConnectingFacilities(connectsIn);
			this.schedule = setSchedule();
		}
		catch (InvalidParameterException | NoSuchInventoryException | NoSuchScheduleException e) {
			e.printStackTrace();
		}
		
		
	}

	//city;
	public String getCity() {
		return city;
	}
	
	private String setCity(String name) {
		//consider additional necessary validation
		return name;
	}
	
	//state;
	public String getState() {
		return state;
	}
	
	private String setState(String name) {
		//consider additional validation
		return name;
	}
	
	//uniqueIdentifier
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	
	private String setUniqueIdentifier(String cityIn, String stateIn) {
		//consider additional validation
		return String.format("%s, %s", cityIn, stateIn);
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
	
	private Inventory setInventory(HashMap<String, Integer> itemsIn) throws NoSuchInventoryException {
		return InventoryFactory.createInventory("Regular", itemsIn);
	}
	
	private ArrayList<FacilityNeighborHelper> setConnectingFacilities(ArrayList<FacilityNeighborHelper> connectsIn) throws InvalidParameterException {
		return connectsIn;
	}
	
	private void printConnectFacilities(ArrayList<FacilityNeighborHelper> connectsIn) {
		double distancePerDay = 400.00; //TODO Consider making me a constant much, much higher up the chain please
		//Prints format like: "Detroit, MI (0.7d);"
		
		for (FacilityNeighborHelper connect : connectsIn) {
			double day = (connect.getDistance()/distancePerDay);
			System.out.printf("%s (%.1fd); ", connect.getUniqueIdentifier(), day);
		}
	}
	
	public Schedule getSchedule() {
		//this abstraction is present for other potential implementations
		//functionally the schedule in this case is much like a public member
		//the schedule is expected to manage its own privacy appropriately
		return this.schedule;
	}
	
	private Schedule setSchedule() throws NoSuchScheduleException {
		 return ScheduleFactory.createSchedule("Regular", itemsPerDay); 
	}

	@Override
	public void printReport() {
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println(city + " , " + state);
		System.out.println("-----------");
		System.out.println("Rate per Day: " + itemsPerDay);
		System.out.printf("Cost per Day: %.1f %n", costPerDay); 
		System.out.println("");
		System.out.println("Direct Links:");
		printConnectFacilities(connectingFacilities);
		System.out.printf("%n");
		System.out.println("");
		inventory.printReport();
		System.out.println("");
		schedule.printReport(20);
		System.out.printf("%n");
		System.out.println("---------------------------------------------------------------------------------");
		
	}
	
	//Add the method for reporting how much data I have
	
}
