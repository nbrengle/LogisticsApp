package item.exceptions;

public class InvalidPriceException extends Exception {
	 
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
