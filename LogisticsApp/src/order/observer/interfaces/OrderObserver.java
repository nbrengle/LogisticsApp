package order.observer.interfaces;

import java.util.Observable;
import java.util.Observer;

public interface OrderObserver extends Observer {

	void update(Observable o, Object arg);

	//TODO Presumably I'll want to be more than just an Observer some day!
}
