package order;

public class OrderProcessor {
	
		
	//Singleton Facade for a Catalog of Orders
		private volatile static OrderProcessor ourInstance;
		
		private OrderProcessor() {	

		}
		
		public static OrderProcessor getInstance() {
			if (ourInstance == null) {
				synchronized (OrderService.class) {
					if (ourInstance == null) //Double Check
						ourInstance = new OrderProcessor();
				}
			}
			return ourInstance;
		}
}
