package logic.exceptions;

/*
 * Classe FieldEmptyException che gestirà il
 * caso in cui non si inserisca nulla nei testi.
 */

public class FieldEmptyException extends Exception {

	

	private static final long serialVersionUID = 1L;

	public FieldEmptyException(String message) {
		super(message);
	}
	
}
