package facility;

import facility.exceptions.NoSuchFacilityLoaderException;
import facility.exceptions.NoSuchGraphException;
import facility.exceptions.NoSuchPathFinderException;
import facility.graph.GraphFactory;
import facility.graph.interfaces.EdgeWeightedGraph;
import facility.graph.pathfinder.GraphPathFinderFactory;
import facility.graph.pathfinder.interfaces.GraphPathFinder;
import facility.helpers.FacilityNeighborHelper;
import facility.interfaces.Facility;
import facility.loader.FacilityLoaderFactory;
import item.exceptions.NoSuchItemException;

import java.util.ArrayList;
import java.util.List;

public class FacilityService {
	
	private List<Facility> facilities;
	private EdgeWeightedGraph<String> facilityGraph;
	private GraphPathFinder<String> facilityPathFinder;
	
	//---Singleton Constructor---
	private volatile static FacilityService ourInstance;
	
	private FacilityService() {
		GraphFactory<String> graphBuilder = new GraphFactory<>();
		GraphPathFinderFactory<String> pathFinderBuilder = new GraphPathFinderFactory<>();
		
		String filePath = "data/TransportNetwork.xml";
		
		try {
			facilities = new ArrayList<>();
			facilities.addAll( FacilityLoaderFactory.createFacilityLoader("XML").loadFacilities(filePath) );
			facilityGraph = graphBuilder.createGraph("Dijkstra");
			//Pass one to add all the nodes
			for (Facility fac : facilities) {
				String facName = fac.getUniqueIdentifier();
				facilityGraph.addNode(facName);
			}
			//Pass two to add the edges
			for (Facility fac: facilities) {
				for (FacilityNeighborHelper neighbor : fac.getConnectingFacilities()) {
					facilityGraph.addEdge(fac.getUniqueIdentifier(), neighbor.getUniqueIdentifier(), neighbor.getDistance());
				}
			}
			
			facilityPathFinder = pathFinderBuilder.createPathFinder("Dijkstra", facilityGraph);
			
		}
		catch (NoSuchFacilityLoaderException | 
				NullPointerException | 
				NoSuchGraphException | 
				NoSuchPathFinderException | 
				NoSuchItemException
				e ) {
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
		
		facilityPathFinder.printBestPath(start, end);
	}

}
