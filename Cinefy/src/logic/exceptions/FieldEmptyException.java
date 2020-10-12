package logic.exceptions;

/*
 * Classe FieldEmptyException che gestirà il
 * caso in cui non si inserisca nulla nei testi.
 */

public class FieldEmptyException extends Exception {

	public static boolean EMPTYPASSWORD=false;
	public static boolean EMPTYUSERNAME=false;
	public static boolean EMPTYROLE=false;
	public static boolean EMPTYPROFESSION=false;

	private static final long serialVersionUID = 1L;

	public FieldEmptyException(String message) {
		super(message);
	}
	
}
