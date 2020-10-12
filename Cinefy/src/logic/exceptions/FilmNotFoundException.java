package logic.exceptions;

/*
 * Classe FilmNotFoundException che gestisce il caso in
 * cui si stia cercando un film non presente nel database.
 */

public class FilmNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public FilmNotFoundException (String message) {
		super(message);
	}
}
