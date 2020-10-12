package logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.connection.ConnectionDB;
import logic.entities.Film;
import logic.exceptions.FilmNotFoundException;

/*
 * Classe dao  di Film che avrà il compito di mediare tra la rappresentazione in 
 * memoria delle istanze e la rappresentazione su layer di persistenza
 */

public class FilmDAO {

	public Film selectFilmByName(String filmName) throws SQLException, FilmNotFoundException {
		Connection conn = null;
		Film fm = null;
		String sql = null;
		conn = ConnectionDB.getInstance();
		sql = "call CinefyDB.stampa_film_nome(?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, filmName);
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first()) {
					throw new FilmNotFoundException("No film found with this name");
				}
				int filmId = rs.getInt("FilmId");
				String url = rs.getString("Url");
				String regista = rs.getString("Regista");
				String attore = rs.getString("AttorePrincipale");
				int anno = rs.getInt("Anno");
				String nazione = rs.getString("Nazione");
				String genere = rs.getString("Genere");
				fm = new Film(filmId, filmName, url, regista, attore, anno, nazione, genere);
			}
		}
		return fm;
	}

	// Query che ritorna un film se si è scelto come filtro Director
	public List<Film> selectFilmByDirector(String director) throws SQLException, FilmNotFoundException {
		return this.queryDatabase(director, "Director");
	}

	@SuppressWarnings("resource")
	private List<Film> queryDatabase(String string, String type) throws SQLException, FilmNotFoundException {
		Connection conn = null;
		List<Film> fml = new ArrayList<>();
		PreparedStatement s = null;
		try {
			if (type.equals("Director")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.stampa_film_regista(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, string);
			}
			if (type.equals("Actor")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.stampa_film_attore(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, string);
			}
			if (type.equals("Nation")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.stampa_film_nazione(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, string);
			}
			if (type.equals("Year")) {
				conn = ConnectionDB.getInstance();
				int a = Integer.parseInt(string);
				String sql = "call CinefyDB.stampa_film_anno(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setInt(1, a);
			}
			if (type.equals("Genre")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.stampa_film_genere(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, string);
			}
			if (type.equals("Playlist")) {
				conn = ConnectionDB.getInstance();
				int a = Integer.parseInt(string);
				String sql = "call CinefyDB.stampa_film_playlist(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setInt(1, a);
			}
			if (s != null) {
				try (ResultSet rs = s.executeQuery()) {
					fml = unpackResultSet(rs, type);
				}
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return fml;
	}

	private List<Film> unpackResultSet(ResultSet rs, String type) throws SQLException, FilmNotFoundException {
		List<Film> fml = new ArrayList<>();
		if (!rs.first()) {
			if (type.equals("Playlist"))
				throw new FilmNotFoundException("No film found in this playlist");
			else
				throw new FilmNotFoundException("No film found with this " + type);
		}
		do {
			int id = rs.getInt("FilmId");
			String titolo = rs.getString("Titolo");
			String url = rs.getString("Url");
			String regista = rs.getString("Regista");
			String attore = rs.getString("AttorePrincipale");
			int anno = rs.getInt("Anno");
			String nazione = rs.getString("Nazione");
			String genere = rs.getString("Genere");
			Film temp = new Film(id, titolo, url, regista, attore, anno, nazione, genere);
			fml.add(temp);
		} while (rs.next());
		return fml;
	}

	// Query che ritorna un film se si è scelto come filtro Actor
	public List<Film> selectFilmByActor(String actor) throws SQLException, FilmNotFoundException {
		return this.queryDatabase(actor, "Actor");
	}

	// Query che ritorna un film se si è scelto come filtro Nation
	public List<Film> selectFilmByNation(String nation) throws SQLException, FilmNotFoundException {
		return this.queryDatabase(nation, "Nation");
	}

	// Query che ritorna un film se si è scelto come filtro Director
	public List<Film> selectFilmByYear(String year) throws SQLException, FilmNotFoundException {
		return this.queryDatabase(year, "Year");
	}

	// Query che ritorna un film se si è scelto come filtro Director
	public List<Film> selectFilmByGenre(String genre) throws SQLException, FilmNotFoundException {
		return this.queryDatabase(genre, "Genre");
	}

	public List<Film> selectFilmPlaylist(int id) throws SQLException, FilmNotFoundException {
		return this.queryDatabase(Integer.toString(id), "Playlist");
	}
}
