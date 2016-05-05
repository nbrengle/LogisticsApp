package facility.graph;

import facility.exceptions.NoSuchPathFinderException;
import facility.graph.impl.FacilityGraphAStarImpl;
import facility.graph.interfaces.FacilityGraphPathFinder;

public class FacilityGraphPathFinderFactory {

	//createFacility("Regular", facCity, facState, facItemsPerDay, facCostPerDay, connections, inventory)
	
	private FacilityGraphPathFinderFactory() {}; // empty constructor as methods are static
	
	public static FacilityGraphPathFinder createFacilityPathFinder(String type) throws NoSuchPathFinderException{
		if (type.equals("Regular")) 
			return new FacilityGraphAStarImpl(); 
		else throw new NoSuchPathFinderException("Pathfinder type :" + type + " Does Not Exist");
	}
}
