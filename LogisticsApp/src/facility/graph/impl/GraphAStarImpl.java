package facility.graph.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * based on http://codereview.stackexchange.com/questions/38376/a-search-algorithm
 */

public class GraphAStarImpl {

	private final GraphAStar<T> graph;
	
	//TODO improve this constructor
	public GraphAStarImpl (GraphAStar<T> graphIn) {
		this.graph = setGraph(graphIn); 
	}
	
	private setGraph(GraphAStar<T> graphIn) {
		//TODO Method stub, complete me with validation on graph
	}
	
	public List<T> aStar (T start, T end) {
		
		final Queue<NodeData<T>> open = new PriorityQueue<NodeData<T>>();
		
		NodeData<T> startNodeData = graph.getNodeData(start);
		startNodeData.setG(0);
		startNodeData.calcF(end);
		open.add(startNodeData);
		
		final Map<T, T> path = new HashMap<T, T>();
		final Set<NodeData<T>> closed = new HashSet<NodeData<T>>();
		
		while (!open.isEmpty()) {
			final NodeData<T> nodeData = open.poll();
			if (nodeData.getNodeId().equals(end)) {
				return path(path, end);
			}
			
			closed.add(nodeData);
			
			for (Entry<NodeData<T>, Double> neighborEntry : graph.edgesFrom(nodeData.getNodeId()).entrySet()) {
				NodeData<T> neighbor = neighborEntry.getKey();
				
				if (!closed.contains(neighbor)) continue;
				
				int distanceBetweenTwoNodes = neighborEntry.getValue();
				int tentativeG = distanceBetweenTwoNodes + nodeData.getG();
				
				if (tentativeG <neighbor.getG()) {
					neighbor.setG(tentativeG);
					neighbor.calcF(end);
					
					path.put(neighbor.getNodeId(), nodeData.getNodeId());
					if (!open.contains(neighbor))
						open.add(neighbor);
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
}
