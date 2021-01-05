package logic.utils;

public class ExceptionInfo {

	//classe che serve a far apparire contemporaneamente più messaggi d' errore che hanno causato eccezioni
	
	private  boolean emptyPassword;
	private  boolean emptyUsername;
	private  boolean emptyRole;
	private  boolean emptyProfession;
	private  boolean userTooLong ;
	private  boolean passTooLong ;
	private  boolean bioTooLong ;
	
	public ExceptionInfo() {
		emptyPassword=false;
		emptyUsername=false;
		emptyRole=false;
		emptyProfession=false;
		userTooLong=false;
		passTooLong=false;
		bioTooLong=false;
		
	}
	
	public void setEmptyUsername(boolean emptyUsername) {
		this.emptyUsername=emptyUsername;
	}
	

	public boolean getEmptyUsername() {
		return emptyUsername;
	}
	
	public void setEmptyPassword(boolean emptyPassword) {
		this.emptyPassword=emptyPassword;
	}
	

	public boolean getEmptyPassword() {
		return emptyPassword;
	}
	
	public void setEmptyRole(boolean emptyRole) {
		this.emptyRole=emptyRole;
	}
	

	public boolean getEmptyRole() {
		return emptyRole;
	}
	
	public void setEmptyProfession(boolean emptyProfession) {
		this.emptyProfession=emptyProfession;
	}
	

	public boolean getEmptyProfession() {
		return emptyProfession;
	}
	
	public void setTooLongUser(boolean userTooLong) {
		this.userTooLong=userTooLong;
	}
	

	public boolean getTooLongUser() {
		return userTooLong;
	}
	
	public void setTooLongPass(boolean passTooLong) {
		this.passTooLong=passTooLong;
	}
	

	public boolean getTooLongPassword() {
		return passTooLong;
	}
	
	public void setTooLongBio(boolean bioTooLong) {
		this.bioTooLong=bioTooLong;
	}
	

	public boolean getTooLongBio() {
		return bioTooLong;
	}
}
