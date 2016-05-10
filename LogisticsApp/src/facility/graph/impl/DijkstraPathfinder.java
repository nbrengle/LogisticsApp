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

import facility.graph.GraphDijkstra;
import facility.graph.NeighborNode;
import facility.graph.NodeData;

/**
 * based on http://codereview.stackexchange.com/questions/38376/a-search-algorithm
 */

public class DijkstraPathfinder<T> {

	private GraphDijkstra<T> graph = null;
	
	//TODO improve this constructor
	public DijkstraPathfinder (GraphDijkstra<T> graphIn) {
		setGraph(graphIn); 
	}
	
	private void setGraph(GraphDijkstra<T> graphIn) {
		// TODO include validation!
		this.graph = graphIn;
	}
	
	public List<T> dijkstra(T start, T end) {
		
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
}
