package logic.exceptions;

/*
 * Classe FieldEmptyException che gestirà il
 * caso in cui non si inserisca nulla nei testi.
 */

public class FieldEmptyException extends Exception {

	public static final boolean EMPTYPASSWORD=false;
	public static final boolean EMPTYUSERNAME=false;
	public static final boolean EMPTYROLE=false;
	public static final boolean EMPTYPROFESSION=false;

	private static final long serialVersionUID = 1L;

	public FieldEmptyException(String message) {
		super(message);
	}
	
}
