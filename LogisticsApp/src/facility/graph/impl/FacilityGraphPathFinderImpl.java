package facility.graph.impl;

import java.util.ArrayList;
import java.util.HashSet;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityGraph;
import facility.graph.FacilityGraphHelper;
import facility.graph.NodePair;
import facility.graph.WeightedNodePair;
import facility.graph.interfaces.FacilityGraphPathFinder;

public class FacilityGraphPathFinderImpl implements FacilityGraphPathFinder {

	ArrayList<WeightedNodePair> pairs;
	HashSet<String> seen;
	ArrayList<NodePair> lowPath;
	
	public FacilityGraphPathFinderImpl() {
		this.pairs = new ArrayList<>();
		this.seen = new HashSet<>();
		this.lowPath = new ArrayList<>();
	}
	
	@Override
	public ArrayList<NodePair> findBestPath(String start, String end) throws InvalidParameterException {
		mapPairs(start);
		seen.clear();
		ArrayList<NodePair> pathList = new ArrayList<>();
		pathList.add(new NodePair (start,start));
		findPaths(start, end, pathList);
		return lowPath;
	}

	private void mapPairs(String init) throws InvalidParameterException {
		seen.add(init);
		ArrayList<FacilityGraphHelper> neighbors = FacilityGraph.getInstance().getNeighbors(init);
		for (FacilityGraphHelper neighbor : neighbors) {
			pairs.add(new WeightedNodePair(init,neighbor.getUniqueIdentifier(),neighbor.getDistance()));
			if(!seen.contains(neighbor.getUniqueIdentifier()))
				mapPairs(neighbor.getUniqueIdentifier());
		}
	}
		
	private void findPaths(String start, String end, ArrayList<NodePair> pathList) {
		if (start.equals(end)) {				
			try {
				if (lowPath.isEmpty() || (pathLength(pathList) < pathLength(lowPath)))
					lowPath = copyPath(pathList);
			} catch (InvalidParameterException e) {
				e.printStackTrace();
			}
			return;
		}
		HashSet<WeightedNodePair> fromHere = new HashSet<>();
		for (WeightedNodePair pair : pairs) {
			if(pair.getNode().equals(start))
				fromHere.add(pair);
		}
		for (WeightedNodePair pair : fromHere) {
			NodePair connection = new NodePair(pair.getNode(),pair.getConnection());
			if (!pathList.contains(connection)) { //TODO may need to OR this to the reverse of the connection!
				ArrayList<NodePair> newPath = copyPath(pathList);
				newPath.add(new NodePair(connection.getConnection(),connection.getConnection()));
				findPaths(connection.getConnection(),end,newPath);
			}
			
		}
		
		return;
	}

	private ArrayList<NodePair> copyPath(ArrayList<NodePair> pathNodes) {
		ArrayList<NodePair> returnArray = new ArrayList<>();
		for (NodePair node : pathNodes) {
			returnArray.add(node);
		}
		return returnArray;
	}

	private int pathLength(ArrayList<NodePair> nodes) throws InvalidParameterException {
		int accumulator = 0;
		for (NodePair node : nodes) {
			accumulator += FacilityGraph.getInstance().getEdgeWeight(node.getNode(), node.getConnection());
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
		
		ArrayList<NodePair> pathElems = null;
		try {
			pathElems = findBestPath(start, end);
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		}
		System.out.println(start + " to " + end + ":");
		System.out.print("\t- " + start + "->");
		for (NodePair elem : pathElems) { 
			if (!elem.getNode().equals(start) && !elem.getNode().equals(end)) 
				System.out.print(elem.getNode() + "->");
			}
		int totalDist = 0;
		try {
			totalDist = pathLength(pathElems);
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		}
		double hoursPerDay = 8.00; //TODO consider making me a constant much higher up in the stack
		double milesPerHour = 50.00; //TODO consider making me a constant much higher up in the stack
		double daysNecessary = totalDist / (hoursPerDay * milesPerHour);
		System.out.printf("%s = %,d mi%n", end, totalDist); //TODO confirm this linebreak character
		System.out.printf("\t- %,d mi / (%.0f hours per day * %.0f mph) = %.2f days%n",
							totalDist, hoursPerDay, milesPerHour, daysNecessary);
	}

}
