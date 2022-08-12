package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

	private String database_connection_string = "jdbc:postgresql://localhost:5432/smartvax";

	private String database_user_name;

	private String database_user_password;

	public Connection connect() {
		Connection conn = null;

		try {

			conn = DriverManager.getConnection(database_connection_string, database_user_name, database_user_password);

		} catch (SQLException e)

		{

			System.exit(0);
		}

		return conn;

	}

	public ConnectDB(String user, String pass) {
		database_user_name = user;
		database_user_password = pass;
	}
}
