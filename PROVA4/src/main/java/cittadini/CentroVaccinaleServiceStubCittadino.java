package cittadini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.login.LoginContext;

import centrivaccinaliServer.Server;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleService;
import common.CentroVaccinaleServiceCittadino;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;
import common.ConnectDB;

public class CentroVaccinaleServiceStubCittadino implements CentroVaccinaleServiceCittadino {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));
	String CF;

	public CentroVaccinaleServiceStubCittadino() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	public void menuCittadino() throws IOException, SQLException {
		out.writeObject("CITTADINO");
		int scelta;
		System.out.println("1 - ACCESSO LIBERO");
		System.out.println("2 - REGISTRAZIONE CITTADINO");
		System.out.println("3 - SEI GIA' REGISTRATO? EFFETTUA L'ACCESSO");
		System.out.print("-> ");

		scelta = Integer.parseInt(ins.readLine());
		switch (scelta) {
		case 1:
			try {
				VisualizzaCentro(false, null);
			} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {

			}
			break;
		case 2:
			try {
				registraCittadino();
			} catch (IOException | CittadinoGiaRegistrato | SQLException | CittadinoNonVaccinato e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			menuLogin();
			break;
		}
	}

	@Override
	public void VisualizzaCentro(boolean access, String codFiscale)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti {

		Object tmp;

		if (!access)
			out.writeObject("VISCENTRO");
		else {
			out.writeObject("EVENTOAVV");
			out.writeObject(CF);
		}

		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CentroVaccinaleNonEsistente) {
			throw (CentroVaccinaleNonEsistente) tmp;
		} else if (tmp instanceof CentriVaccinaliNonEsistenti) {
			throw new CentriVaccinaliNonEsistenti();
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) {
			return;
		} else {
			throw new IOException();
		}

	}

	@Override
	public void registraCittadino() throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato {
		Object tmp;

		out.writeObject("REGCITT");

		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CittadinoGiaRegistrato) {
			throw (CittadinoGiaRegistrato) tmp;
		} else if (tmp instanceof CittadinoNonVaccinato) {
			throw new CittadinoNonVaccinato();
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) {
			return;
		} else {
			throw new IOException();
		}
	}

	private void menuLogin() throws IOException, SQLException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		out.writeObject("LOGIN");
		try {
			while (true) {
				System.out.print("Inserisci userId: ");
				String id = in.readLine();
				System.out.print("Inserisci password: ");
				String pw = in.readLine();
				if (Login(id, pw))
					break;
				else
					System.out.println("Accesso negato, reinserire le credenziali");
			}

			System.out.println("Accesso eseguito\n");

			while (true) {
				System.out.println("1 - VISUALIZZA CENTRO");
				System.out.println("2 - INSERIRE EVENTO AVVERSO");
				System.out.print("-> ");
				int scelta = Integer.parseInt(in.readLine());
				switch (scelta) {
				case 1:
					try {
						VisualizzaCentro(false, null);
					} catch (SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // qui non è possibile inserire effetti collaterali per via del passaggio del
						// valore booleano false
					break;
				case 2:
					try {
						VisualizzaCentro(true, CF);
					} catch (SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // qui è possibile inserire effetti collaterali per via del passaggio del valore
						// booleano true
					break;
				default:
					System.out.println("SCELTA NON VALIDA!!!");
					menuCittadino();
					break;
				}
			}
		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
		} catch (NumberFormatException e) {
			System.err.println(
					"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
		}
	}

	private boolean Login(String id, String pw) throws SQLException {

		ConnectDB c = new ConnectDB("postgres", "Nhuari062799!");
		Connection connection = c.connect();

		String queryVerificaLogin = "SELECT codfiscale" + " FROM cittadiniregistrati" + " WHERE username='" + id
				+ "' AND password= '" + pw + "'";

		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery(queryVerificaLogin);

		if (rs.isBeforeFirst()) {
			rs.next();
			CF = rs.getString("codFiscale");
			return true;
		} else
			return false;
	}
}
