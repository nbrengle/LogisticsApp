package facility.schedule;

import facility.schedule.impl.ScheduleRegImpl;
import facility.schedule.interfaces.Schedule;
import item.exceptions.NoSuchItemException;

public class ScheduleFactory {
	private ScheduleFactory() {}; // empty constructor as methods are static
	
	public static Schedule createSchedule(String type, int ItemsPerDayIn) throws NoSuchItemException{
		if (type.equals("Regular")) 
			return new ScheduleRegImpl(ItemsPerDayIn); 
		
		//TODO correct this exception
		else throw new NoSuchItemException("Item type :" + type + " Does Not Exist");
	}
}
