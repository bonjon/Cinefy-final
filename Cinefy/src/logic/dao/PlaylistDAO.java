package logic.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.connection.ConnectionDB;
import logic.entities.Film;
import logic.entities.Playlist;
import logic.exceptions.PlaylistNotFoundException;

/*
 * Classe dao di Playlist che avrà il compito di mediare tra la rappresentazione in 
 * memoria delle istanze e la rappresentazione su layer di persistenza.
 */

public class PlaylistDAO {

	public Playlist addPlaylist(String name, String username, String playlistPic) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		Playlist p = null;
		String sql = "call CinefyDB.aggiungi_playlist(?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, name);
			s.setString(2, username);
			s.setString(3, playlistPic);
			try (ResultSet rs = s.executeQuery()) {
				if(!rs.first())
					System.out.println("Error");
				int id = rs.getInt("idPlaylist");
				String advanced = rs.getString("AdvancedName");
				double voto = rs.getDouble("Voto");
				Date data = rs.getDate("DataPubblicazione");
				int numerodivoti = rs.getInt("numerodivoti");
				String playlistPath = rs.getString("playlistPic");
				p = new Playlist(id, advanced, voto, numerodivoti, data, name, playlistPath);
			}
		}
		return p;
	}

	public Playlist selectPlaylistByName(String name) throws SQLException, PlaylistNotFoundException {
		Connection conn = null;
		Playlist p = null;
		String sql = null;
		conn = ConnectionDB.getInstance();
		sql = "call CinefyDB.stampa_playlist_nome(?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, name);
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					throw new PlaylistNotFoundException("No playlist found with this name");
				int id = rs.getInt("idPlaylist");
				String advanced = rs.getString("AdvancedName");
				double voto = rs.getDouble("Voto");
				Date data = rs.getDate("DataPubblicazione");
				int numerodivoti = rs.getInt("numerodivoti");
				String playlistPic = rs.getString("playlistPic");
				p = new Playlist(id, advanced, voto, numerodivoti, data, name, playlistPic);
			}
		}
		return p;
	}

	public List<Playlist> selectPlaylistByUsername(String username) throws SQLException, PlaylistNotFoundException {
		return this.queryDatabase(username, "Advanced");
	}

	@SuppressWarnings("resource")
	private List<Playlist> queryDatabase(String string, String type) throws SQLException, PlaylistNotFoundException {
		Connection conn = null;
		List<Playlist> pl = new ArrayList<>();
		PreparedStatement s = null;
		try {
			if (type.equals("Advanced")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.stampa_playlist_username(?)\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, string);
			}
			if (type.equals("Rewards")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.classifica_playlist();\r\n";
				s = conn.prepareStatement(sql);
			}
			if (s != null) {
				try (ResultSet rs = s.executeQuery()) {
					pl = unpackResultSet(rs, type);
				}
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return pl;
	}

	private List<Playlist> unpackResultSet(ResultSet rs, String type) throws SQLException, PlaylistNotFoundException {
		List<Playlist> pl = new ArrayList<>();
		if (!rs.first())
			if (type.equals("Advanced"))
				throw new PlaylistNotFoundException("No playlist found with this advanced name");
			else
				throw new PlaylistNotFoundException("No playlists in leaderboard");
		do {
			int id = rs.getInt("idPlaylist");
			String advanced = rs.getString("AdvancedName");
			double voto = rs.getDouble("Voto");
			Date data = rs.getDate("DataPubblicazione");
			int numerodivoti = rs.getInt("numerodivoti");
			String name = rs.getString("Nome");
			String playlistPic = rs.getString("playlistPic");
			Playlist temp = new Playlist(id, advanced, voto, numerodivoti, data, name, playlistPic);
			pl.add(temp);
		} while (rs.next());
		return pl;
	}

	public List<Playlist> leaderBoardPl() throws SQLException, PlaylistNotFoundException {
		return this.queryDatabase(null, "Rewards");
	}

	public void assignToken(int id) throws SQLException, PlaylistNotFoundException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.assegna_token_playlist(?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setInt(1, id);
			s.executeUpdate();
		}
	}

	public void addFilms(List<Film> lf) {

	}
}
