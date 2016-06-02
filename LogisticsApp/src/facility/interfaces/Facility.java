package facility.interfaces;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import facility.helpers.FacilityNeighborHelper;
import item.exceptions.NoSuchItemException;
import order.DTO.QuoteDTO;

public interface Facility {
	
	public void printReport();
	public List<FacilityNeighborHelper> getConnectingFacilities();
	public ArrayList<Integer> getSchedule();
	public String getState();
	public String getCity();
	public String getUniqueIdentifier();
	public int getItemsPerDay();
	public double getCostPerDay();
	public String toString();
	public int getDayOrderWillComplete(int startDay, int itemsInBatch);
	public HashMap<String, Integer> getActiveItems();
	public HashMap<String, Integer> getDepletedItems();
	public void commitQuote(QuoteDTO quote) throws InvalidParameterException, NoSuchItemException;

}
