package facility;

import facility.interfaces.Facility;
import facility.exceptions.NoSuchFacilityLoaderException;
import facility.loader.FacilityLoaderFactory;

import java.util.ArrayList;

public class FacilityService {
	
	private ArrayList<Facility> facilities;
	
	//---Singleton Constructor---
	private volatile static FacilityService ourInstance;
	
	private FacilityService() {
		try {
			facilities.addAll( FacilityLoaderFactory.createFacilityLoader("XML").loadFacilities("TransportNetwork.xml") );
		}
		catch (NoSuchFacilityLoaderException e) {
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
	
	
	public void printReport() {
		for (Facility f : facilities) f.printReport();
	}
	

}
