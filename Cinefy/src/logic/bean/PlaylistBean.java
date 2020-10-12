package logic.bean;

import java.io.Serializable;
import java.sql.Date;

/*
 * Classe bean di Playlist che sarà interposta tra view e control
 */

public class PlaylistBean implements Serializable {

	private static final long serialVersionUID = 42L;
	private String id;
	private String advancedName;
	private String voto;
	private String numeroVoti;
	private String date;
	private String name;
	private String playlistPic;

	public String getPlaylistPic() {
		return playlistPic;
	}

	public void setPlaylistPic(String playlistPic) {
		this.playlistPic = playlistPic;
	}

	public String getId() {
		return id;
	}

	public void setId(int id) {
		this.id = "" + id;
	}

	public String getAdvancedName() {
		return advancedName;
	}

	public void setAdvancedName(String advancedName) {
		this.advancedName = advancedName;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date.toString();
	}

}
