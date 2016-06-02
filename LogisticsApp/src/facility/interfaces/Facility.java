package facility.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import facility.helpers.FacilityNeighborHelper;

public interface Facility {
	
	public void printReport();

	public List<FacilityNeighborHelper> getConnectingFacilities();
	public ArrayList<Integer> getSchedule();
	public String getState();
	public String getCity();
	public String getUniqueIdentifier();
	public int getItemsPerDay();
	public double getCostPerDay();
	public String toString();

	public int getDayOrderWillComplete(int startDay, int itemsInBatch);
	public void scheduleItems(int startDay, int itemsInBatch);

	public HashMap<String, Integer> getActiveItems();

	public HashMap<String, Integer> getDepletedItems();

}
