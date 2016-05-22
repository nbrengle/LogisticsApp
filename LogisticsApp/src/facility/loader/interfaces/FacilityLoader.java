package facility.loader.interfaces;

import facility.exceptions.InvalidParameterException;
import facility.exceptions.NoSuchFacilityException;
import facility.exceptions.NoSuchInventoryException;
import facility.exceptions.NoSuchScheduleException;
import facility.interfaces.Facility;
import item.exceptions.NoSuchItemException;

import java.util.ArrayList;

public interface FacilityLoader {
	
	public ArrayList<Facility> loadFacilities(String inputFile) 
			throws NoSuchFacilityException, 
				   InvalidParameterException, 
				   NoSuchInventoryException, 
				   NoSuchScheduleException, 
				   NoSuchItemException;

}
