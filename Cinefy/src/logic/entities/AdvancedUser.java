package logic.entities;

/*
 * Classe Entity di AdvancedUser che rappresenta la tabella Advanced
 */

public class AdvancedUser extends GeneralUser {

	private String profilePic;
	private String profession;
	private String bio;
	private double voto;
	private int numeroVoti;
	private int tokens;

	public AdvancedUser(String username, String profilePic, String profession, String bio, double voto, int numeroVoti,
			int tokens) {
		this.profilePic = profilePic;
		this.profession = profession;
		this.bio = bio;
		this.username = username;
		this.voto = voto;
		this.numeroVoti = numeroVoti;
		this.tokens = tokens;
	}

	public int getTokens() {
		return tokens;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public String getProfession() {
		return profession;
	}

	public String getBio() {
		return bio;
	}

	public double getVoto() {
		return voto;
	}

	public int getNumeroVoti() {
		return numeroVoti;
	}

}
