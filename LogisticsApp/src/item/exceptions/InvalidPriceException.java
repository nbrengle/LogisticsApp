package item.exceptions;

public class InvalidPriceException extends Exception {
	 
	private static final long serialVersionUID = 1382216470993962338L;
	private String message = null;
 
    public InvalidPriceException() {
        super();
    }
 
    public InvalidPriceException(String message) {
        super(message);
        this.message = message;
    }
 
    public InvalidPriceException(Throwable cause) {
        super(cause);
    }
 
}
