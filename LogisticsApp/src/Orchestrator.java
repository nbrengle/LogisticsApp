import facility.FacilityService;
import facility.graph.FacilityGraph;
import item.ItemService;

public class Orchestrator {

	private static void printShortestPathTestsReport() {
		
		System.out.println("Shortest Path Tests:");
		char[] outline = new char[]{'a','b','c','d','e','f','g','h','i','j'};
			System.out.print(outline[0] +") ");
			FacilityGraph.getInstance().printBestPath("Santa Fe, NM", "Chicago, IL");
			System.out.print(outline[1] +") ");
			FacilityGraph.getInstance().printBestPath("Atlanta, GA" , "St. Louis, MO");
			System.out.print(outline[2] +") ");
			FacilityGraph.getInstance().printBestPath("Seattle, WA" , "Nashville, TN");
			System.out.print(outline[3] +") ");
			FacilityGraph.getInstance().printBestPath("New York City, NY" , "Phoenix, AZ");
			System.out.print(outline[4] +") ");
			FacilityGraph.getInstance().printBestPath("Fargo, ND" , "Austin, TX");
			System.out.print(outline[5] +") ");
			FacilityGraph.getInstance().printBestPath("Denver, CO" , "Miami, FL");
			System.out.print(outline[6] +") ");
			FacilityGraph.getInstance().printBestPath("Austin, TX" , "Norfolk, VA");
			System.out.print(outline[7] +") ");
			FacilityGraph.getInstance().printBestPath("Miami, FL" , "Seattle, WA");
			System.out.print(outline[8] +") ");
			FacilityGraph.getInstance().printBestPath("Los Angeles, CA" , "Chicago, IL");
			System.out.print(outline[9] +") ");
			FacilityGraph.getInstance().printBestPath("Detroit, MI" , "Nashville, TN");
	}

	private static void printItemCatalogReport() {
		ItemService.getInstance().printReport();
	}

	private static void printFacilityReport() {
		FacilityService.getInstance().printReport();
	}
	
	public static void main(String[] args) {
		printFacilityReport();
		printItemCatalogReport();
		System.out.println("");
		printShortestPathTestsReport();
	}

}
