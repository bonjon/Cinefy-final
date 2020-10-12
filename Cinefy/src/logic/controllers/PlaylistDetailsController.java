package logic.controllers;

import java.sql.SQLException;
import java.util.List;

import logic.bean.FilmBean;
import logic.dao.BeginnerUserDAO;
import logic.dao.FilmDAO;
import logic.entities.Film;
import logic.exceptions.FilmNotFoundException;
import logic.utils.Controller;

public class PlaylistDetailsController extends Controller {

	public List<FilmBean> getFilmPlaylist(String id) throws NumberFormatException, SQLException, FilmNotFoundException {
		FilmDAO fd = new FilmDAO();
		List<Film> lf = fd.selectFilmPlaylist(Integer.parseInt(id));
		return this.convertFilmList(lf);
	}

	public void votePlaylist(int voto, String id, String beginner) throws NumberFormatException, SQLException {
		BeginnerUserDAO bud = new BeginnerUserDAO();
		bud.votePlaylist(voto, Integer.parseInt(id), beginner);
	}
}
