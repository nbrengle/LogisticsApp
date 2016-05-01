package item.exceptions;

public class NoSuchItemLoaderException extends Exception {
    
	private String message = null;
    
    public NoSuchItemLoaderException() {
        super();
    }
 
    public NoSuchItemLoaderException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchItemLoaderException(Throwable cause) {
        super(cause);
    }
}
