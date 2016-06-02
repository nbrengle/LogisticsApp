package facility.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import facility.helpers.FacilityNeighborHelper;
import java.security.InvalidParameterException;
import facility.exceptions.NoSuchInventoryException;
import facility.exceptions.NoSuchScheduleException;
import facility.interfaces.Facility;
import facility.inventory.InventoryFactory;
import facility.inventory.interfaces.Inventory;
import facility.schedule.ScheduleFactory;
import facility.schedule.interfaces.Schedule;
import item.exceptions.NoSuchItemException;

public class FacilityRegImpl implements Facility {
	
	private String city;
	private String state;
	private String uniqueIdentifier;
	private int itemsPerDay;
	private double costPerDay;
	private List<FacilityNeighborHelper> connectingFacilities;
	private Inventory inventory;
	private Schedule schedule;

	public FacilityRegImpl(String cityIn, String stateIn, int ipdIn, double cpdIn, 
							ArrayList<FacilityNeighborHelper> connectsIn, HashMap<String, Integer> invIn) 
							throws InvalidParameterException, NoSuchInventoryException, NoSuchScheduleException, NoSuchItemException { 
			setCity(cityIn);
			setState(stateIn);
			setUniqueIdentifier(cityIn, stateIn);
			setItemsPerDay(ipdIn);
			setCostPerDay(cpdIn);
			setInventory(invIn);
			setConnectingFacilities(connectsIn);
			setSchedule();
	}

	//city;
	public String getCity() {
		return city;
	}
	
	private void setCity(String name) {
		//consider additional necessary validation
		this.city = name;
	}
	
	//state;
	public String getState() {
		return state;
	}
	
	private void setState(String name) {
		//consider additional validation
		this.state = name;
	}
	
	//uniqueIdentifier
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	
	private void setUniqueIdentifier(String cityIn, String stateIn) {
		//consider additional validation
		this.uniqueIdentifier = String.format("%s, %s", cityIn, stateIn);
	}
	
	//itemsPerDay;
	public int getItemsPerDay() {
		return this.itemsPerDay;
	}
	
	private void setItemsPerDay(int valIn) throws InvalidParameterException {
		if (valIn < 0) 
			throw new InvalidParameterException("ItemsPerDay must be >= 0");
		this.itemsPerDay = valIn;
	}
	
	//costPerDay;
	public double getCostPerDay() {
		return this.costPerDay;
	}
	
	private void setCostPerDay(double valIn) throws InvalidParameterException {
		//TODO consider creating a price class
		if (valIn < 0.00)  
			throw new InvalidParameterException("CostPerDay must be >= 0"); 
		this.costPerDay = valIn;
	}
	
	@Override
	public HashMap<String, Integer> getActiveItems() {	
		return inventory.getActiveItems();
	}
	
	@Override
	public HashMap<String, Integer> getDepletedItems() {	
		return inventory.getDepletedItems();
	}
	
	private void setInventory(HashMap<String, Integer> itemsIn) throws NoSuchInventoryException, NoSuchItemException {
		this.inventory = InventoryFactory.createInventory("Regular", itemsIn);
	}
	
	private void setConnectingFacilities(ArrayList<FacilityNeighborHelper> connectsIn) throws InvalidParameterException {
		//TODO add more validation!
		this.connectingFacilities = connectsIn;
	}
	
	public List<FacilityNeighborHelper> getConnectingFacilities() {
		//TODO this might not amount to much in terms of information hiding
		List<FacilityNeighborHelper>  returnList = new ArrayList<>();
		for (FacilityNeighborHelper fnh : connectingFacilities) { returnList.add(fnh);}
		return returnList;
	}
	
	private void printConnectFacilities(List<FacilityNeighborHelper> connectsIn) {
		double distancePerDay = 400.00; //TODO Consider making me a constant much, much higher up the chain please
		//Prints format like: "Detroit, MI (0.7d);"
		
		for (FacilityNeighborHelper connect : connectsIn) {
			double day = (connect.getDistance()/distancePerDay);
			System.out.printf("%s (%.1fd); ", connect.getUniqueIdentifier(), day);
		}
	}
	
	public ArrayList<Integer> getSchedule() {
		return schedule.getSchedule();
	}
	
	private void setSchedule() throws NoSuchScheduleException, InvalidParameterException {
		 this.schedule = ScheduleFactory.createSchedule("Regular", itemsPerDay); 
	}
	
	@Override
	public String toString() {
		return uniqueIdentifier;
	}
	
	@Override
	public int getDayOrderWillComplete(int startDay, int itemsInBatch) {
		return schedule.getDayOrderWillComplete(startDay, itemsInBatch);
	}

	@Override
	public void scheduleItems(int startDay, int itemsInBatch) {
		schedule.scheduleItems(startDay, itemsInBatch);
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
	
}
