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
	
	@Override
	public void scheduleItems(int startDay, int itemsInBatch) {
		int temp = itemsInBatch, tempStart = startDay;
		if (itemsConsumedEachDay.get(startDay) > 0) {
			tempStart++;
			temp = temp - itemsConsumedEachDay.get(startDay);
			itemsConsumedEachDay.set(startDay, itemsPerDay);
		}
		//TODO Off by one error? 
		for (int i = tempStart; i < itemsConsumedEachDay.size(); i++){
			if (temp > itemsPerDay) {
				itemsConsumedEachDay.set(i, itemsPerDay);
				temp = temp - itemsPerDay;
			}
			else if ( 0 < temp && temp < itemsPerDay) {
				itemsConsumedEachDay.set(i, temp);
			}
		}
	}
	
	//method for reporting how many days are consumed
	@Override
	public ArrayList<Integer> getSchedule() {
		ArrayList<Integer> returnList = new ArrayList<>();
		//TODO Off by one error? 
		for (int i = 1; i < itemsConsumedEachDay.size() ; i++ ) {
			returnList.add(itemsPerDay - itemsConsumedEachDay.get(i));
		}
		return returnList;
	}
	
	
	/*
	 * Schedule:
	 * Day:
	 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
	 * Available:
	 * 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10
	 */
	
	private void fillConsumed (int valIn) {
		if (itemsConsumedEachDay.size() < valIn) {
			for (int i = 0 ; i < valIn +1; i++) itemsConsumedEachDay.add(0);
		}
	}
	
	@Override
	public void printReport(int valIn) {
		fillConsumed(valIn);
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
