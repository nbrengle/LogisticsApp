package facility.graph;

import facility.exceptions.InvalidParameterException;
import facility.graph.interfaces.FacilityGraph;
import facility.interfaces.Facility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * adapted from: http://introcs.cs.princeton.edu/java/45graph/Graph.java.html
 */

public class FacilityGraphImpl implements FacilityGraph {
	
	//---Data Members---
	//hashmap: key = vertex, value = hashmap of neighboring vertices and their integer edgeweight
	private HashMap<String, HashMap<String, Integer>> st;
	
	//number of edges
	private int numEdges;
	//------------------
	
	
	//---Singleton Constructor---
	private volatile static FacilityGraphImpl ourInstance;
	
	private FacilityGraphImpl() {
		//TODO empty constructor is a terrible plan, make this better
		st = new HashMap<String, HashMap<String, Integer>>();
	}
	
	public static FacilityGraphImpl getInstance() {
		if (ourInstance == null) {
			synchronized (FacilityGraphImpl.class) {
				if (ourInstance == null) //Double Check
					ourInstance = new FacilityGraphImpl();
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
	
	public Map<String, Integer> getNeigbors(String facilityName) throws InvalidParameterException {
		validateVertex(facilityName);
		return  Collections.unmodifiableMap(st.get(facilityName));
	}

}
