package order.execeptions;

public class NoSuchOrderLoaderException extends Exception {
	
	private static final long serialVersionUID = -4325020475736545576L;
	@SuppressWarnings("unused")
	private String message = null;
    
    public NoSuchOrderLoaderException() {
        super();
    }
 
    public NoSuchOrderLoaderException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchOrderLoaderException(Throwable cause) {
        super(cause);
    }
}
