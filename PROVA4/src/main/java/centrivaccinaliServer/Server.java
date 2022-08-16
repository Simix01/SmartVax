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

	public static final int PORT = 11100;

	Connection conn;

	public Server(String user, String password) throws IOException, SQLException {
		ConnectDB c = new ConnectDB(user, password);

		conn = c.connect();

	}

	public void Start() throws IOException {
		try (ServerSocket ser = new ServerSocket(PORT)) {
			CentroVaccinaleService g = new CentroVaccinaleServiceImpl(conn);

			while (true) {
				Socket s = ser.accept();
				CentroVaccinaleServiceSkel cs = new CentroVaccinaleServiceSkel(s, g);
				cs.start();
			}
		}
	}

	public static void main(String[] args) {
		ConnectDB c = new ConnectDB("postgres", "Nhuari062799!");

		Connection conn = c.connect();
		try (ServerSocket ser = new ServerSocket(PORT)) {
			CentroVaccinaleService g = new CentroVaccinaleServiceImpl(conn);

			while (true) {
				Socket s = ser.accept();
				CentroVaccinaleServiceSkel cs = new CentroVaccinaleServiceSkel(s, g);
				cs.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
