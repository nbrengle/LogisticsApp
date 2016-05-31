package order.exceptions;

public class NoSuchOrderObserverException extends Exception {

	private static final long serialVersionUID = 6149123913972733197L;

	@SuppressWarnings("unused")
	private String message = null;
    
    public NoSuchOrderObserverException() {
        super();
    }
 
    public NoSuchOrderObserverException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchOrderObserverException(Throwable cause) {
        super(cause);
    }
}
