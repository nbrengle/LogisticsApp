package facility.exceptions;

public class NoSuchFacilityException extends Exception{
    
	private static final long serialVersionUID = -8221029829950786928L;
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
