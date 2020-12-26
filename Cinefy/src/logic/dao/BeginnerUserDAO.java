package logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import logic.connection.ConnectionDB;

/*
 * Classe dao di BeginnerUser che avrà il compito di mediare tra la rappresentazione in 
 * memoria delle istanze e la rappresentazione su layer di persistenza.
 */

public class BeginnerUserDAO {

	public boolean createBeginnerUser(String username, String password, String bio, String profilePic)
			throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		
		String sql = "call CinefyDB.aggiungi_beginner(?,?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, username);
			s.setString(2, password);
			s.setString(3, bio);
			s.setString(4, profilePic);
			
			s.executeUpdate();
			return true;
		}
	}

	public void voteAdvanced(String username, String beginnerName, int voto, String idRisposta) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.vota_advanced(?,?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, username);
			s.setString(2, beginnerName);
			s.setInt(3, voto);
			s.setInt(4, Integer.parseInt(idRisposta));
			s.executeUpdate();
		}
	}

	public void votePlaylist(int voto, int idPlaylist, String beginner) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.vota_playlist(?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setInt(1, voto);
			s.setInt(2, idPlaylist);
			s.setString(3, beginner);
			s.executeUpdate();
		}
	}
}
