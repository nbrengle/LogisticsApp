package order.exceptions;

public class NoSuchOrderProcessorException extends Exception {

	private static final long serialVersionUID = 1777585868554455785L;
	@SuppressWarnings("unused")
	private String message = null;
    
    public NoSuchOrderProcessorException() {
        super();
    }
 
    public NoSuchOrderProcessorException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchOrderProcessorException(Throwable cause) {
        super(cause);
    }
}
