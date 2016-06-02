package order.interfaces;

import java.util.HashMap;

public interface Order {

	public String getId();
	public int getStartDay();
	public String getDestinationCity();
	public String getDestinationState();
	public String getDestinationUniqueIdentifier();
	//TODO I'm bad! such a bad method, fix me!
	public HashMap<String, Integer> getItems();
	void printReport(int index);
}
