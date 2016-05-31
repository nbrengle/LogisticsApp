package order.observer.impl;

import java.util.Observable;

import facility.DTO.FacilityDTO;
import order.observer.interfaces.OrderObserver;

public class OrderObserverRegImpl implements OrderObserver {

	private FacilityDTO facility;
	
	public OrderObserverRegImpl(FacilityDTO facilityIn) {
		setFacility(facilityIn);
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	public void setFacility(FacilityDTO facility) {
		if (facility.equals(null)) {throw new NullPointerException("OrderObservers Cannot Have a Null Facility");}
		this.facility = facility;
	}
}
