package order.helpers;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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

	public double getTotalPrice() {
		double returnPrice = 0;
		ArrayList<Double> accum = new ArrayList<>();
		solutions.forEach((k,v) -> accum.add(v));
		for (Double ac : accum) returnPrice += ac;
		return returnPrice;
	}
}
