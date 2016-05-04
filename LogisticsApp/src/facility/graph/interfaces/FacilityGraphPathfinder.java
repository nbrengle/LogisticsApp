package facility.graph.interfaces;

import java.util.ArrayList;

import facility.graph.FacilityNeighborHelper;

public interface FacilityGraphPathfinder {
	
	public void printBestPath(String start, String end);
	public int getBestPathLength(String start, String end);
	public ArrayList<FacilityNeighborHelper> findBestPath(String start, String end);
}
