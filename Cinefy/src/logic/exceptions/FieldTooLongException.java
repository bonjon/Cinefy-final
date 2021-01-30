package logic.exceptions;

/*
 * Classe FieldTooLongException che gestisce il caso
 * in cui si inserisca un testo maggiore di un certo
 * numero di caratteri. Jacopo Onorati
 */

public class FieldTooLongException extends Exception {

	
	
	private static final long serialVersionUID = 1L;

	public FieldTooLongException(String message) {
		super(message);
	}
}
