package item.exceptions;

public class NoSuchItemException extends Exception {
	
	private static final long serialVersionUID = -4606301894563792657L;
	private String message = null;
 
    public NoSuchItemException() {
        super();
    }
 
    public NoSuchItemException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchItemException(Throwable cause) {
        super(cause);
    }
 

}
