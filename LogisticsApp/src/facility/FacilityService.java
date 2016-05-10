package facility;

import facility.interfaces.Facility;
import facility.exceptions.NoSuchFacilityLoaderException;
import facility.graph.FacilityGraph;
import facility.graph.interfaces.EdgeWeightedGraph;
import facility.graph.interfaces.Graph;
import facility.loader.FacilityLoaderFactory;

import java.util.ArrayList;

public class FacilityService {
	
	private ArrayList<Facility> facilities;
	private EdgeWeightedGraph<Facility> graph;
	
	//---Singleton Constructor---
	private volatile static FacilityService ourInstance;
	
	private FacilityService() {
		String filePath = "data/TransportNetwork.xml";
		try {
			facilities = new ArrayList<>();
			facilities.addAll( FacilityLoaderFactory.createFacilityLoader("XML").loadFacilities(filePath) );
			//TODO Build the Graph!
		}
		catch (NoSuchFacilityLoaderException | NullPointerException  e) {
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
		graph.getInstance.
		
	}

}
