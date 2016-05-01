package facility.exceptions;

public class NoSuchFacilityLoaderException extends Exception{
    
	private String message = null;
    
    public NoSuchFacilityLoaderException() {
        super();
    }
 
    public NoSuchFacilityLoaderException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchFacilityLoaderException(Throwable cause) {
        super(cause);
    }
}
