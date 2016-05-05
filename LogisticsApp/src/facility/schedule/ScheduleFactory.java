package facility.schedule;

import facility.exceptions.NoSuchScheduleException;
import facility.schedule.impl.ScheduleRegImpl;
import facility.schedule.interfaces.Schedule;

public class ScheduleFactory {
	private ScheduleFactory() {}; // empty constructor as methods are static
	
	public static Schedule createSchedule(String type, int ItemsPerDayIn) throws NoSuchScheduleException{
		if (type.equals("Regular")) 
			return new ScheduleRegImpl(ItemsPerDayIn); 
		
		//TODO correct this exception
		else throw new NoSuchScheduleException("Schedule type :" + type + " Does Not Exist");
	}
}
