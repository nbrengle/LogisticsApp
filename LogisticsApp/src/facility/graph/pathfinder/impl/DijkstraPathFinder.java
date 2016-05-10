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
 * simplified from http://codereview.stackexchange.com/questions/38376/a-search-algorithm
 */

public class DijkstraPathFinder<T> implements GraphPathFinder<T>{

	private GraphDijkstra<T> graph = null;
	private int pathLength = 0;
	
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
		
		final Queue<NodeData<T>> open = new PriorityQueue<>();
		
		NodeData<T> startNodeData = graph.getNodeData(start);
		startNodeData.setDistance(0);
		open.add(startNodeData);
		
		final Map<T, T> path = new HashMap<T, T>();
		final Set<NodeData<T>> visited = new HashSet<>();
		
		while (!open.isEmpty()) {
			final NodeData<T> nodeData = open.poll();
			
			if (nodeData.getNodeId().equals(end)) {
				pathLength = nodeData.getDistance();
				return path(path, end);
			}
			
			visited.add(nodeData);
			
			for (NeighborNode<T> neighbor : graph.getNeighbors(nodeData.getNodeId())) {
				
				if (visited.contains(neighbor.getNodeData())) continue;
				
				int distanceBetweenTwoNodes = neighbor.getWeight();
				int tentativeG = distanceBetweenTwoNodes + nodeData.getDistance();
				
				if (tentativeG < neighbor.getNodeData().getDistance()) {
					neighbor.getNodeData().setDistance(tentativeG);
					
					//TODO I'm putting in too many nodes right now!
					path.put(neighbor.getNodeData().getNodeId(), nodeData.getNodeId());
					
					//TODO looks like a lot of extra stuff is getting put in here
					//neighbors aren't the same objects =(
					if (!open.contains(neighbor.getNodeData()))
						open.add(neighbor.getNodeData());
				}
			}
		}
		return null;
	}
	
    private List<T> path(Map<T, T> pathIn, T destination) {
        assert pathIn != null;
        assert destination != null;

        final List<T> pathList = new ArrayList<T>();
        pathList.add(destination);
        pathIn.forEach((k,v) -> pathList.add(v));
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
		
		List<T> pathElems = null;
		pathElems = findBestPath(start, end);
		System.out.println(start + " to " + end + ":");
		System.out.print("\t- " + start + "->");
		//uses overriden toString to just get name this is really fragile
		//TODO improve this 
		for (T elem : pathElems) { 
			if (pathElems == null) System.out.println("Unable to Find Shortest Path between" + start + " , " + end);
			else if (!elem.toString().equals(start) && !elem.toString().equals(end)) 
				System.out.print(elem.toString() + "->");
			}
		int totalDist = pathLength;
		double hoursPerDay = 8.00; //TODO consider making me a constant much higher up in the stack
		double milesPerHour = 50.00; //TODO consider making me a constant much higher up in the stack
		double daysNecessary = totalDist / (hoursPerDay * milesPerHour);
		System.out.printf("%s = %,d mi%n", end, totalDist); //TODO confirm this linebreak character
		System.out.printf("\t- %,d mi / (%.0f hours per day * %.0f mph) = %.2f days%n",
							totalDist, hoursPerDay, milesPerHour, daysNecessary);
	}

}
