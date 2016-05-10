package facility.graph;

import java.util.ArrayList;
import java.util.HashMap;

import facility.FacilityNeighborHelper;
import facility.exceptions.InvalidParameterException;
import facility.exceptions.NoSuchPathFinderException;
import facility.graph.interfaces.FacilityGraphPathFinder;
import facility.graph.interfaces.GraphPathFinder;

/**
 * adapted from: http://introcs.cs.princeton.edu/java/45graph/Graph.java.html
 */

public class FacilityGraph implements GraphPathFinder{
	
	//---Data Members---
	
	//FacilityGraphPathFinder implementation
	private GraphPathFinder pathFinder;
	
	
	//---Singleton Constructor---
	private volatile static FacilityGraph ourInstance;
	
	private FacilityGraph() {

		try {
			this.pathFinder = GraphPathFinderFactory.createPathFinder("Dijkstra");
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
	
	

}
