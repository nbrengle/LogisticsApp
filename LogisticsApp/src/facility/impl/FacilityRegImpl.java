package facility.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityGraph;
import facility.graph.FacilityNeighborHelper;
import facility.interfaces.Facility;
import facility.inventory.InventoryFactory;
import facility.inventory.interfaces.Inventory;
import facility.loader.FacilityLoaderHelper;
import facility.schedule.ScheduleFactory;
import facility.schedule.interfaces.Schedule;


public class FacilityRegImpl implements Facility {
	
	private String city;
	private String state;
	private String uniqueIdentifier;
	private int itemsPerDay;
	private double costPerDay;
	//TODO This is definitely not an ArrayList
	private ArrayList<FacilityNeighborHelper> connectingFacilities;
	private Inventory inventory;
	private Schedule schedule;

	public FacilityRegImpl(String cityIn, String stateIn, int ipdIn, double cpdIn, 
							ArrayList<FacilityLoaderHelper> connectsIn, HashMap<String, Integer> invIn) { 
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
		//TODO pass this exception back up to the Factory
		catch (InvalidParameterException e) {
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
	
	private Inventory setInventory(HashMap<String, Integer> itemsIn) {
		//TODO add appropriate exception handling 
		return InventoryFactory.createInventory("Regular", itemsIn);
	}
	
	private void createConnectionsInFacilityGraph(ArrayList<FacilityLoaderHelper> connectsIn) throws InvalidParameterException {
		for( FacilityLoaderHelper helper : connectsIn) {
			//Facility Loader Helper has City, State, Distance
			String connectionIdentifier = String.format("%s, %s", helper.getCity(), helper.getState());
			FacilityGraph.getInstance().addEdge(uniqueIdentifier, connectionIdentifier, helper.getDistance());
		}
	}
	
	private ArrayList<FacilityNeighborHelper> setConnectingFacilities(ArrayList<FacilityLoaderHelper> connectsIn) throws InvalidParameterException {
		createConnectionsInFacilityGraph(connectsIn);
		return FacilityGraph.getInstance().getNeigbors(uniqueIdentifier);
	}
	
	private void printConnectFacilities(ArrayList<FacilityNeighborHelper> connectsIn) {
		int DistancePerDay = 400; //TODO Consider making me a constant much, much higher up the chain please
		//Prints format like: "Detroit, MI (0.7d);"
		for (FacilityNeighborHelper connect : connectsIn) {
			System.out.printf("%s ('%.1f'd);", connect.getUniqueIdentifier(), (connect.getDistance()/DistancePerDay));
		}
	}
	
	public Schedule getSchedule() {
		//this abstraction is present for other potential implementations
		//functionally the schedule in this case is much like a public member
		//the schedule is expected to manage its own privacy appropriately
		return this.schedule;
	}
	
	private Schedule setSchedule() {
		//TODO add appropriate exception handling
		 return ScheduleFactory.createSchedule("Regular", itemsPerDay); 
	}

	@Override
	public void printReport() {
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println(city + " , " + state);
		System.out.println("-----------");
		System.out.println("Rate per Day: " + itemsPerDay);
		System.out.printf("Cost per Day: '%.1f'%\n", costPerDay); 
		System.out.println("");
		System.out.println("Direct Links:");
		printConnectFacilities(connectingFacilities);
		System.out.println("");
		inventory.printReport();
		System.out.println("");
		schedule.printReport(20);
		System.out.println("---------------------------------------------------------------------------------");
		
	}
	
	//Add the method for reporting how much data I have
	
}
