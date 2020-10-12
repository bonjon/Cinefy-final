package logic.exceptions;

public class AlreadyUsedUsernameException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public AlreadyUsedUsernameException(String message) {
		super(message);
	}
}
