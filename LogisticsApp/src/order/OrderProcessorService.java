package order;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import static java.util.Comparator.comparingInt;

import java.security.InvalidParameterException;

import facility.FacilityService;
import facility.DTO.FacilityDTO;
import item.ItemService;
import item.exceptions.InvalidPriceException;
import item.exceptions.NoSuchItemException;
import order.DTO.OrderDTO;
import order.DTO.QuoteDTO;
import order.exceptions.NoSuchOrderObserverException;
import order.exceptions.NoSuchOrderProcessorException;
import order.helpers.ObserverHelper;
import order.helpers.OrderItemSolution;
import order.observer.OrderObserverFactory;


public class OrderProcessorService extends Observable {

	//Singleton Facade for Order Processing
	private volatile static OrderProcessorService ourInstance;
	private List<OrderDTO> orders = new ArrayList<>();
	private List<QuoteDTO> quotes = new ArrayList<>();


	private OrderProcessorService() {	
		ArrayList<FacilityDTO> facilities = FacilityService.getInstance().getFacilities();
		for (FacilityDTO f : facilities) {
			try {
				OrderObserverFactory.createOrderObserver("Regular", this, f);
			} catch (NoSuchOrderObserverException | NoSuchOrderProcessorException e) {
				e.printStackTrace();
			} 
		}
		orders.addAll(OrderService.getInstance().getOrders());
	}

	public static OrderProcessorService getInstance() {
		if (ourInstance == null) {
			synchronized (OrderService.class) {
				if (ourInstance == null) //Double Check
					ourInstance = new OrderProcessorService();
			}
		}
		return ourInstance;
	}
	
	//Step 1: Identify all facilities with the desired item 
	//NOTE: Destinations cannot be their own source.
	private void notifyOrderObservers(OrderDTO order, String itemName) {
		notifyObservers(new ObserverHelper(order, itemName));
		if (quotes.isEmpty()) 
			throw new InvalidParameterException();
	}
	//Step 2: For each Facility Identified, get a Quote
	public void addQuote (QuoteDTO quote) {
		quotes.add(quote);
	}
	
	//Step 4: Select the facility with the earliest (lowest) Arrival Day and do the following:
	//Reduce the inventory of the item at that facility by the number of items taken
	//Update the schedule of the selected site (book the days needed to process the items)
	
	private void commitQuote (QuoteDTO quote) {
		try {
			FacilityService.getInstance().commitQuote(quote);
		} catch (InvalidParameterException | NoSuchItemException e) {
			e.printStackTrace();
		}
	}
	
	//Save this as part of your solution


	//Step 5: If there is still more quantity of the item needed, go back to step 4 and continue the process. 
	//Step 6: Compute the total cost of this item.
	//The logistics costs of an Order Item consist of: (Total Item Cost + Total Facility Processing Cost + Total Transport Cost)
	private double calcQuotePrice (QuoteDTO quote) throws NoSuchItemException, InvalidPriceException {
		double returnPrice = 0;
		double unitPrice = ItemService.getInstance().getPrice(quote.getItemName());
		double toalItemPrice = unitPrice * quote.getNumItems();
		double facCostPerDay = FacilityService.getInstance().getFacilityCostPerDay(quote.getSource());
		int daysNecessary = quote.getEndDay() - quote.getStartDay();
		double totalProcessingPrice = facCostPerDay * daysNecessary; //TODO this might need to become partial days 
		double totalTravelPrice = quote.getTravelTime() * 500; //TODO that 500 should be a CONST! No magic numbers. 
		returnPrice = toalItemPrice + totalProcessingPrice + totalTravelPrice;
		return returnPrice;
	}
	//Step 7: Generate the complete logistics record for this order-item (your set of solutions from step 4).
	//Step 8: If there are more items to process in this order, go back and repeat this process from step 1 with the next item

	//TODO this is a nightmare function, it ought to be broken into much smaller pieces!
	public void printOrderReport() {
		//Step1
		for (OrderDTO order : orders) {
			int index = 1;
			List<OrderItemSolution> OrderSolution = new ArrayList<>();
			order.getItems().forEach((item,quantity) -> {
				quotes.clear(); //Make sure quotes are empty each pass through the loop
				clearChanged();
				OrderItemSolution itemSolutions = new OrderItemSolution();
				int tempItemsNeeded = quantity;	
				while (tempItemsNeeded > 0) {				
					setChanged();
					notifyOrderObservers(order,item);
					//Step 3: Sort the (4) records developed in step “2d” above by earliest (lowest) Arrival Day
					quotes.sort(comparingInt(quote -> quote.getArrivalDay()));
					//Step 4
					QuoteDTO bestQuote = quotes.get(0);
					commitQuote(bestQuote);
					try {
						itemSolutions.addSolution(bestQuote, calcQuotePrice(bestQuote));
					} catch (Exception e) {
						e.printStackTrace();
					}
					//Reduce the quantity of the item that is needed for the order by the amount taken from the selected facility
					tempItemsNeeded -= bestQuote.getNumItems();
				}
				OrderSolution.add(itemSolutions);
			});
			OrderService.getInstance().printOrderReport(order.getId(),index);
			index ++;
			double totalCost = 0;
			for (OrderItemSolution sol : OrderSolution){
				totalCost += sol.getTotalPrice();
			}
			int firstDelivery = 0;
			//TODO Get the Earliest Delivery
			
			int lastDelivery = 0;
			//TODO Get the Latest Delivery
			
			System.out.println("Processing Solution:");
			System.out.printf("\tTotal Cost:\t\t$%,.0f%n", totalCost);
			System.out.printf("\t1st Delivery Day: %d%n", firstDelivery);
			System.out.printf("\tLast Delivery Day:\t%d%n", lastDelivery);
			System.out.printf("\tOrder Items:%n");
			System.out.printf("\t\tItem ID\tQuantity\t\tCost\t\t# Sources Used\tFirst Day\tLastDay%n");
		}
	}
	
	//Step 9: Generate output. 
	// Order Report Info + this info:
	/*
	 * 	Processing Solution:
			Total Cost: 	  $94,355
			1st Delivery Day: 3
			Last Delivery Day:	16
			Order Items:
				   Item ID 	Quantity	Cost 		# Sources Used 	First Day	Last Day
				1) ABC123	180			$67,705		4				4			16
				2) CR2032	320			$26,650		1				3			6
		----------------------------------------------------------------------------------
	 */

}
