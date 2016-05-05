package facility.exceptions;

public class NoSuchInventoryException extends Exception{
	private String message = null;
    
    public NoSuchInventoryException() {
        super();
    }
 
    public NoSuchInventoryException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchInventoryException(Throwable cause) {
        super(cause);
    }
}
