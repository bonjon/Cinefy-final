package logic.exceptions;

public class PlaylistNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlaylistNotFoundException(String message) {
		super(message);
	}
}
