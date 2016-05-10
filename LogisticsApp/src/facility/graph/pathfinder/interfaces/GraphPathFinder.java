package facility.graph.interfaces;

import java.util.ArrayList;

import facility.exceptions.InvalidParameterException;
import facility.graph.NodePair;

public interface GraphPathFinder<T>{
	
	public void printBestPath(T start, T end);
	public int getBestPathLength(T start, T end) throws InvalidParameterException;
	public ArrayList<T> findBestPath(T start, T end) throws InvalidParameterException;
}
