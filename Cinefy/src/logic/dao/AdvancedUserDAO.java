package logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.connection.ConnectionDB;
import logic.entities.AdvancedUser;
import logic.exceptions.AdvancedNotFoundException;

/*
 * Classe dao  di Advanced User che avrà il compito di mediare tra la rappresentazione in 
 * memoria delle istanze e la rappresentazione su layer di persistenza.
 */

public class AdvancedUserDAO {

	public AdvancedUser selectAdvancedByUsername(String name) throws SQLException, AdvancedNotFoundException, ClassNotFoundException {
		Connection conn = null;
		AdvancedUser au = null;
		String sql = null;
		conn = ConnectionDB.getInstance();
		sql = "call CinefyDB.stampa_advanced_nome(?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, name);
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					throw new AdvancedNotFoundException("No advanced found with this name");
				String ruolo = rs.getString("ruolo");
				String bio = rs.getString("bio");
				String immagine = rs.getString("immagine");
				double voto = rs.getDouble("voto");
				int numerodivoti = rs.getInt("numerodivoti");
				int tokens = rs.getInt("tokens");
				au = new AdvancedUser(name, immagine, ruolo, bio, voto, numerodivoti, tokens);
			}
		}
		return au;
	}

	public List<AdvancedUser> selectAdvancedByRole(String role) throws SQLException, AdvancedNotFoundException, ClassNotFoundException {
		return this.queryDatabase(role, "Role");
	}

	@SuppressWarnings("resource")
	private List<AdvancedUser> queryDatabase(String string, String type)
			throws SQLException, AdvancedNotFoundException, ClassNotFoundException {
		Connection conn = null;
		List<AdvancedUser> aul = new ArrayList<>();
		PreparedStatement s = null;
		try {
			if (type.equals("Role")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.stampa_advanced_ruolo(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, string);
			}
			if (type.equals("Rewards")) {
				conn = ConnectionDB.getInstance();
				String sql = "call CinefyDB.classifica_advanced();\r\n";
				s = conn.prepareStatement(sql);
			}
			if (s != null) {
				try (ResultSet rs = s.executeQuery()) {
					aul = unpackResultSet(rs, type);
				}
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return aul;
	}

	private List<AdvancedUser> unpackResultSet(ResultSet rs, String type)
			throws SQLException, AdvancedNotFoundException {
		List<AdvancedUser> aul = new ArrayList<>();
		if (!rs.first()) {
			if (type.equals("Role"))
				throw new AdvancedNotFoundException("No advanced with this role");
			else
				return null;
		}
		do {
			String name = rs.getString("username");
			String ruolo = rs.getString("ruolo");
			String bio = rs.getString("bio");
			String immagine = rs.getString("immagine");
			double voto = rs.getDouble("voto");
			int numerodivoti = rs.getInt("numerodivoti");
			int tokens = rs.getInt("tokens");
			AdvancedUser temp = new AdvancedUser(name, immagine, ruolo, bio, voto, numerodivoti, tokens);
			aul.add(temp);
		} while (rs.next());
		return aul;
	}

	public boolean createAdvancedUser(String username, String password, String role, String bio, String profilePic)
			throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.aggiungi_advanced(?,?,?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, username);
			s.setString(2, password);
			s.setString(3, role);
			s.setString(4, bio);
			s.setString(5, profilePic);
			s.executeUpdate();
			return true;
		}
	}

	public List<AdvancedUser> leaderBoardAd() throws SQLException, AdvancedNotFoundException, ClassNotFoundException {
		return this.queryDatabase(null, "Rewards");
	}
	
	public boolean addFilmPlaylist(int id, int film) throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.aggiungi_film_playlist(?,?);\r\n";
		try (PreparedStatement stm = conn.prepareStatement(sql)){
			stm.setInt(1, id);
			stm.setInt(2, film);
			stm.executeUpdate();
			return true;
		}
	}
}
