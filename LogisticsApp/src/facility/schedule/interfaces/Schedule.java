package facility.schedule.interfaces;

import java.util.ArrayList;

public interface Schedule {

	public void printReport(int valIn);
	public void printReport();
	public int getDayOrderWillComplete(int startDay, int itemsInBatch);
	public void scheduleItems(int startDay, int itemsInBatch);
	public ArrayList<Integer> getSchedule();
	
	
}
