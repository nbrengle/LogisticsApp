package order.helpers;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import order.DTO.QuoteDTO;

public class OrderItemSolution {

	private Map<QuoteDTO, Double> solutions;
	
	public OrderItemSolution() {
		solutions = new HashMap<>();
	}
	
	public void addSolution(QuoteDTO quote, double price) {
		if (quote.equals(null)) throw new NullPointerException("OrderItemSolutions cannot contain null QuoteDTOs");
		if (price <= 0) throw new InvalidParameterException("Prices must be greater than 0");
		solutions.put(quote, price);
	}
}
