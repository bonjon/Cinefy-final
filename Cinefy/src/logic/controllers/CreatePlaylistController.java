package logic.controllers;

import java.sql.SQLException;

import logic.bean.FilmBean;
import logic.bean.PlaylistBean;
import logic.dao.AdvancedUserDAO;
import logic.dao.PlaylistDAO;
import logic.entities.Playlist;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.Controller;

public class CreatePlaylistController extends Controller {

	public PlaylistBean createPlaylist(String text, String username, String playlistPic)
			throws FieldEmptyException, FieldTooLongException, SQLException, ClassNotFoundException {
		PlaylistDAO pd = new PlaylistDAO();
		if (text.isEmpty()) {
			throw new FieldEmptyException("Enter a name, please");
		}
		if (text.length() >= 45) {
			throw new FieldTooLongException("Name is too long");
		}
		Playlist p = pd.addPlaylist(text, username, playlistPic);
		return this.convert(p);
	}

	public void addFilm(int id, FilmBean selected)
			throws NumberFormatException, SQLException, FieldEmptyException, ClassNotFoundException {
		AdvancedUserDAO aud = new AdvancedUserDAO();
		if (selected == null)
			throw new FieldEmptyException("You press add without select any film");
		aud.addFilmPlaylist(id, Integer.parseInt(selected.getFilmId()));
	}
}
