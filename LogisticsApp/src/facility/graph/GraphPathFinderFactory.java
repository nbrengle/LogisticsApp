package facility.graph;

import facility.exceptions.NoSuchPathFinderException;
import facility.graph.impl.FacilityGraphPathFinderImpl;
import facility.graph.interfaces.FacilityGraphPathFinder;

public class GraphPathFinderFactory {

	//createFacility("Regular", facCity, facState, facItemsPerDay, facCostPerDay, connections, inventory)
	
	private GraphPathFinderFactory() {}; // empty constructor as methods are static
	
	public static GraphPathFinder createPathFinder(String type) throws NoSuchPathFinderException{
		if (type.equals("Regular")) 
			return new FacilityGraphPathFinderImpl(); 
		else throw new NoSuchPathFinderException("Pathfinder type :" + type + " Does Not Exist");
	}
}
