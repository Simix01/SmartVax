package centrivaccinaliServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import common.CentroVaccinaleService;
import common.CentroVaccinaleServiceImpl;
import common.ConnectDB;

/**
 * Classe che permette la connessione con il DataBase, inoltre istanzia il server socket e resta in ascolto
 * per eventuali richieste di connessione, facendo partire il metodo run dello skeleton.
 * 
 * <p>
 * <code>
 * PORT Porta per il collegamento
 * conn Oggetto di tipo Connection
 * </code>
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public class Server {

	public static final int PORT = 11100; //porta per il collegamento

	Connection conn;

	/**
	 * Metodo per effettuare la connessione con il database.
	 * 
	 * @param user Variabile che contiene l'user per l'accesso al database
	 * @param password Variabile che contiene la password per l'accesso al database
	 * @throws IOException Nel caso in cui l'utente non inserisca nulla
	 * @throws SQLException Nel caso in cui il databse ritorni un errore
	 */
	public Server(String user, String password) throws IOException, SQLException {
		ConnectDB c = new ConnectDB(user, password); //chiama classe connectDB per la connessione con user e password presi dalla GUI

		conn = c.connect(); //effettua la connessione con il database selezionato da ConnectDB.java

	}

	/**
	 * Metodo con cui il server rimane in ascolto in attesa di richieste
	 * 
	 */
	public void Start() throws IOException {
		try (ServerSocket ser = new ServerSocket(PORT)) { //istanzia la server socket con la porta definita static final
			CentroVaccinaleService g = new CentroVaccinaleServiceImpl(conn); //istanzia l'interfaccia e la sua implementazione

			while (true) { 
				Socket s = ser.accept(); //rimane in ascolto per richieste di connessione
				CentroVaccinaleServiceSkel cs = new CentroVaccinaleServiceSkel(s, g); //istanzia lo skeleton 
				cs.start(); //fa eseguire il metodo run della classe centrovaccinaleserviceskel che implementa la classe Thread
			}
		}
	}
}
 
