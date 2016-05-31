package order.loader.interfaces;

import java.util.ArrayList;

import order.execeptions.NoSuchOrderException;
import order.interfaces.Order;

public interface OrderLoader {

	public ArrayList<Order> loadOrders(String inputFile) throws NoSuchOrderException;
}
