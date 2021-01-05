package logic.exceptions;

/*
 * Classe FieldTooLongException che gestisce il caso
 * in cui si inserisca un testo maggiore di un certo
 * numero di caratteri.
 */

public class FieldTooLongException extends Exception {

	public boolean USERTOOLONG = false;
	public boolean PASSTOOLONG = false;
	public boolean BIOTOOLONG = false;
	
	private static final long serialVersionUID = 1L;

	public FieldTooLongException(String message) {
		super(message);
	}
}
