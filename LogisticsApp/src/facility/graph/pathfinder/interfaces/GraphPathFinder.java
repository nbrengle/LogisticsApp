package facility.graph.pathfinder.interfaces;

import java.util.List;

import facility.exceptions.InvalidParameterException;

public interface GraphPathFinder<T>{
	
	public void printBestPath(T start, T end) throws InvalidParameterException;
	public int getBestPathLength(T start, T end) throws InvalidParameterException;
	public List<T> findBestPath(T start, T end) throws InvalidParameterException;
}
