package logic.utils;

public class ExceptionInfo {

	//classe che serve a far apparire contemporaneamente più messaggi d' errore che hanno causato eccezioni
	
	public  boolean EMPTYPASSWORD;
	public  boolean EMPTYUSERNAME;
	public  boolean EMPTYROLE;
	public  boolean EMPTYPROFESSION;
	public  boolean USERTOOLONG ;
	public  boolean PASSTOOLONG ;
	public  boolean BIOTOOLONG ;
	
	public ExceptionInfo() {
		EMPTYPASSWORD=false;
		EMPTYUSERNAME=false;
		EMPTYROLE=false;
		EMPTYPROFESSION=false;
		USERTOOLONG=false;
		PASSTOOLONG=false;
		BIOTOOLONG=false;
		
	}

}
