package item.impl;

import item.exceptions.InvalidPriceException;
import item.interfaces.Item;

public class ItemRegImpl implements Item {
	
	private double price;
	private String name;

	public ItemRegImpl(String name, Double price) {
		try {
			this.name = setName(name);
			this.price = setPrice(price);
		}
		//TODO pass this exception back up to the Factory
		catch (InvalidPriceException e) {
			e.printStackTrace();
		}		
	}
	
	private String setName(String s) {
		//this method exists to allow for validation 
		//it currently does very little
		return s;
	}
	
	private Double setPrice(Double d) throws InvalidPriceException{
		//exists to validate the price values
		//currently validation here is weak as there are no strong business rules yet
		if (d < 0) throw new InvalidPriceException("Tried to set Price less than 0");
		else return d;
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
