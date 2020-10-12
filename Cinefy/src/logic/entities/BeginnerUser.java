package logic.entities;

/*
 * Classe entity di BeginnerUser che rappresenta la tabella Beginner 
 */

public class BeginnerUser extends GeneralUser {
	
	private String profilePic;
	private String bio;

	public BeginnerUser(String username, String profilePic, String bio) {
		this.profilePic = profilePic;
		this.bio = bio;
		this.username = username;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public String getBio() {
		return bio;
	}

}
