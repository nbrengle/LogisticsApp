package facility.exceptions;

public class NoSuchGraphException extends Exception {

	private static final long serialVersionUID = -8148250279699564984L;
	private String message = null;
	
	public NoSuchGraphException() {
        super();
    }
 
    public NoSuchGraphException(String message) {
        super(message);
        this.message = message;
    }
 
    public NoSuchGraphException(Throwable cause) {
        super(cause);
    }
}
