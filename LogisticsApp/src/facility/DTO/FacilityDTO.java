package facility.DTO;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import facility.helpers.FacilityNeighborHelper;

public class FacilityDTO {
	private String city;
	private String state;
	private String uniqueIdentifier;
	private int itemsPerDay;
	private double costPerDay;
	private List<FacilityNeighborHelper> connectingFacilities;
	private HashMap<String, Integer> activeItems;
	private HashMap<String, Integer> depletedItems;
	private ArrayList<Integer> schedule;
	
	public FacilityDTO (
						 String cityIn,
						 String stateIn,
						 int itemsPerDayIn,
						 double costPerDayIn,
						 List<FacilityNeighborHelper> connectingFacilitiesIn,
						 HashMap<String, Integer> activeItemsIn,
						 HashMap<String, Integer> depletedItemsIn,
						 ArrayList<Integer> scheduleIn
						){
		setCity(cityIn);
		setState(stateIn);
		setUniqueIdentifier(cityIn, stateIn);
		setItemsPerDay(itemsPerDayIn);
		setCostPerDay(costPerDayIn);
		setConnectingFacilities(connectingFacilitiesIn);
		setActiveItems(activeItemsIn);
		setDepletedItems(depletedItemsIn);
		setSchedule(scheduleIn);
	}
	
	private void validateStringInput(String input) {
		if (input.equals(null)) { throw new InvalidParameterException("An Order's " + input + " cannot be null"); }
		if (input.equals("")) { throw new NullPointerException("An Order's " + input + " cannot be empty"); }
	}
	
	public String getCity() {
		return city;
	}
	
	private void setCity(String city) {
		validateStringInput(city);
		this.city = city;
	}

	public String getState() {
		return state;
	}

	private void setState(String state) {
		validateStringInput(state);
		this.state = state;
	}

	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	private void setUniqueIdentifier(String cityIn, String stateIn) {
		validateStringInput(cityIn);
		validateStringInput(stateIn);
		this.uniqueIdentifier = String.format("%s, %s", cityIn, stateIn);
	}

	public int getItemsPerDay() {
		return itemsPerDay;
	}

	private void setItemsPerDay(int itemsPerDay) {
		if (itemsPerDay <= 0) throw new InvalidParameterException("ItemsPerDay must be greater than 0");
		this.itemsPerDay = itemsPerDay;
	}

	public double getCostPerDay() {
		return costPerDay;
	}

	private void setCostPerDay(double costPerDay) {
		if (costPerDay <= 0) throw new InvalidParameterException("costPerDay must be greater than 0");
		this.costPerDay = costPerDay;
	}
	
	public List<FacilityNeighborHelper> getConnectingFacilities() {
		//TODO this might not amount to much for information hiding
		List<FacilityNeighborHelper>  returnList = new ArrayList<>();
		for (FacilityNeighborHelper fnh : connectingFacilities) { returnList.add(fnh);}
		return returnList;
	}

	private void setConnectingFacilities(List<FacilityNeighborHelper> connectingFacilities) {
		if (connectingFacilities.equals(null)) throw new NullPointerException("connectingFacilties cannot be null");
		this.connectingFacilities = connectingFacilities;
	}

	public HashMap<String, Integer> getActiveItems() {
		//TODO this might not amount to much for information hiding
		HashMap<String, Integer> returnList = new HashMap<>();
		activeItems.forEach((k,v) -> returnList.put(k,v));
		return returnList;
	}

	private void setActiveItems(HashMap<String, Integer> activeItems) {
		if (activeItems.equals(null)) throw new NullPointerException("activeItems cannot be null");
		this.activeItems = activeItems;
	}

	public HashMap<String, Integer> getDepletedItems() {
		//TODO this might not amount to much for information hiding
		HashMap<String, Integer> returnList = new HashMap<>();
		depletedItems.forEach((k,v) -> returnList.put(k,v));
		return returnList;
	}

	private void setDepletedItems(HashMap<String, Integer> depletedItems) {
		if (depletedItems.equals(null)) throw new NullPointerException("depletedItems cannot be null");
		this.depletedItems = depletedItems;
	}

	public ArrayList<Integer> getSchedule() {
		//TODO this might not amount to much for information hiding
		ArrayList<Integer>  returnList = new ArrayList<>();
		for (int i : schedule) { returnList.add(i);}
		return returnList;
	}

	private void setSchedule(ArrayList<Integer> schedule) {
		if (schedule.equals(null)) throw new NullPointerException("schedule cannot be null");
		this.schedule = schedule;
	}

}
