package order.processor.interfaces;

import java.security.InvalidParameterException;

import order.DTO.QuoteDTO;

public interface OrderProcessor {

	QuoteDTO getQuote(String targetItem) throws InvalidParameterException;

}
