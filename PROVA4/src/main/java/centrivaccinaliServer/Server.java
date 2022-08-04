package centrivaccinaliServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import common.CentroVaccinaleService;
import common.CentroVaccinaleServiceImpl;
import common.ConnectDB;

public class Server {

	public static final int PORT = 11100;


	public static void main(String[] args) throws Exception {

		ConnectDB c = new ConnectDB("postgres", "Nhuari062799!");

		Connection conn = c.connect();

		try (ServerSocket ser = new ServerSocket(PORT)) {
			CentroVaccinaleService g = new CentroVaccinaleServiceImpl(conn);

			while (true) {
				Socket s = ser.accept();
				CentroVaccinaleServiceSkel cs = new CentroVaccinaleServiceSkel(s, g);
				cs.start();
			}
		}

	}
}
