package facility.graph;

import facility.exceptions.InvalidParameterException;

//this is a helper to map a neighbor to its distance in a unmodifiable way

public class FacilityNeighborHelper {
	
	private String uniqueIdentifier;
	private int distance;
	
	public FacilityNeighborHelper(String idIn, int distIn) {
		try{
			this.uniqueIdentifier = setUniqueIdentifier(idIn);
			this.distance = setDistance(distIn);
		}
		catch (InvalidParameterException e) {e.printStackTrace();}
	}
	
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	
	private String setUniqueIdentifier(String idIn) {
		//mostly relying on external validation, TODO may be ways to improve this
		return idIn;
	}
	
	public int getDistance() {
		return distance;
	}
	
	private int setDistance(int distIn) throws InvalidParameterException {
		//mostly relying on external validation
		//TODO consider other invalid state for distance
		//TODO consider alternate exceptions
		if (distIn < 0) throw new InvalidParameterException(distIn + "is not a valid distance, must be > 0");
		//else
		return distIn;
	}
	
	
}
