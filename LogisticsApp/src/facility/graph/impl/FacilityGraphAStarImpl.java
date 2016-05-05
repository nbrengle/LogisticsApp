package facility.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityGraph;
import facility.graph.FacilityGraphHelper;
import facility.graph.interfaces.FacilityGraphPathFinder;

public class FacilityGraphAStarImpl implements FacilityGraphPathFinder {
	
    // The set of nodes already evaluated.
    //closedSet := {}
	HashSet<String> closedSet; //<ConnectionId, Distance>
	
	// The set of currently discovered nodes still to be evaluated.
    // Initially, only the start node is known.
    //openSet := {start}
	PriorityQueue<FacilityGraphHelper> openSet; //<ConnectionId, Distance>
	
    // Will be the reversed path
    HashMap<FacilityGraphHelper, FacilityGraphHelper> reversedSolution;
    
    // For each node, the cost of getting from the start node to that node.
    HashMap<FacilityGraphHelper, Integer> gScore;
	
	public FacilityGraphAStarImpl() {
		closedSet = new HashSet<>();
		openSet = new PriorityQueue<>();
		reversedSolution = new HashMap<>();
		gScore = new HashMap<>();
	}; 
	
	@Override
	public ArrayList<FacilityGraphHelper> findBestPath(String start, String end) {
		
		closedSet.clear();
		openSet.clear();
		reversedSolution.clear();
		gScore.clear();
		
		//add the start node into the PriorityQueue
		FacilityGraphHelper startNode = new FacilityGraphHelper(start,0);
		openSet.add(startNode);
		gScore.put(startNode, 0);
		
		while (!openSet.isEmpty()) {
			FacilityGraphHelper current = openSet.poll();
			if (current.getUniqueIdentifier() == end) {
				return reversePath(reversedSolution, current);
			}
			closedSet.add(current.getUniqueIdentifier());
			
			ArrayList<FacilityGraphHelper> currentNeighbors = null;
			try {
				currentNeighbors = FacilityGraph.getInstance().getNeighbors(current.getUniqueIdentifier());
			} catch (InvalidParameterException e) {
				e.printStackTrace();
			}
			
			for (FacilityGraphHelper neighbor : currentNeighbors) {
     
                if (closedSet.contains(neighbor)) continue;

                if (!gScore.containsKey(neighbor))
                	gScore.put(neighbor,9999999);
                
                int g = gScore.get(neighbor);
                int distanceBetweenTwoNodes = neighbor.getDistance();
                int tentativeG = distanceBetweenTwoNodes + g;

                if (tentativeG < gScore.get(neighbor)) {
                    if (gScore.containsKey(neighbor))
                    	gScore.replace(neighbor, tentativeG);

                    //path.put(neighbor.getNodeId(), nodeData.getNodeId());
                    reversedSolution.put(neighbor, current);
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
		}
		
		return new ArrayList<FacilityGraphHelper>();
	}
	

	private ArrayList<FacilityGraphHelper> reversePath(HashMap<FacilityGraphHelper, FacilityGraphHelper> backwardsPath, 
														FacilityGraphHelper node) {
	    ArrayList<FacilityGraphHelper> totalPath = new ArrayList<>();
	    totalPath.add(node);
	    while (backwardsPath.containsKey(node)) {
	    	node = backwardsPath.get(node);
	        totalPath.add(node);
	    }
	    return totalPath;
	}
	
	private int pathLength(ArrayList<FacilityGraphHelper> nodes) {
		int accumulator = 0;
		for (FacilityGraphHelper node : nodes) {
			accumulator += node.getDistance();
		}
		return accumulator;
	}
	
	@Override
	public int getBestPathLength(String start, String end) {
		return pathLength(findBestPath(start, end));
	}
	
	//equivalent to calling findBestPath but includes a print step
	//TODO consider re-imaging this with STREAMS!
	@Override
	public void printBestPath(String start, String end) {
		//Santa Fe, NM to Chicago, IL:
		//	- Santa Fe, NM->St. Louis, MO->Chicago, IL = 1,329 mi 
		//	- 1,329 mi / (8 hours per day * 50 mph) = 3.32 days
		
		ArrayList<FacilityGraphHelper> pathElems = findBestPath(start, end);
		System.out.println(start + " to " + end + ":");
		System.out.print("\t- " + start + "->");
		for (FacilityGraphHelper elem : pathElems) { 
			if (!elem.getUniqueIdentifier().equals(start) && !elem.getUniqueIdentifier().equals(end)) 
				System.out.print(elem.getUniqueIdentifier() + "->");
			}
		int totalDist = pathLength(pathElems);
		int hoursPerDay = 8; //TODO consider making me a constant much higher up in the stack
		int milesPerHour = 50; //TODO consider making me a constant much higher up in the stack
		double daysNecessary = totalDist / (hoursPerDay * milesPerHour);
		System.out.printf("%s = %,d mi%n", end, totalDist); //TODO confirm this linebreak character
		System.out.printf("\t- %,d mi / (%d hours per day * %d mph) = %.2f days%n",
							totalDist, hoursPerDay, milesPerHour, daysNecessary);
	}

}
