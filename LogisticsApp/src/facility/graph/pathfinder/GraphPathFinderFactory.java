package facility.graph.pathfinder;

import facility.exceptions.NoSuchPathFinderException;
import facility.graph.impl.GraphDijkstra;
import facility.graph.interfaces.EdgeWeightedGraph;
import facility.graph.pathfinder.impl.DijkstraPathFinder;
import facility.graph.pathfinder.interfaces.GraphPathFinder;


public class GraphPathFinderFactory<T> {
	
	public GraphPathFinderFactory() {}; // empty constructor as methods are reference across it
	
	public GraphPathFinder<T> createPathFinder(String type, EdgeWeightedGraph<T> graph) throws NoSuchPathFinderException{
		if (type.equals("Dijkstra")) 
			return new DijkstraPathFinder<T>((GraphDijkstra<T>) graph); 
		else throw new NoSuchPathFinderException("Pathfinder type :" + type + " Does Not Exist");
	}
}
