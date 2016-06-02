package facility;

import facility.DTO.FacilityDTO;
import facility.exceptions.NoSuchFacilityException;
import facility.exceptions.NoSuchFacilityLoaderException;
import facility.exceptions.NoSuchGraphException;
import facility.exceptions.NoSuchInventoryException;
import facility.exceptions.NoSuchPathFinderException;
import facility.exceptions.NoSuchScheduleException;
import facility.graph.GraphFactory;
import facility.graph.interfaces.EdgeWeightedGraph;
import facility.graph.pathfinder.GraphPathFinderFactory;
import facility.graph.pathfinder.interfaces.GraphPathFinder;
import facility.helpers.FacilityNeighborHelper;
import facility.interfaces.Facility;
import facility.loader.FacilityLoaderFactory;
import item.exceptions.NoSuchItemException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
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
		
			facilities = new ArrayList<>();
			try {
				facilities.addAll( FacilityLoaderFactory.createFacilityLoader("XML").loadFacilities(filePath) );
		
			facilityGraph = graphBuilder.createGraph("Dijkstra");
			//TODO move this logic into the factory! Or some alternate interface, I shouldn't own this logic
			for (Facility fac : facilities) {
				facilityGraph.addNode(fac);
				for (FacilityNeighborHelper neighbor : fac.getConnectingFacilities()) {
					Facility neighborFac = null;
					//TODO do me up pretty in lambdas
					for (Facility facPluck : facilities) {
						if (facPluck.getUniqueIdentifier().equals(neighbor.getUniqueIdentifier()))
							neighborFac = facPluck;
					}
					facilityGraph.addEdge(fac, neighborFac, neighbor.getDistance());
				}
			}
			
			facilityPathFinder = pathFinderBuilder.createPathFinder("Dijkstra", facilityGraph);
			} catch (InvalidParameterException | 
					 NoSuchFacilityException | 
					 NoSuchInventoryException | 
					 NoSuchScheduleException | 
					 NoSuchItemException | 
					 NoSuchFacilityLoaderException | 
					 NoSuchGraphException | 
					 NoSuchPathFinderException e) {
				// TODO Auto-generated catch block
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

	public void printBestPath(String start, String end) throws InvalidParameterException {
		Facility startFac = null, endFac = null;
		for (Facility facPluck : facilities) {
			if (facPluck.getUniqueIdentifier().equals(start)) startFac = facPluck;
			if (facPluck.getUniqueIdentifier().equals(end)) endFac = facPluck;
		}
		
		facilityPathFinder.printBestPath(startFac, endFac);
	}
	
	public boolean isFacility(String fac) {
		boolean tester = false;
		for (Facility facCheck : facilities) {
			if (facCheck.getUniqueIdentifier().equals(fac)) tester = true;
		}
		return tester;
	}
	
	public int getBestPathLength(String start, String end) throws InvalidParameterException {
		Facility startFac = null, endFac = null;
		for (Facility facPluck : facilities) {
			if (facPluck.getUniqueIdentifier().equals(start)) startFac = facPluck;
			if (facPluck.getUniqueIdentifier().equals(end)) endFac = facPluck;
		}
		
		return facilityPathFinder.getBestPathLength(startFac, endFac);
	}
	
	public int getDayOrderWillComplete(String facName, int startDay, int itemsInBatch) {
		Facility fac = null;
		for (Facility facPluck : facilities) {
			if (facPluck.getUniqueIdentifier().equals(facName)) fac = facPluck;
		}
		return fac.getDayOrderWillComplete(startDay, itemsInBatch);
	}
	
	public void commitOrder(String facName, int startDay, int itemsInBatch) {
		scheduleItems(String facName, int startDay, int itemsInBatch);

	}
	
	public ArrayList<FacilityDTO> getFacilities() {
		ArrayList<FacilityDTO> returnList = new ArrayList<>();
		for (Facility f : facilities) {
			returnList.add(new FacilityDTO(f.getCity(),
										   f.getState(),
										   f.getItemsPerDay(),
										   f.getCostPerDay(),
										   f.getConnectingFacilities(),
										   f.getActiveItems(),
										   f.getDepletedItems(),
										   f.getSchedule()
										   ));
		}
		return returnList;
		
	}

}
