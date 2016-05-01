package facility.graph;

import facility.exceptions.InvalidParameterException;

/**
 * adapted from: http://introcs.cs.princeton.edu/java/45graph/Graph.java.html
 */

import facility.interfaces.Facility;

import java.util.HashMap;
import java.util.HashSet;

public class FacilityGraph {
	
	//hashmap: key = vertex, value = set of neighboring vertices
	private HashMap<Facility, HashSet<Facility>> st;
	
	//number of edges
	private int numEdges;
	
	public FacilityGraph() {
		//TODO empty constructor is a terrible plan, make this better
		st = new HashMap<Facility, HashSet<Facility>>();
	}
	
	public int getNumVertices() {
		return st.size();
	}
	
	public int getNumEdges() {
		return numEdges;
	}
	
	public boolean hasVertex(Facility f) {
		return st.containsKey(f);
	}
	
	public boolean hasEdge(Facility f, Facility c) throws InvalidParameterException {
		validateVertex(f);
		validateVertex(c);
		return st.get(f).contains(c);
	}
	
	private void validateVertex(Facility f) throws InvalidParameterException{
		if (!hasVertex(f)) throw new InvalidParameterException(f + " is not in the FacilityGraph");
	}
	
	public int degree(Facility f) throws InvalidParameterException {
		validateVertex(f);
		return st.get(f).size();
	}
	
	//TODO should I be public? Probably not
	public void addVertex (Facility f) {
		if (!hasVertex(f)) st.put(f, new HashSet<Facility>());
	}
	
	//TODO should I be public? Probably not
	public void addEdge(Facility f, Facility c) {
		try {
			if (!hasVertex(f)) addVertex(f);
			if (!hasVertex(c)) addVertex(c);
			if (!hasEdge(f, c)) numEdges++;
			st.get(f).add(c);
			st.get(c).add(f);
		}
		catch {
			//TODO fulfill me!
		}
	}
	
	public Iterable<Facility> adjacentTo(Facility f) throws InvalidParameterException {
		validateVertex(f);
		return st.get(f);
	}

}
