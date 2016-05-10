package facility.graph.interfaces;

import java.util.List;

import facility.graph.helpers.NeighborNode;
import item.exceptions.NoSuchItemException;

public interface EdgeWeightedGraph<T> {

	void addNode(T nodeId);
	void addEdge(T nodeIdFirst, T nodeIdSecond, int edgeWeight) throws NoSuchItemException;
	List<NeighborNode<T>> getNeighbors(T nodeId);
	
}
