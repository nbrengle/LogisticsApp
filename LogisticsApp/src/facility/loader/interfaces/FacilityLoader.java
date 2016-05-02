package facility.loader.interfaces;

import facility.interfaces.Facility;
import java.util.ArrayList;

public interface FacilityLoader {
	
	public ArrayList<Facility> loadFacilities(String inputFile);

}
