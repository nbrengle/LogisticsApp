package order;

import java.util.ArrayList;
import java.util.List;
import order.exceptions.NoSuchOrderException;
import order.exceptions.NoSuchOrderLoaderException;
import order.interfaces.Order;
import order.loader.OrderLoaderFactory;
import order.DTO.OrderDTO;

public class OrderService {
	
	private ArrayList<Order> orders = new ArrayList<>();
	
	//Singleton Facade for a Catalog of Orders
	private volatile static OrderService ourInstance;
	
	private OrderService() {	
		String filePath = "data/Orders.xml";
		try {
			orders.addAll( OrderLoaderFactory.createOrderLoader("XML").loadOrders(filePath) );
		}
		catch (NoSuchOrderLoaderException | NoSuchOrderException e) {
			e.printStackTrace();
		}
	}
	
	public static OrderService getInstance() {
		if (ourInstance == null) {
			synchronized (OrderService.class) {
				if (ourInstance == null) //Double Check
					ourInstance = new OrderService();
			}
		}
		return ourInstance;
	}

	public List<OrderDTO> getOrders() {
		List<OrderDTO> returnList = new ArrayList<>();
		for (Order o : orders) {
			returnList.add(new OrderDTO(o.getId(),
										o.getStartDay(),
										o.getDestinationCity(),
										o.getDestinationState(),
										o.getItems()
										));
		}
		return returnList;
	}
	
}
