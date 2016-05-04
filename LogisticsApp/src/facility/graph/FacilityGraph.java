package facility.graph;

import facility.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * adapted from: http://introcs.cs.princeton.edu/java/45graph/Graph.java.html
 */

public class FacilityGraph {
	
	//---Data Members---
	//hashmap: key = vertex, value = hashmap of neighboring vertices and their integer edgeweight
	private HashMap<String, HashMap<String, Integer>> st;
	
	//number of edges
	private int numEdges;
	//------------------
	
	
	//---Singleton Constructor---
	private volatile static FacilityGraph ourInstance;
	
	private FacilityGraph() {
		//TODO empty constructor is a terrible plan, make this better
		st = new HashMap<String, HashMap<String, Integer>>();
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
	
	private void validateVertex(String facilityName) throws InvalidParameterException{
		if (!hasVertex(facilityName)) throw new InvalidParameterException(facilityName + " is not in the FacilityGraph");
	}
	
	public int degree(String facilityName) throws InvalidParameterException {
		validateVertex(facilityName);
		return st.get(facilityName).size();
	}
	
	//TODO should I be public? Probably not
	public void addVertex (String facilityName) {
		if (!hasVertex(facilityName)) st.put(facilityName, new HashMap<String, Integer>());
	}
	
	//TODO should I be public? Probably not
	public void addEdge(String facilityName, String connectionName, int edgeWeight) throws InvalidParameterException {
		if (!hasVertex(facilityName)) addVertex(facilityName);
		if (!hasVertex(connectionName)) addVertex(connectionName);
		if (!hasEdge(facilityName, connectionName)) numEdges++;
		st.get(facilityName).put(connectionName, edgeWeight);
	}
	
	public ArrayList<FacilityNeighborHelper> getNeighbors(String facilityName) throws InvalidParameterException {
		validateVertex(facilityName);
		ArrayList<FacilityNeighborHelper> neighbors = new ArrayList<>();
		st.get(facilityName).forEach((k,v) -> neighbors.add(new FacilityNeighborHelper(k,v))); 
		return neighbors;	
	}

}
