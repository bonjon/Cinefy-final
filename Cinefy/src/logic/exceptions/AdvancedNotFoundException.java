package logic.exceptions;

/*
 * Classe AdvancedNotFoundException che gestisce il
 * caso in cui si cerchi un advanced non presente nel 
 * database.
 */

public class AdvancedNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AdvancedNotFoundException(String message) {
		super(message);
	}
}
