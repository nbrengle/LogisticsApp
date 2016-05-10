package facility;

import facility.exceptions.NoSuchFacilityLoaderException;
import facility.exceptions.NoSuchGraphException;
import facility.exceptions.NoSuchPathFinderException;
import facility.graph.GraphFactory;
import facility.graph.interfaces.EdgeWeightedGraph;
import facility.graph.pathfinder.GraphPathFinderFactory;
import facility.graph.pathfinder.interfaces.GraphPathFinder;
import facility.interfaces.Facility;
import facility.loader.FacilityLoaderFactory;

import java.util.ArrayList;
import java.util.List;

public class FacilityService {
	
	private List<Facility> facilities;
	private EdgeWeightedGraph<Facility> facilityGraph;
	private GraphPathFinder<Facility> facilityPathFinder;
	
	//---Singleton Constructor---
	private volatile static FacilityService ourInstance;
	
	private FacilityService() {
		GraphFactory<Facility> graphBuilder = new GraphFactory<>();
		GraphPathFinderFactory<Facility> pathFinderBuilder = new GraphPathFinderFactory<>();
		
		String filePath = "data/TransportNetwork.xml";
		
		try {
			facilities = new ArrayList<>();
			facilities.addAll( FacilityLoaderFactory.createFacilityLoader("XML").loadFacilities(filePath) );
			facilityGraph = graphBuilder.createGraph("Dijkstra");
			
			
			facilityPathFinder = pathFinderBuilder.createPathFinder("Dijkstra", facilityGraph);
			
		}
		catch (NoSuchFacilityLoaderException | NullPointerException | NoSuchGraphException | NoSuchPathFinderException e) {
			e.printStackTrace();
			}

	}
	
	public static FacilityService getInstance() {
		if (ourInstance == null) {
			synchronized (FacilityService.class) {
				if (ourInstance == null) //Double Check
					ourInstance = new FacilityService();
			}
		}
		return ourInstance;
	}
	//------------------
	
	
	public void printFacilityReport() {
		for (Facility f : facilities) f.printReport();
	}

	public void printBestPath(String start, String end) {
		
		
	}

}
