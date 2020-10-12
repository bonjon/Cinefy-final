package logic.entities;

import java.sql.Date;

/*
 * Classe Entity Playlist che rappresenta la tabella Playlist
 */

public class Playlist {

	private int id;
	private String advancedName;
	private double voto;
	private int numeroVoti;
	private Date date;
	private String name;
	private String playlistPic;

	public Playlist(int id, String advancedName, double voto, int numeroVoti, Date date, String name,
			String playlistPic) {
		this.id = id;
		this.advancedName = advancedName;
		this.voto = voto;
		this.numeroVoti = numeroVoti;
		this.date = date;
		this.name = name;
		this.playlistPic = playlistPic;
	}

	public String getPlaylistPic() {
		return playlistPic;
	}

	public int getId() {
		return id;
	}

	public String getAdvancedName() {
		return advancedName;
	}

	public double getVoto() {
		return voto;
	}

	public int getNumeroVoti() {
		return numeroVoti;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

}
