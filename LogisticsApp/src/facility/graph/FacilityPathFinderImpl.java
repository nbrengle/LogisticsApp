package facility.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityNeighborHelper;

public class FacilityPathFinderImpl implements FacilityGraphPathfinder {
	
	//the Strings here are uniqueIdentifiers for Facilities
	private HashMap<String, FacilityNeighborHelper> pairs = new HashMap<>(); // <Facility, <Neighbor,Distance>>
	private HashSet<String> seen = new HashSet<>();
	private ArrayList<FacilityNeighborHelper> lowPath = new ArrayList<>();

	//TODO consider re-imagining this as associated with a Factory rather than as a collection of procedures
	private FacilityPathFinderImpl() { //Constructor will need to set the values as above
		pairs = new HashMap<>();
		seen = new HashSet<>();
		lowPath = new ArrayList<>();
	} 
	
	private void mapPairs(String init) {
		seen.add(init);
		
		ArrayList<FacilityNeighborHelper> neighbors = new ArrayList<>();
		try {
			neighbors.addAll(FacilityGraph.getInstance().getNeighbors(init));
		}
		catch (InvalidParameterException e) { e.printStackTrace(); }
		
		for (FacilityNeighborHelper neighbor : neighbors) {
			pairs.put(init, neighbor);
			if (!seen.contains(neighbor.getUniqueIdentifier())) 
				mapPairs(neighbor.getUniqueIdentifier());
		}
	}
	
	private int pathLength(ArrayList<FacilityNeighborHelper> nodes) {
		int accumulator = 0;
		for (FacilityNeighborHelper node : nodes) {
			accumulator += node.getDistance();
		}
		return accumulator;
	}
	
	private void findPath(String start, String end, ArrayList<FacilityNeighborHelper> pathList) {
		if (start.equals(end)) {
			int currentPathLength = pathLength(pathList);
			if (currentPathLength < pathLength(lowPath)) {
				lowPath = pathList;
				return;
			} 
			else { return; }
		}
		else {
			HashMap<String, FacilityNeighborHelper> fromHere = new HashMap<>();
			pairs.forEach ((k,v) -> {if (k.equals(start)) fromHere.put(k,v);});
			fromHere.forEach((k,v) -> {
				if (!pathList.contains(v)) {
					ArrayList<FacilityNeighborHelper> newPath = new ArrayList<>();
					for (FacilityNeighborHelper node : pathList) {
						newPath.add(new FacilityNeighborHelper(node.getUniqueIdentifier(), node.getDistance()));
					}
					newPath.add(v);
					findPath(v.getUniqueIdentifier(),end,newPath);
				}
			});
		}
	}
	
	public ArrayList<FacilityNeighborHelper> findBestPath(String start, String end) {
		mapPairs(start);
		seen.clear();
		ArrayList<FacilityNeighborHelper> pathList = new ArrayList<>();
		pathList.add(new FacilityNeighborHelper(start,0)); 
		findPath(start, end, pathList);
		return lowPath;
	}
	
	//equivalent to calling findBestPath but includes a print step
	public void printBestPath(String start, String end) {
		//Santa Fe, NM to Chicago, IL:
		//	- Santa Fe, NM->St. Louis, MO->Chicago, IL = 1,329 mi 
		//	-1,329 mi / (8 hours per day * 50 mph) = 3.32 days
		
	}
}
