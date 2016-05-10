package facility.exceptions;

public class InvalidParameterException extends Exception {
    
	private static final long serialVersionUID = -5266756951661865050L;
	@SuppressWarnings("unused")
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
