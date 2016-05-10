package facility.graph.pathfinder.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import facility.exceptions.InvalidParameterException;
import facility.graph.helpers.NeighborNode;
import facility.graph.helpers.NodeData;
import facility.graph.impl.GraphDijkstra;
import facility.graph.pathfinder.interfaces.GraphPathFinder;


/**
 * simplified from on http://codereview.stackexchange.com/questions/38376/a-search-algorithm
 */

public class DijkstraPathFinder<T> implements GraphPathFinder<T>{

	private GraphDijkstra<T> graph = null;
	
	//TODO improve this constructor
	public DijkstraPathFinder (GraphDijkstra<T> graphIn) {
		setGraph(graphIn); 
	}
	
	private void setGraph(GraphDijkstra<T> graphIn) {
		// TODO include validation!
		this.graph = graphIn;
	}
	
	@Override
	public List<T> findBestPath(T start, T end) {
		
		final Queue<NodeData<T>> open = new PriorityQueue<NodeData<T>>();
		
		NodeData<T> startNodeData = graph.getNodeData(start);
		startNodeData.setDistance(0);
		open.add(startNodeData);
		
		final Map<T, T> path = new HashMap<T, T>();
		final Set<NodeData<T>> closed = new HashSet<NodeData<T>>();
		
		while (!open.isEmpty()) {
			final NodeData<T> nodeData = open.poll();
			if (nodeData.getNodeId().equals(end)) {
				return path(path, end);
			}
			
			closed.add(nodeData);
			
			for (NeighborNode<T> neighbor : graph.getNeighbors(nodeData.getNodeId())) {
				
				if (!closed.contains(neighbor)) continue;
				
				int distanceBetweenTwoNodes = neighbor.getWeight();
				int tentativeG = distanceBetweenTwoNodes + nodeData.getDistance();
				
				if (tentativeG <neighbor.getWeight()) {
					neighbor.getNodeData().setDistance(tentativeG);
					
					path.put(neighbor.getNodeData().getNodeId(), nodeData.getNodeId());
					if (!open.contains(neighbor.getNodeData()))
						open.add(neighbor.getNodeData());
				}
			}
		}
		return null;
	}
	
	private List<T> path(Map <T, T> path, T end) {
		assert path != null; //TODO do this differently
		assert end != null;
		
		final List<T> pathList = new ArrayList<T>();
		pathList.add(end);
		while (path.containsKey(end)) {
			end = path.get(end);
			pathList.add(end);
		}
		Collections.reverse(pathList);
		return pathList;
	}
	
	@Override
	public int getBestPathLength(T init, T dest) throws InvalidParameterException {
		//TODO Implement me!
		return 0;
	}
	
	//equivalent to calling findBestPath but includes a print step
	//TODO consider re-imaging this with STREAMS!
	@Override
	public void printBestPath(T start, T end) {
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
