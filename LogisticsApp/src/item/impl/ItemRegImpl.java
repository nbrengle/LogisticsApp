package item.impl;

import java.security.InvalidParameterException;
import item.interfaces.Item;

public class ItemRegImpl implements Item {
	
	private double price;
	private String name;

	public ItemRegImpl(String name, Double price) throws InvalidParameterException {
			setName(name);
			setPrice(price);	
	}
	
	private void setName(String s) {
		//this method exists to allow for validation 
		//it currently does very little
		this.name = s;
	}
	
	private void setPrice(Double priceIn) throws InvalidParameterException{
		//exists to validate the price values
		//currently validation here is weak as there are no strong business rules yet
		if (priceIn < 0) 
			throw new InvalidParameterException("Tried to set Price less than 0");
		else 
			this.price = priceIn;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

}
