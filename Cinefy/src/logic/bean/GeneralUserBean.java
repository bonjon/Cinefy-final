package logic.bean;

import java.io.Serializable;

/*
 * Classe bean di GeneralUser che sarà interposta tra view e control
 */

public class GeneralUserBean implements Serializable {
	
	static final long serialVersionUID = 42L;
	protected String username;
	protected String password;
	protected String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
