package order.helpers;

import java.security.InvalidParameterException;

import order.DTO.OrderDTO;

public class ObserverHelper {

	private OrderDTO order;
	private String target;
	
	public ObserverHelper(OrderDTO orderIn, String targetIn) {
		setOrder(orderIn);
		setTarget(targetIn);
	}

	public OrderDTO getOrder() {
		return order;
	}

	private void setOrder(OrderDTO order) {
		if (order.equals(null)) throw new NullPointerException("order cannot be null");
		this.order = order;
	}

	public String getTarget() {
		return target;
	}

	private void setTarget(String target) {
		if (target.equals(null)) throw new NullPointerException("target cannot be null");
		if (target.equals("")) throw new InvalidParameterException("target cannot be empty");
		this.target = target;
	}
}
