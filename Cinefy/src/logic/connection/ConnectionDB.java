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
	private static ConnectionDB instance;
	private String username = "root";
	private String password = "password";
	private String dbUrl = "jdbc:mysql://localhost:3306/CinefyDB?useSSL=false";
	private static String driverClassName = "com.mysql.jdbc.Driver";

	private ConnectionDB() throws ClassNotFoundException {
		// Loading Dinamico del server
		Class.forName(driverClassName);
	}

	public static synchronized Connection getInstance() throws SQLException, ClassNotFoundException {
		// Si controlla che non ci sia un'altra istanza di connessione
		if (instance == null) {
			instance = new ConnectionDB();
		}
		return DriverManager.getConnection(instance.dbUrl, instance.username, instance.password);
	}

	public static void close(Connection c) throws SQLException {
		if (c != null) {
			c.close();
		}
	}
}
