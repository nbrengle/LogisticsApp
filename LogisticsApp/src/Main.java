import facility.FacilityService;

public class Main {
	
	final static int DRIVING_HOURS_PER_DAY = 8;
	final static int AVERAGE_MILES_PER_HOUR = 50;

	private static void printFacilityReport() {
		FacilityService.getInstance().printFacilityReport();
	}
	
	public static void main(String[] args) {

		printFacilityReport();
		System.out.println("");
		
	}

}
