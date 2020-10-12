package logic.bean;

import java.io.Serializable;

/*
 * Classe bean di BeginnerUser che sarà interposta tra view e control
 */

public class BeginnerUserBean extends GeneralUserBean implements Serializable {
	
	static final long serialVersionUID = 42L;
	private String profilePic;
	private String bio;

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

}
