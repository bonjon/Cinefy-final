package logic.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.connection.ConnectionDB;
import logic.entities.Playlist;
import logic.exceptions.PlaylistNotFoundException;

/*
 * Classe dao di Playlist che avrà il compito di mediare tra la rappresentazione in 
 * memoria delle istanze e la rappresentazione su layer di persistenza.
 */

public class PlaylistDAO {

	public static final String ADVANCED = "AdvancedName";
	public static final String ID = "idPlaylist";
	public static final String DATA = "DataPubblicazione";
	public static final String NUMERO = "numerodivoti";
	public static final String PIC = "playlistPic";
	public static final String AD = "Advanced";

	public Playlist addPlaylist(String name, String username, String playlistPic)
			throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionDB.getInstance();
		Playlist p = null;
		String sql = "call CinefyDB.aggiungi_playlist(?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, name);
			s.setString(2, username);
			s.setString(3, playlistPic);
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					return null;
				int id = rs.getInt(ID);
				String advanced = rs.getString(ADVANCED);
				double voto = rs.getDouble("Voto");
				Date data = rs.getDate(DATA);
				int numerodivoti = rs.getInt(NUMERO);
				String playlistPath = rs.getString(PIC);
				p = new Playlist(id, advanced, voto, numerodivoti, data, name, playlistPath);
			}
		}
		return p;
	}

	public Playlist selectPlaylistByName(String name)
			throws SQLException, PlaylistNotFoundException, ClassNotFoundException {
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
				int id = rs.getInt(ID);
				String advanced = rs.getString(ADVANCED);
				double voto = rs.getDouble("Voto");
				Date data = rs.getDate(DATA);
				int numerodivoti = rs.getInt(NUMERO);
				String playlistPic = rs.getString(PIC);
				p = new Playlist(id, advanced, voto, numerodivoti, data, name, playlistPic);
			}
		}
		return p;
	}

	public List<Playlist> selectPlaylistByUsername(String username)
			throws SQLException, PlaylistNotFoundException, ClassNotFoundException {
		return this.queryDatabase(username, AD);
	}

	@SuppressWarnings("resource")
	private List<Playlist> queryDatabase(String string, String type)
			throws SQLException, PlaylistNotFoundException, ClassNotFoundException {
		Connection conn = null;
		List<Playlist> pl = new ArrayList<>();
		PreparedStatement s = null;
		try {
			if (type.equals(AD)) {
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
		if (!rs.first()) {
			if (type.equals(AD)) {
				throw new PlaylistNotFoundException("No playlist found with this advanced name");
			} else {
				throw new PlaylistNotFoundException("No playlists in leaderboard");
			}
		}
		do {
			int id = rs.getInt(ID);
			String advanced = rs.getString(ADVANCED);
			double voto = rs.getDouble("Voto");
			Date data = rs.getDate(DATA);
			int numerodivoti = rs.getInt(NUMERO);
			String name = rs.getString("Nome");
			String playlistPic = rs.getString(PIC);
			Playlist temp = new Playlist(id, advanced, voto, numerodivoti, data, name, playlistPic);
			pl.add(temp);
		} while (rs.next());
		return pl;
	}

	public List<Playlist> leaderBoardPl() throws SQLException, PlaylistNotFoundException, ClassNotFoundException {
		return this.queryDatabase(null, "Rewards");
	}

	public void assignToken(int id) throws SQLException, PlaylistNotFoundException, ClassNotFoundException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.assegna_token_playlist(?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setInt(1, id);
			s.executeUpdate();
		}
	}
}
