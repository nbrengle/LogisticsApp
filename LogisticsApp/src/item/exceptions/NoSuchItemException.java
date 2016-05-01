package item.exceptions;

public class NoSuchItemException extends Exception {
	
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
