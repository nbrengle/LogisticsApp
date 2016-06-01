package facility.schedule.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import facility.schedule.interfaces.Schedule;

public class ScheduleRegImpl implements Schedule {

	private int itemsPerDay;
	private ArrayList<Integer> itemsConsumedEachDay = new ArrayList<>();

	public ScheduleRegImpl (int itemsPerDayIn) throws InvalidParameterException  {
			setItemsPerDay(itemsPerDayIn);
	}

	private void setItemsPerDay(int valIn) throws InvalidParameterException {
		//this exists to do more complex validation if needed at some point
		if (valIn <= 0) throw new InvalidParameterException(
									valIn + "is not a valid number of items per day for a schedule");
		this.itemsPerDay = valIn;

	}
	
	//method of testing when an item batch could be completed
	@Override
	public int getDayOrderWillComplete(int startDay, int itemsInBatch) {
		int dayComplete = 0, temp = itemsInBatch;
		//TODO Off by one error? 
		for (int i = startDay; i < itemsConsumedEachDay.size(); i++) {
			if (temp == 0) dayComplete = i;
			else if (itemsConsumedEachDay.get(i) < itemsPerDay) {
				int difference = itemsPerDay - itemsConsumedEachDay.get(i);
				temp = temp - difference;
			}
		}
		
		return dayComplete;
	}
	
	//method for adding items to the schedule
	public void scheduleBatch(int itemsInBatch) {
		
	}
	//method for reporting how many days are consumed
	//method of printing days by amount consumed
	
	
	/*
	 * Schedule:
	 * Day:
	 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
	 * Available:
	 * 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10
	 */
	
	@Override
	public void printReport(int valIn) {
		System.out.println("Schedule:");
		System.out.printf("%-11s\t","Day:"); 
		for (int i = 1; i <= valIn; i++) {
			System.out.printf("%2d ", i);
		}
		System.out.printf("%n");
		System.out.print("Available:\t"); 

		for (int i = 1; i <= valIn; i++) {
			int val = itemsPerDay - itemsConsumedEachDay.get(i);
			System.out.printf("%2d ", val);

		}
	}
	
	@Override
	public void printReport() {
		System.out.println("Schedule:");
		System.out.printf("%-11s\t","Day:"); 
		//TODO Off by one error? 
		//TODO confirm page fit
		for (int i = 1; i <= itemsConsumedEachDay.size(); i++) {
			System.out.printf("%2d ", i);
		}
		System.out.printf("%n");
		System.out.print("Available:\t"); 

		//TODO Off by one error? 
		//TODO confirm page fit
		for (int i = 1; i <= itemsConsumedEachDay.size(); i++) {
			int val = itemsPerDay - itemsConsumedEachDay.get(i);
			System.out.printf("%2d ", val);

		}
	}
}
