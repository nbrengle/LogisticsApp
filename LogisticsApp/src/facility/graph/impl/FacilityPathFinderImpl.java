package facility.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import facility.exceptions.InvalidParameterException;
import facility.graph.interfaces.FacilityGraphPathFinder;
import facility.graph.FacilityGraph;
import facility.graph.FacilityNeighborHelper;

public class FacilityPathFinderImpl implements FacilityGraphPathFinder {
	
	//the Strings here are uniqueIdentifiers for Facilities
	private HashMap<String, FacilityNeighborHelper> pairs; // <Facility, <Neighbor,Distance>>
	private HashSet<String> seen;
	private ArrayList<FacilityNeighborHelper> lowPath;

	public FacilityPathFinderImpl() { 
		this.pairs = new HashMap<>();
		this.seen = new HashSet<>();
		this.lowPath = new ArrayList<>();
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
			pairs.forEach ((k,v) -> {
				if (k.equals(start)) 
					fromHere.put(k,v);
				}
			);
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
	
	public int getBestPathLength(String start, String end) {
		return pathLength(findBestPath(start, end));
	}
	
	//equivalent to calling findBestPath but includes a print step
	//TODO consider re-imaging this with STREAMS!
	public void printBestPath(String start, String end) {
		//Santa Fe, NM to Chicago, IL:
		//	- Santa Fe, NM->St. Louis, MO->Chicago, IL = 1,329 mi 
		//	- 1,329 mi / (8 hours per day * 50 mph) = 3.32 days
		
		ArrayList<FacilityNeighborHelper> pathElems = findBestPath(start, end);
		System.out.println(start + " to " + end + ":");
		System.out.print("\t- " + start + "->");
		pathElems.forEach(elem -> { 
			if (!elem.getUniqueIdentifier().equals(start) && !elem.getUniqueIdentifier().equals(end)) 
				System.out.print(elem.getUniqueIdentifier() + "->");
			});
		int totalDist = pathLength(pathElems);
		int hoursPerDay = 8; //TODO consider making me a constant much higher up in the stack
		int milesPerHour = 50; //TODO consider making me a constant much higher up in the stack
		double daysNecessary = totalDist / (hoursPerDay * milesPerHour);
		System.out.printf("%s = '%,d' mi%n", end, totalDist); //TODO confirm this linebreak character
		System.out.printf("\t- '%,d' mi / (%d hours per day * %d mph) = '%.2f' days%n",
							totalDist, hoursPerDay, milesPerHour, daysNecessary);

		
	}
}
