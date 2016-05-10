package item.exceptions;

public class NoSuchItemLoaderException extends Exception {

	private static final long serialVersionUID = 1595784824784417285L;
	@SuppressWarnings("unused")
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
