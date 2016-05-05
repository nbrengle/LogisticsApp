package facility.graph.interfaces;

import java.util.ArrayList;

import facility.exceptions.InvalidParameterException;
import facility.graph.FacilityGraphHelper;

public interface FacilityGraphPathFinder {
	
	public void printBestPath(String start, String end);
	public int getBestPathLength(String start, String end) throws InvalidParameterException;
	public ArrayList<FacilityGraphHelper> findBestPath(String start, String end) throws InvalidParameterException;
}
