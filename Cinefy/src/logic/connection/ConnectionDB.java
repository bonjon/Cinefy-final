package logic.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Classe che stabilisce la connessione con il db di Film, è stato utilizzato come pattern il Singleton
 * per evitare che ci siano piu connessioni ripetute con il db.
 */

public class ConnectionDB {

	// Dichiarazione variabili
	private static ConnectionDB INSTANCE;
	private String username = "root";
	private String password = "password";
	//private String password = "Password_98";
	//private String DB_URL = "jdbc:mysql://localhost:3306/CinefyDB";
	private String DB_URL = "jdbc:mysql://localhost:3306/CinefyDB?useSSL=false";
	private static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	private ConnectionDB() {
		// Loading Dinamico del server
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized Connection getInstance() throws SQLException {
		// Si controlla che non ci sia un'altra istanza di connessione
		if (INSTANCE == null) {
			INSTANCE = new ConnectionDB();
		}
		try {
			return DriverManager.getConnection(INSTANCE.DB_URL, INSTANCE.username, INSTANCE.password);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void close(Connection c) {
		try {
			if (c != null) {
				c.close();
				c = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
