package facility.exceptions;

public class NoSuchFacilityException extends Exception{
    
	private String message = null;
    
    public NoSuchFacilityException() {
        super();
    }
 
    public NoSuchFacilityException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchFacilityException(Throwable cause) {
        super(cause);
    }
}
