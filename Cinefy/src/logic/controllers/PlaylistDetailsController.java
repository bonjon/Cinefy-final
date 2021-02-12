package logic.controllers;

import java.sql.SQLException;
import java.util.List;

import logic.bean.FilmBean;
import logic.dao.BeginnerUserDAO;
import logic.dao.FilmDAO;
import logic.dao.PlaylistDAO;
import logic.entities.Film;
import logic.exceptions.FilmNotFoundException;
import logic.exceptions.PlaylistNotFoundException;
import logic.utils.Controller;

public class PlaylistDetailsController extends Controller {

	public List<FilmBean> getFilmPlaylist(String id)
			throws NumberFormatException, SQLException, FilmNotFoundException, ClassNotFoundException {
		FilmDAO fd = new FilmDAO();
		List<Film> lf = fd.selectFilmPlaylist(Integer.parseInt(id));
		return this.convertFilmList(lf);
	}
 
	public void votePlaylist(int voto, String id, String beginner)
			throws NumberFormatException, SQLException, ClassNotFoundException, PlaylistNotFoundException {
		BeginnerUserDAO bud = new BeginnerUserDAO();
		PlaylistDAO pd = new PlaylistDAO();
		boolean voteResult;
		voteResult = bud.votePlaylist(voto, Integer.parseInt(id), beginner);
		if(voteResult) {
			pd.assignToken(Integer.parseInt(id),voto);
		}
	}
}
