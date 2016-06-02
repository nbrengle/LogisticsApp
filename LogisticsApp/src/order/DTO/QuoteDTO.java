package order.DTO;

import java.security.InvalidParameterException;

public class QuoteDTO {

	private int distance;
	private int daysNecessary;
	private int daysOfTravel;
	private int arrivalDay;
	
	public QuoteDTO(int distance,
					int daysNecessary,
					int daysOfTravel,
					int arrivalDay) {
		setDistance(distance);
		setDaysNecessary(daysNecessary);
		setDaysOfTravel(daysOfTravel);
		setArrivalDay(arrivalDay);
	}

	public int getDistance() {
		return distance;
	}

	private void setDistance(int distanceIn) {
		if (distanceIn <= 0) throw new InvalidParameterException("distance must be > 0");
		this.distance = distanceIn;
	}

	public int getDaysNecessary() {
		return daysNecessary;
	}

	private void setDaysNecessary(int daysNecessaryIn) {
		if (daysNecessaryIn <= 0) throw new InvalidParameterException("daysNecessary must be > 0");
		this.daysNecessary = daysNecessaryIn;
	}

	public int getDaysOfTravel() {
		return daysOfTravel;
	}

	private void setDaysOfTravel(int daysOfTravelIn) {
		if (daysOfTravelIn <= 0) throw new InvalidParameterException("daysOfTravel must be > 0");
		this.daysOfTravel = daysOfTravelIn;
	}

	public int getArrivalDay() {
		return arrivalDay;
	}

	private void setArrivalDay(int arrivalDayIn) {
		if (arrivalDayIn <= 0) throw new InvalidParameterException("arrivalDay must be > 0");
		this.arrivalDay = arrivalDayIn;
	}
}
