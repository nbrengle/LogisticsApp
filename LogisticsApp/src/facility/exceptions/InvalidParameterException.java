package facility.exceptions;

public class InvalidParameterException extends Exception {
    
	private String message = null;
    
    public InvalidParameterException() {
        super();
    }
 
    public InvalidParameterException(String message) {
        super(message);
        this.message = message;
    }
 
    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
}
