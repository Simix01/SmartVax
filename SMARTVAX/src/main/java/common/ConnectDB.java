package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che consente la connessione al database.
 * 
 * <p>
 * <code>
 * database_connection_string Variabile globale che contiene la selezione del database nel DBMS
 * database_user_name Variabile globale che contiene l'username per accedere al DBMS
 * database_user_password Variabile globale che contiene la password per accedere al DBMS
 * </code>
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public class ConnectDB {

	private String database_connection_string = "jdbc:postgresql://localhost:5432/smartvax"; //database smartvax selezionato

	private String database_user_name;

	private String database_user_password;

	/**
	 * Metodo che istanzia la connessione con il DBMS, verifica le credenziali e in caso di verifica positiva ritorna l'istanza della
	 * connessione
	 * 
	 */
	public Connection connect() {
		Connection conn = null;

		try {

			conn = DriverManager.getConnection(database_connection_string, database_user_name, database_user_password); //istanzia la connessione al database

		} catch (SQLException e)

		{

			System.exit(0); //se le credenziali sono errate il processo viene killato con il metodo exit
		}

		return conn; //ritorna la connessione istanziata

	}

	/**
	 * Costruttore della classe
	 * 
	 * @param user Variabile che identifica l'username
	 * @param pass Variabile che identifica la password
	 */
	public ConnectDB(String user, String pass) {
		database_user_name = user;
		database_user_password = pass;
	}
}
