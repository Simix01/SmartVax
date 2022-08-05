package centrivaccinaliClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

import centrivaccinaliServer.Server;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleService;
import common.CentroVaccinaleServiceOperatore;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;

public class CentroVaccinaleServiceStubOperatore implements CentroVaccinaleServiceOperatore {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));

	public CentroVaccinaleServiceStubOperatore() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	public void menuOperatori() throws NumberFormatException, IOException {

		String scelta2;
		
		while (true) {
			out.writeObject("OPERATORE");
			System.out.println("1 - REGISTRAZIONE CENTRO VACCINALE");
			System.out.println("2 - REGISTRAZIONE VACCINATO");
			System.out.print("-> ");
			int scelta = Integer.parseInt(ins.readLine());

			switch (scelta) {
			case 1:
				try {
					registraCentroVaccinale();
				} catch (IOException | CentroVaccinaleGiaRegistrato e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					registraVaccinato();
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				System.err.println("ERRORE");
				break;
			}
			System.out.println("Vuoi uscire? s/n");
			scelta2 = ins.readLine();
			if (scelta2.equals("s"))
				break;
		}
	}

	@Override
	public void registraCentroVaccinale() throws IOException, CentroVaccinaleGiaRegistrato {
		Object tmp;
		out.writeObject("REGCENTRO");
		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof CentroVaccinaleGiaRegistrato) {
			throw (CentroVaccinaleGiaRegistrato) tmp;
		} else if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) {
			return;
		} else {
			throw new IOException();
		}

	}

	@Override
	public void registraVaccinato() throws IOException, SQLException {
		Object tmp;
		out.writeObject("REGVACC");
		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) {
			return;
		} else {
			throw new IOException();
		}

	}
}
