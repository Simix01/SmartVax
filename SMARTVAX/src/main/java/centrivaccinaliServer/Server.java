package centrivaccinaliServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import common.CentroVaccinaleService;
import common.CentroVaccinaleServiceImpl;
import common.ConnectDB;

public class Server {

	public static final int PORT = 11100; //porta per il collegamento

	Connection conn;

	public Server(String user, String password) throws IOException, SQLException {
		ConnectDB c = new ConnectDB(user, password); //chiama classe connectDB per la connessione con user e password presi dalla GUI

		conn = c.connect(); //effettua la connessione con il database selezionato da ConnectDB.java

	}

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
 
