package facility.exceptions;

public class NoSuchInventoryException extends Exception{

	private static final long serialVersionUID = -5250128990830215400L;
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
