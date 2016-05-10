package facility.exceptions;

public class NoSuchScheduleException extends Exception {

	private static final long serialVersionUID = 1994182397400808501L;
	@SuppressWarnings("unused")
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
