package logic.bean;

import java.io.Serializable;

/*
 * Classe bean di AdvancedUser che verrà interposta tra view e control
 */

public class AdvancedUserBean extends GeneralUserBean implements Serializable {

	static final long serialVersionUID = 42L;
	private String profilePic;
	private String profession;
	private String bio;
	private String voto;
	private String numeroVoti;
	private String tokens;
	private Integer queueCount;

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(double voto) {
		this.voto = "" + voto;
	}

	public String getNumeroVoti() {
		return numeroVoti;
	}

	public void setNumeroVoti(int numeroVoti) {
		this.numeroVoti = "" + numeroVoti;
	}

	public String getTokens() {
		return tokens;
	}

	public void setTokens(int tokens) {
		this.tokens = "" + tokens;
	}
	public Integer getQueueCount() {
		return queueCount;
	}
	
	public void setQueueCount(Integer queueCount) {
		 this.queueCount = queueCount;
	}

}
