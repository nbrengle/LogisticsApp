package facility.loader;

import facility.exceptions.NoSuchFacilityLoaderException;
import facility.loader.impl.FacilityXmlLoader;
import facility.loader.interfaces.FacilityLoader;

public class FacilityLoaderFactory {
	
	private FacilityLoaderFactory() {}; // empty constructor as methods are static
	
	public static FacilityLoader createFacilityLoader(String type) throws NoSuchFacilityLoaderException{
		if (type.equals("XML")) 
			return new FacilityXmlLoader(); 
		else throw new NoSuchFacilityLoaderException("FacilityLoader type :" + type + " Does Not Exist");
	}

}
