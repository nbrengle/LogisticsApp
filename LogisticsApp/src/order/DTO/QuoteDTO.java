package order.DTO;

import java.security.InvalidParameterException;

import facility.FacilityService;
import item.ItemService;

public class QuoteDTO {

	private String source;
	private String itemName;
	private int numItems; 
	private int endDay;
	private int startDay;
	private int travelTime; 
	private int arrivalDay;
	
	//These are what I actually need back from the Quote!
	//Source: Norfolk, VA
	//Target Item : ItemID
	//Number of Items: 50
	//Processing End Day: 5
	//Travel Time: 4d
	//Arrival Day: 9
	
	public QuoteDTO(String source, 
					String itemName,
					int numItems,
					int startDay,
					int endDay, 
					int travelTime, 
					int arrivalDay) {
		setSource(source);
		setItemName(itemName);
		setNumItems(numItems);
		setEndDay(endDay);
		setStartDay(startDay);
		setTravelTime(travelTime);
		setArrivalDay(arrivalDay);
	}

	public int getNumItems() {
		return numItems;
	}

	private void setNumItems(int numItemsIn) {
		if (numItemsIn <= 0) throw new InvalidParameterException("distance must be > 0");
		this.numItems = numItemsIn;
	}

	public int getEndDay() {
		return endDay;
	}

	private void setEndDay(int endDayIn) {
		if (endDayIn <= 0) throw new InvalidParameterException("startDay must be > 0");
		this.endDay = endDayIn;
	}
	
	public int getStartDay() {
		return startDay;
	}

	private void setStartDay(int startDayIn) {
		if (startDayIn <= 0) throw new InvalidParameterException("startDay must be > 0");
		this.startDay = startDayIn;
	}


	public int getTravelTime() {
		return travelTime;
	}

	private void setTravelTime(int daysOfTravelIn) {
		if (daysOfTravelIn <= 0) throw new InvalidParameterException("daysOfTravel must be > 0");
		this.travelTime = daysOfTravelIn;
	}

	public int getArrivalDay() {
		return arrivalDay;
	}

	private void setArrivalDay(int arrivalDayIn) {
		if (arrivalDayIn <= 0) throw new InvalidParameterException("arrivalDay must be > 0");
		this.arrivalDay = arrivalDayIn;
	}

	public String getSource() {
		return source;
	}

	private void setSource(String source) {
		if (source.equals(null)) throw new NullPointerException("Source cannot be null");
		if (!FacilityService.getInstance().isFacility(source)) throw new InvalidParameterException("Sources, must be facilities");
		this.source = source;
	}
	
	public String getItemName() {
		return itemName;
	}

	private void setItemName(String itemName) {
		if (itemName.equals(null)) throw new NullPointerException("itemName cannot be null");
		if (!ItemService.getInstance().isItem(itemName)) throw new InvalidParameterException("Sources, must be facilities");
		this.itemName = itemName;
	}
}
