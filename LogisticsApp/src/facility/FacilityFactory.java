package facility;

import java.util.ArrayList;
import java.util.HashMap;

import facility.exceptions.NoSuchFacilityException;
import facility.impl.FacilityRegImpl;
import facility.interfaces.Facility;
import facility.loader.FacilityLoaderHelper;

public class FacilityFactory {

	//createFacility("Regular", facCity, facState, facItemsPerDay, facCostPerDay, connections, inventory)
	
	private FacilityFactory() {}; // empty constructor as methods are static
	
	public static Facility createFacility(String type, String cityIn, String stateIn, int ipdIn, double cpdIn, 
									ArrayList<FacilityLoaderHelper> connectionsIn, HashMap<String, Integer> inventoryIn) throws NoSuchFacilityException{
		if (type.equals("Regular")) {
			return new FacilityRegImpl(cityIn, stateIn, ipdIn, cpdIn, connectionsIn, inventoryIn); 
		}
		else throw new NoSuchFacilityException("Facility type :" + type + " Does Not Exist");
	}
}
