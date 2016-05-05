package facility.graph;

import java.util.ArrayList;
import java.util.HashMap;

import facility.exceptions.InvalidParameterException;
import facility.exceptions.NoSuchPathFinderException;
import facility.graph.interfaces.FacilityGraphPathFinder;

/**
 * adapted from: http://introcs.cs.princeton.edu/java/45graph/Graph.java.html
 */

public class FacilityGraph implements FacilityGraphPathFinder{
	
	//---Data Members---
	//hashmap: key = vertex, value = hashmap of neighboring vertices and their integer edgeweight
	private HashMap<String, HashMap<String, Integer>> st;
	
	//number of edges
	private int numEdges;
	//------------------
	
	//FacilityGraphPathFinder implementation
	private FacilityGraphPathFinder pathFinder;
	
	
	//---Singleton Constructor---
	private volatile static FacilityGraph ourInstance;
	
	private FacilityGraph() {
		this.st = new HashMap<String, HashMap<String, Integer>>();
		this.numEdges = 0;
		try {
			this.pathFinder = FacilityGraphPathFinderFactory.createFacilityPathFinder("Regular");
		} catch (NoSuchPathFinderException e) { e.printStackTrace(); }
	}
	
	public static FacilityGraph getInstance() {
		if (ourInstance == null) {
			synchronized (FacilityGraph.class) {
				if (ourInstance == null) //Double Check
					ourInstance = new FacilityGraph();
			}
		}
		return ourInstance;
	}
	//------------------
	
	public int getNumVertices() {
		return st.size();
	}
	
	public int getNumEdges() {
		return numEdges;
	}
	
	public boolean hasVertex(String facilityName) {
		return st.containsKey(facilityName);
	}
	
	public boolean hasEdge(String facilityName, String connectionName) throws InvalidParameterException {
		validateVertex(facilityName);
		validateVertex(connectionName);
		return st.get(facilityName).containsKey(connectionName);
	}
	
	public int getEdgeWeight(String facilityName, String connectionName) throws InvalidParameterException {
		if (hasEdge(facilityName, connectionName))
			return st.get(facilityName).get(connectionName);
		else throw new InvalidParameterException(facilityName + " and " + connectionName + "do not have a valid distance");
	}
	
	private void validateVertex(String facilityName) throws InvalidParameterException{
		if (!hasVertex(facilityName)) throw new InvalidParameterException(facilityName + " is not in the FacilityGraph");
	}
	
	public int degree(String facilityName) throws InvalidParameterException {
		validateVertex(facilityName);
		return st.get(facilityName).size();
	}
	
	public void addVertex (String facilityName) {
		if (!hasVertex(facilityName)) st.put(facilityName, new HashMap<String, Integer>());
	}
	
	public void addEdge(String facilityName, String connectionName, int edgeWeight) throws InvalidParameterException {
		if (!hasVertex(facilityName)) addVertex(facilityName);
		if (!hasVertex(connectionName)) addVertex(connectionName);
		if (!hasEdge(facilityName, connectionName)) numEdges++;
		st.get(facilityName).put(connectionName, edgeWeight);
	}
	
	public ArrayList<FacilityGraphHelper> getNeighbors(String facilityName) throws InvalidParameterException {
		validateVertex(facilityName);
		ArrayList<FacilityGraphHelper> neighbors = new ArrayList<>();
		st.get(facilityName).forEach((k,v) -> neighbors.add(new FacilityGraphHelper(k,v))); 
		return neighbors;	
	}

	@Override
	public void printBestPath(String start, String end) {
		
		pathFinder.printBestPath(start, end);
	}

	@Override
	public int getBestPathLength(String start, String end) throws InvalidParameterException {
		
		return pathFinder.getBestPathLength(start, end);
	}

	@Override
	public ArrayList<NodePair> findBestPath(String start, String end) throws InvalidParameterException {
		
		return pathFinder.findBestPath(start, end);
	}

}
