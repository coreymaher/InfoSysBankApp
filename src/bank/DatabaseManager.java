package bank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseManager {
	
	private static DatabaseManager instance;

	private DatabaseManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println("Could not load mysql driver");
		}
	}

	public static DatabaseManager getInstance() {
		if ( instance == null ) {
			instance = new DatabaseManager();
		}

		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost/cam3819", "cam3819", "Iphahfeek1");
	}
}
