package facility.exceptions;

public class NoSuchPathFinderException extends Exception{
	private String message = null;
    
    public NoSuchPathFinderException() {
        super();
    }
 
    public NoSuchPathFinderException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchPathFinderException(Throwable cause) {
        super(cause);
    }
}
