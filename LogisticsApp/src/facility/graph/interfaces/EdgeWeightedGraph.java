package facility.graph.interfaces;

import java.util.List;

import java.security.InvalidParameterException;
import facility.graph.helpers.NeighborNode;

public interface EdgeWeightedGraph<T> {

	void addNode(T nodeId);
	void addEdge(T nodeIdFirst, T nodeIdSecond, int edgeWeight);
	List<NeighborNode<T>> getNeighbors(T nodeId) throws InvalidParameterException;
	
}
