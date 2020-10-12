package logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logic.connection.ConnectionDB;
import logic.entities.AdvancedUser;
import logic.entities.BeginnerUser;
import logic.entities.GeneralUser;

/*
 * Classe dao di GeneralUser che si occuperà di 
 * trovare un determinato utente.
 */

public class GeneralUserDAO {

	public GeneralUser findUser(String username, String password) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionDB.getInstance();
		GeneralUser u = null;
		String sql = "call CinefyDB.login(?,?);\r\n";
		try (PreparedStatement stm = conn.prepareStatement(sql)) {
			stm.setString(1, username);
			stm.setString(2, password);
			try (ResultSet rs = stm.executeQuery()) {
				if (!rs.first()) {
					return null;
				}
				String role = rs.getString("role");
				u = new GeneralUser(username, "", role);
			}
		}
		return u;
	}
	
	public BeginnerUser findByName(String username, String role) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		BeginnerUser bu = null;
		String sql = "call CinefyDB.get_by_username(?,?);\r\n";
		try (PreparedStatement stm = conn.prepareStatement(sql)){
			stm.setString(1, role);
			stm.setString(2, username);
			try (ResultSet rs = stm.executeQuery()){
				if (!rs.first())
					return null;
				String bio = rs.getString("bio");
				String profilePic = rs.getString("immagine");
				bu = new BeginnerUser(username, profilePic, bio);
			}
		}
		return bu;
	}
	
	public AdvancedUser findByName2(String username, String role) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		AdvancedUser bu = null;
		String sql = "call CinefyDB.get_by_username(?,?);\r\n";
		try (PreparedStatement stm = conn.prepareStatement(sql)){
			stm.setString(1, role);
			stm.setString(2, username);
			try (ResultSet rs = stm.executeQuery()){
				if (!rs.first())
					return null;
				String bio = rs.getString("bio");
				String profession = rs.getString("ruolo");
				double voto = rs.getDouble("voto");
				int numero = rs.getInt("numerodivoti");
				int tokens = rs.getInt("tokens");
				String profilePic = rs.getString("immagine");
				bu = new AdvancedUser(username, profilePic, profession, bio, voto, numero, tokens);
			}
		}
		return bu;
	}
}
