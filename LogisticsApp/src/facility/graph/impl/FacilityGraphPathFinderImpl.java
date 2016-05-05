package facility.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityGraph;
import facility.graph.FacilityGraphHelper;
import facility.graph.interfaces.FacilityGraphPathFinder;

public class FacilityGraphPathFinderImpl implements FacilityGraphPathFinder {

	HashMap<String, FacilityGraphHelper> pairs;
	HashSet<String> seen;
	ArrayList<FacilityGraphHelper> lowPath;
	
	public FacilityGraphPathFinderImpl() {
		this.pairs = new HashMap<>();
		this.seen = new HashSet<>();
		this.lowPath = new ArrayList<>();
	}
	
	@Override
	public ArrayList<FacilityGraphHelper> findBestPath(String start, String end) throws InvalidParameterException {
		mapPairs(start);
		seen.clear();
		ArrayList<FacilityGraphHelper> pathList = new ArrayList<>();
		pathList.add(new FacilityGraphHelper (start, 0));
		findPaths(start, end, pathList);
		return lowPath;
	}

	private void mapPairs(String init) throws InvalidParameterException {
		seen.add(init);
		ArrayList<FacilityGraphHelper> neighbors = FacilityGraph.getInstance().getNeighbors(init);
		for (FacilityGraphHelper neighbor : neighbors) {
			pairs.put(init, neighbor);
			if (!seen.contains(neighbor.getUniqueIdentifier())) {
				mapPairs(neighbor.getUniqueIdentifier());
			}
		}
	}
	
	private void findPaths(String start, String end, ArrayList<FacilityGraphHelper> pathList) {
		if (start.equals(end)) {				
			if (lowPath.isEmpty() || (pathLength(pathList) < pathLength(lowPath)))
				lowPath = pathList;
			return;
		}
		HashMap<String, FacilityGraphHelper> fromHere = new HashMap<>();
		pairs.forEach((facilityName,connection) -> {
			if(facilityName.equals(start))
				fromHere.put(facilityName, connection);
		});
		fromHere.forEach((facilityName,connection) -> {
			if(!pathList.contains(connection)) {
				ArrayList<FacilityGraphHelper> newPath = new ArrayList<>();
				for (FacilityGraphHelper node : pathList) {
					newPath.add(node);
				}
				newPath.add(connection);
				findPaths(connection.getUniqueIdentifier(), end, newPath);
			}
		});
		return;
	}

	private int pathLength(ArrayList<FacilityGraphHelper> nodes) {
		int accumulator = 0;
		for (FacilityGraphHelper node : nodes) {
			accumulator += node.getDistance();
		}
		return accumulator;
	}
	
	@Override
	public int getBestPathLength(String start, String end) throws InvalidParameterException {
		return pathLength(findBestPath(start, end));
	}
	
	//equivalent to calling findBestPath but includes a print step
	//TODO consider re-imaging this with STREAMS!
	@Override
	public void printBestPath(String start, String end) {
		//Santa Fe, NM to Chicago, IL:
		//	- Santa Fe, NM->St. Louis, MO->Chicago, IL = 1,329 mi 
		//	- 1,329 mi / (8 hours per day * 50 mph) = 3.32 days
		
		ArrayList<FacilityGraphHelper> pathElems = null;
		try {
			pathElems = findBestPath(start, end);
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		}
		System.out.println(start + " to " + end + ":");
		System.out.print("\t- " + start + "->");
		for (FacilityGraphHelper elem : pathElems) { 
			if (!elem.getUniqueIdentifier().equals(start) && !elem.getUniqueIdentifier().equals(end)) 
				System.out.print(elem.getUniqueIdentifier() + "->");
			}
		int totalDist = pathLength(pathElems);
		double hoursPerDay = 8.00; //TODO consider making me a constant much higher up in the stack
		double milesPerHour = 50.00; //TODO consider making me a constant much higher up in the stack
		double daysNecessary = totalDist / (hoursPerDay * milesPerHour);
		System.out.printf("%s = %,d mi%n", end, totalDist); //TODO confirm this linebreak character
		System.out.printf("\t- %,d mi / (%.0f hours per day * %.0f mph) = %.2f days%n",
							totalDist, hoursPerDay, milesPerHour, daysNecessary);
	}

}
