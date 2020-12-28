package logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logic.connection.ConnectionDB;
import logic.entities.Risposta;

/*
 * Classe dao di Risposta che avrà il compito di mediare tra la rappresentazione in 
 * memoria delle istanze e la rappresentazione su layer di persistenza.
 */

public class RispostaDAO {

	public static final String ACCEPT = "accept";
	public static final String REJECT = "reject";

	@SuppressWarnings("resource")
	public List<Risposta> getAnswers(String username, String role) throws SQLException {
		Connection conn = null;
		String sql = null;
		PreparedStatement s = null;
		List<Risposta> lr = new ArrayList<>();
		try {
			if (role.equals("admin")) {
				conn = ConnectionDB.getInstance();
				sql = "call CinefyDB.stampa_risposte_in_coda();\r\n";
				s = conn.prepareStatement(sql);
			}
			if (role.equals("advanced")) {
				conn = ConnectionDB.getInstance();
				sql = "call CinefyDB.stampa_risposte(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, username);
			}
			if (role.equals("advanced2")) {
				conn = ConnectionDB.getInstance();
				sql = "call CinefyDB.get_pending_risp(?);\r\n";
				s = conn.prepareStatement(sql);
				s.setString(1, username);
			}
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					return Collections.emptyList();
				do {
					int id = rs.getInt("id");
					String contenuto = rs.getString("contenuto");
					String beginnerName = rs.getString("BeginnerName");
					String advancedName = rs.getString("AdvancedName");
					int idDomanda = rs.getInt("idDomanda");
					lr.add(new Risposta(id, contenuto, advancedName, beginnerName, idDomanda));
				} while (rs.next());
			}
		} finally {
			s.close();
		}
		return lr;
	}

	public Risposta getAnswer(String beginnerName, int idDomanda) throws SQLException {
		Risposta r = null;
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.stampa_risposta(?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, beginnerName);
			s.setInt(2, idDomanda);
			try (ResultSet rs = s.executeQuery()) {
				if (!rs.first())
					return null;
				int id = rs.getInt("id");
				String contenuto = rs.getString("Contenuto");
				String advancedName = rs.getString("AdvancedName");
				r = new Risposta(id, contenuto, beginnerName, advancedName, idDomanda);
			}
		}
		return r;
	}

	public boolean addAnswer(String contenuto, String beginnerName, String advancedName, int idDomanda)
			throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		String sql = "call aggiungi_nuova_risposta(?,?,?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, contenuto);
			s.setString(2, beginnerName);
			s.setString(3, advancedName);
			s.setInt(4, idDomanda);
			s.executeUpdate();
			return true;
		}
	}

	public void manageAnswers(int id, String action) throws SQLException {
		Connection conn = ConnectionDB.getInstance();
		String sql = null;
		if (action.equals(ACCEPT)) {
			sql = "call CinefyDB.accetta_risposta(?);\r\n";
		} else if (action.equals(REJECT)) {
			sql = "call CinefyDB.rifiuta_risposta(?);\r\n";
		}
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setInt(1, id);
			s.executeUpdate();
		}
	}

	public int checkVote(String beginnerName, String idRisposta) throws SQLException {
		int i = 0;
		Connection conn = ConnectionDB.getInstance();
		String sql = "call CinefyDB.get_vote_answer(?,?);\r\n";
		try (PreparedStatement s = conn.prepareStatement(sql)) {
			s.setString(1, beginnerName);
			s.setInt(2, Integer.parseInt(idRisposta));
			try (ResultSet rs = s.executeQuery()){
				if (!rs.first())
					return 0;
				i = rs.getInt("Voto");
			}
		}
		return i;
	}
}
