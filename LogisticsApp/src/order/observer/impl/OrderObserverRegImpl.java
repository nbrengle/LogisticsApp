package order.observer.impl;

import java.util.ArrayList;
import java.util.Observable;

import facility.FacilityService;
import facility.DTO.FacilityDTO;
import order.OrderProcessorService;
import order.DTO.OrderDTO;
import order.DTO.QuoteDTO;
import order.exceptions.NoSuchOrderProcessorException;
import order.helpers.ObserverHelper;
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
		ObserverHelper helper = (ObserverHelper) arg; //make sure this case works, it scares me
		setOrder(helper.getOrder());
		String target = helper.getTarget();
		if (facility.getActiveItems().containsKey(target)) {
			updateFacilityData();
			processor.setOrder(order);
			OrderProcessorService.getInstance().addQuote(getQuote(target));
		}
	}
	
	private void updateFacilityData() {
		ArrayList<FacilityDTO> facilities = FacilityService.getInstance().getFacilities();
		for (FacilityDTO f : facilities) {
			if (f.getUniqueIdentifier().equals(facility.getUniqueIdentifier())) {
				setFacility(f);
			}
		}
	}
	
	public QuoteDTO getQuote(String targetItem) {
		return processor.getQuote(targetItem);
	}
	
	private void setSubject(Observable subjectIn) {
		if (subjectIn.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Subject");}
		this.subject = subjectIn;
	}
	
	private void setFacility(FacilityDTO facility) {
		if (facility.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Facility");}
		this.facility = facility;
	}
	
	private void setOrder(OrderDTO order) {
		if (order.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Order");}
		this.order = order;
	}
	
	private void setProcessor(String processorType) throws NoSuchOrderProcessorException {
		if (processorType.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Processor");}
		this.processor = OrderProcessorFactory.createOrderProcessor("Regular",
																	//TODO is this an information hiding issue? 
																	facility);
	}

	

}
