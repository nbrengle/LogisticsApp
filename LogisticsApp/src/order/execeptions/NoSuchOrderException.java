package order.execeptions;

public class NoSuchOrderException extends Exception {

	private static final long serialVersionUID = 4304939856861095722L;
	@SuppressWarnings("unused")
	private String message = null;
    
    public NoSuchOrderException() {
        super();
    }
 
    public NoSuchOrderException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchOrderException(Throwable cause) {
        super(cause);
    }
}
