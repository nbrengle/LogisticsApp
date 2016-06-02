package order.observer.impl;

import java.util.Observable;

import facility.DTO.FacilityDTO;
import order.DTO.OrderDTO;
import order.DTO.QuoteDTO;
import order.exceptions.NoSuchOrderProcessorException;
import order.observer.interfaces.OrderObserver;
import order.processor.OrderProcessorFactory;
import order.processor.interfaces.OrderProcessor;

public class OrderObserverRegImpl implements OrderObserver {

	private Observable subject;
	private OrderProcessor processor;
	private FacilityDTO facility;
	private OrderDTO order = null;
	
	public OrderObserverRegImpl(Observable subjectIn, FacilityDTO facilityIn, String processorType) throws NoSuchOrderProcessorException {
		setSubject(subjectIn);
		setFacility(facilityIn);
		setProcessor(processorType);
		subject.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		OrderDTO orderIn = (OrderDTO) arg; //make sure this cast works!
		this.order = orderIn;
	}
	
	public QuoteDTO getQuote() {
		return processor.getQuote();
	}
	
	private void setSubject(Observable subjectIn) {
		if (subjectIn.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Subject");}
		this.subject = subjectIn;
	}
	
	public void setFacility(FacilityDTO facility) {
		if (facility.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Facility");}
		this.facility = facility;
	}
	
	private void setProcessor(String processorType) throws NoSuchOrderProcessorException {
		if (processorType.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Processor");}
		this.processor = OrderProcessorFactory.createOrderProcessor("Regular",
																	//TODO is this an information hiding issue? 
																	facility,
																	order);
	}

	

}
