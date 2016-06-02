package order.processor.interfaces;

import java.security.InvalidParameterException;

import order.DTO.OrderDTO;
import order.DTO.QuoteDTO;

public interface OrderProcessor {

	QuoteDTO getQuote(String targetItem) throws InvalidParameterException;

	void setOrder(OrderDTO orderIn);

}
