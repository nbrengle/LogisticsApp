package facility.graph.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityGraph;
import facility.graph.FacilityGraphHelper;
import facility.graph.NodePair;
import facility.graph.interfaces.FacilityGraphPathFinder;

public class FacilityGraphPathFinderImpl implements FacilityGraphPathFinder {

	HashSet<String> seen;
	HashSet<String> visited;
	ArrayList<NodePair> lowPath;
	
	PriorityQueue<String> q; //vertex FacilityName
	ArrayList<NodePair> path; //vertex FacilityName
	
	public FacilityGraphPathFinderImpl() {
		this.visited = new HashSet<>();
		this.lowPath = new ArrayList<>();
		this.q = new PriorityQueue<>();
		this.path = new ArrayList<>();
	}
	
	@Override
	public ArrayList<NodePair> findBestPath(String start, String end) throws InvalidParameterException {
		ArrayList<NodePair> pathList = new ArrayList<>();
		pathList.add(new NodePair (start,start));
		findPaths(start, end);
		return lowPath;
	}
		
	private void findPaths(String current, String goal) throws InvalidParameterException {

		q.add(current);
		visited.add(current);

		while (! q.isEmpty() )
		{
		    String node = q.poll();
		    visited.add(node);

		    if (node == goal)
		    {
		        lowPath = reversePath(path,goal,current);
		        return;
		    }
		    ArrayList<FacilityGraphHelper> neighbors = FacilityGraph.getInstance().getNeighbors(current);
		    for (FacilityGraphHelper child : neighbors) {
			    if (! (child == null) ) {
			    	if (!visited.contains(child.getUniqueIdentifier())) {
			    	   path.add(new NodePair(current, child.getUniqueIdentifier()));
			       }
			    }
		    }
		}
	}

	private ArrayList<NodePair> reversePath(ArrayList<NodePair> path,String goal, String start) {
		Stack<NodePair> s = new Stack<>();
		String name = goal;
		while (name != start)
			for (NodePair test : path) {
				if (test.getConnection() == name) {
					name = test.getNode();
					s.push(new NodePair(test.getConnection(),test.getNode())); 
				}
			}
		ArrayList<NodePair> returnArray = new ArrayList<>();
		while (!s.isEmpty()) {
			returnArray.add(s.pop());
		}
		return returnArray;
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
			if (node.getNode().equals(node.getConnection()))
				accumulator += 0;
			else
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
