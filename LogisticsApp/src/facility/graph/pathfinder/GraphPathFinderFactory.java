package facility.graph.pathfinder;

import facility.exceptions.NoSuchPathFinderException;
import facility.graph.pathfinder.impl.DijkstraPathFinder;
import facility.graph.pathfinder.interfaces.GraphPathFinder;


public class GraphPathFinderFactory {

	//createFacility("Regular", facCity, facState, facItemsPerDay, facCostPerDay, connections, inventory)
	
	private GraphPathFinderFactory() {}; // empty constructor as methods are static
	
	public static GraphPathFinder createPathFinder(String type) throws NoSuchPathFinderException{
		if (type.equals("Dijkstra")) 
			return new DijkstraPathFinder(); 
		else throw new NoSuchPathFinderException("Pathfinder type :" + type + " Does Not Exist");
	}
}
