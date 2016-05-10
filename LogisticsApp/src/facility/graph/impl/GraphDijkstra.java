package facility.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import facility.graph.helpers.NodeData;
import facility.graph.helpers.NeighborNode;

/**
 * Adapted from: http://codereview.stackexchange.com/questions/38376/a-search-algorithm
 *
 * @param <T>
 */

public class GraphDijkstra<T> implements Iterable<T>{
	/*
     * A map from the nodeId to outgoing edge.
     * An outgoing edge is represented as a tuple of NodeData and the edge length
     */
	private final Map<T, Map<NodeData<T>, Integer>> graph;
    /*
     * A map between nodeId and nodeData.
     */
    private final Map<T, NodeData<T>> nodeIdNodeData;

    
    public GraphDijkstra() {
    	graph = new HashMap<T, Map<NodeData<T>, Integer>>();
    	nodeIdNodeData = new HashMap<T, NodeData<T>>();
    }
    
    public void addNode(T nodeId) {
    	if (nodeId == null) throw new NullPointerException("The node cannot be null");
    	
    	graph.put(nodeId, new HashMap<NodeData<T>, Integer>());
    	nodeIdNodeData.put(nodeId, new NodeData<T>(nodeId));
    }
    
    public void addEdge(T nodeIdFirst, T nodeIdSecond, int edgeWeight) {
    	if (nodeIdFirst == null || nodeIdSecond == null) throw new NullPointerException("Nodes cannot be null");
    	
    	if (!graph.containsKey(nodeIdFirst)) addNode(nodeIdFirst);
    	if (!graph.containsKey(nodeIdSecond)) addNode(nodeIdSecond);
    	//TODO first confirm that no such edge exists!
    	//TODO confirm that two-way edges are appropriate!
    	
    	graph.get(nodeIdFirst).put(nodeIdNodeData.get(nodeIdSecond), edgeWeight);
    }
    
    private void validateNode(T nodeId) {
    	if (nodeId == null) throw new NullPointerException("The input node must not be null");
    	if (!graph.containsKey(nodeId)) throw new NoSuchElementException(graph + " does not contain " + nodeId);
    }
    
    public List<NeighborNode<T>> getNeighbors(T nodeId) {
    	validateNode(nodeId);
    	
    	//NodeData<T>, Integer
    	List<NeighborNode<T>> returnList = new ArrayList<NeighborNode<T>>();
    	graph.get(nodeId).forEach((k,v) -> returnList.add(new NeighborNode<T>(k.getNodeId(), v)));
    	
    	return returnList;
    }
    
    public NodeData<T> getNodeData(T nodeId) {
    	validateNode(nodeId);
    	return nodeIdNodeData.get(nodeId);
    }
    
    
	@Override
	public Iterator<T> iterator() {
		return graph.keySet().iterator();
	}
	
	

}
