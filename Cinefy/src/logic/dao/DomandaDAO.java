package logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.connection.ConnectionDB;
import logic.entities.Domanda;

/*
 * Classe dao di Domanda che avrà il compito di mediare tra la rappresentazione in 
 * memoria delle istanze e la rappresentazione su layer di persistenza.
 */

public class DomandaDAO {

	public static final String ACCEPT = "accept";
	public static final String REJECT = "reject";

	@SuppressWarnings("resource")
	public List<Domanda> getQuestions(String username, String role) throws SQLException {
		Connection conn = null;
		String sql = null;
		PreparedStatement s = null;
		List<Domanda> ld = new ArrayList<>();
		try {
			if (role.equals("admin")) {
				conn = ConnectionDB.getInstance();
				sql = "call CinefyDB.stampa_domande_in_coda();\r\n";
				s = conn.prepareStatement(sql);
			}
			if (role.equals("beginner")) {
				conn = ConnectionDB.getInstance();
				sql = "call CinefyDB.stampa_domande(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, username);
			}
			if (role.equals("beginner2")) {
				conn = ConnectionDB.getInstance();
				sql = "call CinefyDB.get_pending(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, username);
			}
			if (role.equals("advanced")) {
				conn = ConnectionDB.getInstance();
				sql = "call CinefyDB.stampa_domande_ad(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, username);
			}
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					return null;
				do {
					int id = rs.getInt("id");
					String contenuto = rs.getString("contenuto");
					String beginnerName = rs.getString("BeginnerName");
					String advancedName = rs.getString("AdvancedName");
					ld.add(new Domanda(id, contenuto, beginnerName, advancedName));
				} while (rs.next());
			}
		} finally {
			s.close();
		}
		return ld;
	}

	public List<Domanda> getQuestionsFromABeg(String advancedName, String beginnerName) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		List<Domanda> ld = new ArrayList<>();
		String sql = "call CinefyDB.stampa_domande_ad_beg(?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, advancedName);
			s.setString(2, beginnerName);
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					return null;
				do {
					int id = rs.getInt("id");
					String contenuto = rs.getString("Contenuto");
					String begName = rs.getString("BeginnerName");
					String adName = rs.getString("AdvancedName");
					ld.add(new Domanda(id, contenuto, begName, adName));
				} while (rs.next());
			}
		}
		return ld;
	}

	public boolean addQuestion(String contenuto, String beginnerName, String advancedName) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call aggiungi_nuova_domanda(?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, contenuto);
			s.setString(2, beginnerName);
			s.setString(3, advancedName);
			s.executeUpdate();
			return true;
		}
	}

	public void manageQuestions(int id, String action) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		String sql = null;
		if (action.equals(ACCEPT)) {
			sql = "call CinefyDB.accetta_domanda(?);\r\n";
		} else if (action.equals(REJECT)) {
			sql = "call CinefyDB.rifiuta_domanda(?);\r\n";
		}
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setInt(1, id);
			s.executeUpdate();
		}
	}

	public Domanda getQuestion(int id) throws SQLException {
		Domanda d = null;
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.stampa_domanda(?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setInt(1, id);
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					return null;
				String contenuto = rs.getString("Contenuto");
				String beginnerName = rs.getString("BeginnerName");
				String advancedName = rs.getString("AdvancedName");
				d = new Domanda(id, contenuto, beginnerName, advancedName);
			}
		}
		return d;
	}
}
