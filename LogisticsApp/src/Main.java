import facility.FacilityService;
import facility.exceptions.InvalidParameterException;
import item.ItemService;

public class Main {

	@SuppressWarnings("unused")
	private static void printShortestPathTestsReport() {
		
		System.out.println("Shortest Path Tests:");
		char[] outline = new char[]{'a','b','c','d','e','f','g','h','i','j'};
			System.out.print(outline[0] +") ");
			try {
				FacilityService.getInstance().printBestPath("Santa Fe, NM", "Chicago, IL");
				System.out.print(outline[1] +") ");
				FacilityService.getInstance().printBestPath("Atlanta, GA" , "St. Louis, MO");
				System.out.print(outline[2] +") ");
				FacilityService.getInstance().printBestPath("Seattle, WA" , "Nashville, TN");
				System.out.print(outline[3] +") ");
				FacilityService.getInstance().printBestPath("New York City, NY" , "Phoenix, AZ");
				System.out.print(outline[4] +") ");
				FacilityService.getInstance().printBestPath("Fargo, ND" , "Austin, TX");
				System.out.print(outline[5] +") ");
				FacilityService.getInstance().printBestPath("Denver, CO" , "Miami, FL");
				System.out.print(outline[6] +") ");
				FacilityService.getInstance().printBestPath("Austin, TX" , "Norfolk, VA");
				System.out.print(outline[7] +") ");
				FacilityService.getInstance().printBestPath("Miami, FL" , "Seattle, WA");
				System.out.print(outline[8] +") ");
				FacilityService.getInstance().printBestPath("Los Angeles, CA" , "Chicago, IL");
				System.out.print(outline[9] +") ");
				FacilityService.getInstance().printBestPath("Detroit, MI" , "Nashville, TN");
			} catch (InvalidParameterException e) {
				e.printStackTrace();
			}

	}

	private static void printItemCatalogReport() {
		ItemService.getInstance().printReport();
	}

	private static void printFacilityReport() {
		FacilityService.getInstance().printFacilityReport();
	}
	
	public static void main(String[] args) {
		printFacilityReport();
		printItemCatalogReport();
		System.out.println("");
	}

}
