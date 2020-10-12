package logic.entities;

/*
 * Classe entity GeneralUser che rappresenta la tabella GeneralUser
 * cioè quell'utente che si è loggato che sia un admin, advanced o 
 * beginner. 
 */

public class GeneralUser {
	
	protected String username;
	protected String password;
	private String role;

	public GeneralUser() {
		this.username = "";
		this.password = "";
		this.role = "";
	}

	public GeneralUser(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
}
