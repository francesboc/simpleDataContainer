
public class UserAlreadyExistException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException() {
		super();
	}
	
	public UserAlreadyExistException(String s) {
		super(s);
	}
}
