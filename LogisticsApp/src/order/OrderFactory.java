package order;

import java.util.ArrayList;
import java.util.HashMap;

import facility.exceptions.InvalidParameterException;
import facility.exceptions.NoSuchFacilityException;
import facility.exceptions.NoSuchInventoryException;
import facility.exceptions.NoSuchScheduleException;
import facility.helpers.FacilityNeighborHelper;
import facility.impl.FacilityRegImpl;
import facility.interfaces.Facility;
import item.exceptions.NoSuchItemException;

public class OrderFactory {
	//TODO optimizations include creating an abstract factory that works smarter than this
	
	private OrderFactory() {}; // empty constructor as methods are static
	
	//OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items)
	public static Facility createFacility(
										  String type, 
										  String orderId, 
										  int startDay, 
										  String destCity, 
										  String destState, 
										  HashMap<String, Integer> items
										  ) {
		if (type.equals("Regular")) {
			return new FacilityRegImpl(cityIn, stateIn, ipdIn, cpdIn, connectionsIn, inventoryIn); 
		}
		else throw new NoSuchFacilityException("Facility type :" + type + " Does Not Exist");
	}
}
