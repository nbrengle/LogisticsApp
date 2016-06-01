package facility.interfaces;

import java.util.List;

import facility.helpers.FacilityNeighborHelper;
import facility.schedule.interfaces.Schedule;

public interface Facility {
	
	public void printReport();

	public List<FacilityNeighborHelper> getConnectingFacilities();
	public Schedule getSchedule();
	public String getState();
	public String getCity();
	public String getUniqueIdentifier();
	public int getItemsPerDay();
	public double getCostPerDay();
	public String toString();

	public int getDayOrderWillComplete(int startDay, int itemsInBatch);
	public void scheduleItems(int startDay, int itemsInBatch);

}
