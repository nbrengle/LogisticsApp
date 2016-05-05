package facility.exceptions;

public class NoSuchScheduleException extends Exception {
	private String message = null;
    
    public NoSuchScheduleException() {
        super();
    }
 
    public NoSuchScheduleException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchScheduleException(Throwable cause) {
        super(cause);
    }
}
