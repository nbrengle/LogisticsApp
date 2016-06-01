package order.observer.impl;

import java.util.Observable;

import facility.DTO.FacilityDTO;
import order.exceptions.NoSuchOrderProcessorException;
import order.observer.interfaces.OrderObserver;
import order.processor.OrderProcessorFactory;
import order.processor.interfaces.OrderProcessor;

public class OrderObserverRegImpl implements OrderObserver {

	private FacilityDTO facility;
	private OrderProcessor processor;
	
	public OrderObserverRegImpl(FacilityDTO facilityIn, String processorType) throws NoSuchOrderProcessorException {
		setFacility(facilityIn);
		setProcessor(processorType);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	private void setProcessor(String processorType) throws NoSuchOrderProcessorException {
		if (processorType.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Processor");}
		this.processor = OrderProcessorFactory.createOrderProcessor("Regular");
	}

	
	public void setFacility(FacilityDTO facility) {
		if (facility.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Facility");}
		this.facility = facility;
	}
}
