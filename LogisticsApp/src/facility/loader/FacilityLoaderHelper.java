package facility.loader;

/**
 * The purpose of this class is to act as a pseudo-tuple to transfer
 * ConnectingFacility information from the Raw data to the FacilityFactory
 * @author nathaniel
 *
 */

public class FacilityLoaderHelper {
	
	private String city;
	private String state;
	private int distance;
	
	public FacilityLoaderHelper(String cityIn, String stateIn, int distanceIn) {
		//currently relies on validation on either side
		//TODO consider validation inside this class as well
		this.city = cityIn;
		this.state = stateIn;
		this.distance = distanceIn;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public int getDistance() {
		return distance;
	}
}
