package facility.schedule.interfaces;

public interface Schedule {

	public void printReport(int valIn);
	public void printReport();
	public int getDayOrderWillComplete(int startDay, int itemsInBatch);
	void scheduleItems(int startDay, int itemsInBatch);
	
	
}
