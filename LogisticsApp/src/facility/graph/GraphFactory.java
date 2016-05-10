package facility.graph;

import facility.exceptions.NoSuchGraphException;
import facility.graph.impl.GraphDijkstra;
import facility.graph.interfaces.EdgeWeightedGraph;

public class GraphFactory<T> {
	
	public GraphFactory() {}; // empty constructor as methods are reference across it
	
	public EdgeWeightedGraph<T> createGraph(String type) throws NoSuchGraphException{
		if (type.equals("Dijkstra")) 
			return (EdgeWeightedGraph<T>) new GraphDijkstra<T>(); 
		else throw new NoSuchGraphException("Graph type :" + type + " Does Not Exist");
	}
}
