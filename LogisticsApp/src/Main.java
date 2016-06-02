import facility.FacilityService;
import order.OrderProcessorService;

public class Main {
	
	final static int DRIVING_HOURS_PER_DAY = 8;
	final static int AVERAGE_MILES_PER_HOUR = 50;

	private static void printFacilityReport() {
		FacilityService.getInstance().printFacilityReport();
	}
	
	private static void printOrderReport() {
		OrderProcessorService.getInstance().printOrderReport();
	}
	
	public static void main(String[] args) {

		printFacilityReport();
		System.out.println("");
		printOrderReport();
		System.out.println("");
		printFacilityReport();
	}

}
