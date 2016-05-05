package facility.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityGraph;
import facility.graph.FacilityGraphHelper;
import facility.graph.interfaces.FacilityGraphPathFinder;

public class FacilityGraphPathFinderImpl implements FacilityGraphPathFinder {

	HashMap<String, FacilityGraphHelper> pairs;
	PriorityQueue<String> toCheck;
	HashSet<String> seen;
	ArrayList<FacilityGraphHelper> lowPath;
	
	public FacilityGraphPathFinderImpl() {
		this.pairs = new HashMap<>();
		this.toCheck = new PriorityQueue<>();
		this.seen = new HashSet<>();
		this.lowPath = new ArrayList<>();
	}
	
	@Override
	public ArrayList<FacilityGraphHelper> findBestPath(String start, String end) throws InvalidParameterException {
		FacilityGraphHelper selfLoop = new FacilityGraphHelper (start, 0);
		toCheck.add(start);
		mapPairs(toCheck);
		System.out.print(pairs);
		seen.clear();
		ArrayList<FacilityGraphHelper> pathList = new ArrayList<>();
		pathList.add(selfLoop);
		findPaths(start, end, pathList);
		return lowPath;
	}

	private void mapPairs(PriorityQueue<String> toCheck) throws InvalidParameterException {
		while (!toCheck.isEmpty()) {
			String current = toCheck.poll();
			seen.add(current);
			ArrayList<FacilityGraphHelper> neighbors = FacilityGraph.getInstance().getNeighbors(current);
			for (FacilityGraphHelper neighbor : neighbors) {
				System.out.print(current + neighbor);
				pairs.put(current, neighbor);
				if(!seen.contains(neighbor.getUniqueIdentifier()))
					toCheck.add(neighbor.getUniqueIdentifier());
			}
		}
		
/*		seen.put(init);
		ArrayList<FacilityGraphHelper> neighbors = FacilityGraph.getInstance().getNeighbors(init);
		for (FacilityGraphHelper neighbor : neighbors) {
			pairs.put(init, neighbor);
			//if see doesn't have my childnode, call seen on my childnode
			if (!seen.get) {
				mapPairs(neighbor.getUniqueIdentifier());
			}
		}*/
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
