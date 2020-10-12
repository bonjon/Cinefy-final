package logic.controllers;

import java.sql.SQLException;
import java.util.List;

import logic.bean.FilmBean;
import logic.dao.FilmDAO;
import logic.entities.Film;
import logic.exceptions.FilmNotFoundException;
import logic.utils.Controller;

/*
 * Classe Controller della HomeBeginneer 
 * che avrà il compito di coordinare il 
 * comportamento del caso d'uso View List of Films. 
 */

public class ViewListOfFilmsController extends Controller {

	public FilmBean getFilm(String title) throws FilmNotFoundException, SQLException {
		FilmDAO fd = new FilmDAO();
		Film fm = fd.selectFilmByName(title);
		return this.convert(fm);
	}

	public List<FilmBean> getFilmByDirector(String director) throws FilmNotFoundException, SQLException {
		FilmDAO fd = new FilmDAO();
		List<Film> fml = fd.selectFilmByDirector(director);
		return this.convertFilmList(fml);
	}

	public List<FilmBean> getFilmByNation(String nation) throws FilmNotFoundException, SQLException {
		FilmDAO fd = new FilmDAO();
		List<Film> fml = fd.selectFilmByNation(nation);
		return this.convertFilmList(fml);
	}

	public List<FilmBean> getFilmByActor(String actor) throws FilmNotFoundException, SQLException {
		FilmDAO fd = new FilmDAO();
		List<Film> fml = fd.selectFilmByActor(actor);
		return this.convertFilmList(fml);
	}

	public List<FilmBean> getFilmByYear(String year) throws FilmNotFoundException, SQLException {
		FilmDAO fd = new FilmDAO();
		List<Film> fml = fd.selectFilmByYear(year);
		return this.convertFilmList(fml);
	}

	public List<FilmBean> getFilmByGenre(String genre) throws FilmNotFoundException, SQLException {
		FilmDAO fd = new FilmDAO();
		List<Film> fml = fd.selectFilmByGenre(genre);
		return this.convertFilmList(fml);
	}
}
