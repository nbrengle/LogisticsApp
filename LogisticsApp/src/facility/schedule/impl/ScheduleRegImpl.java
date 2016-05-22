package facility.schedule.impl;

//TODO consider moving me to the java.security.InvalidParameterException
import facility.exceptions.InvalidParameterException;
import facility.schedule.interfaces.Schedule;

public class ScheduleRegImpl implements Schedule {

	private int itemsOrdered;
	private int itemsPerDay;

	public ScheduleRegImpl (int itemsPerDayIn) throws InvalidParameterException  {
			setItemsPerDay(itemsPerDayIn);
			this.itemsOrdered = 0;
	}
	
	private void setItemsPerDay(int valIn) throws InvalidParameterException {
		//this exists to do more complex validation if needed at some point
		if (valIn > 0) 
			this.itemsPerDay = valIn;
		
		//TODO consider a more specific exception
		else throw new InvalidParameterException(valIn + "is not a valid number of items per day for a schedule");
	}
	
	//method of testing when an item batch could be completed
	public int dayComplete(int itemsInBatch) {
		int wholeDays = Math.floorDiv((itemsInBatch + itemsOrdered), itemsPerDay);
		int remainder = (itemsInBatch + itemsOrdered) % itemsPerDay;
		if (remainder > 0) return wholeDays + 1;
		else return wholeDays;
	}
	
	//method for adding items to the schedule
	public void scheduleBatch(int itemsInBatch) {
		
	}
	//method for reporting how many days are consumed
	//method of printing days by amount consumed
	
	@Override
	public void printReport(int valIn) {
		System.out.println("Schedule:");
		System.out.printf("%-11s\t","Date:"); 
		for (int i = 1; i <= valIn; i++) {
			System.out.printf("%2d ", i);
		}
		System.out.printf("%n");
		System.out.print("Available:\t"); 
		
		int temp = itemsOrdered;
		for (int i = 1; i <= valIn; i++) {
			if (temp > itemsPerDay) {
				temp = temp - itemsPerDay;
				System.out.printf("%2d ", 0);
			}
			else if (temp > 0 && temp < itemsPerDay) {
				int val = itemsPerDay - temp;
				temp = temp - itemsPerDay;
				System.out.printf("%2d ", val);
			}
			else { //temp <= 0s
				System.out.printf("%2d ", itemsPerDay);
			}
		}
	}
}
