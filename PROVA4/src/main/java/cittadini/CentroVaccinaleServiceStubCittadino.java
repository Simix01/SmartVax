package cittadini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import centrivaccinaliServer.Server;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleServiceCittadino;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;
import common.ConnectDB;

public class CentroVaccinaleServiceStubCittadino implements CentroVaccinaleServiceCittadino {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));

	public CentroVaccinaleServiceStubCittadino() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	@Override
	public LinkedList<String[]> RicercaCentroComuneTipologia(String comune, String cercatoTipo)
			throws IOException, SQLException {
		Object tmp;
		out.writeObject("CITTADINO");

		out.writeObject("RICERCACOMUNETIPOLOGIA");

		out.writeObject(comune);
		out.writeObject(cercatoTipo);

		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof LinkedList<?>) {
			return (LinkedList<String[]>) tmp;
		} else {
			throw new IOException();
		}

	}

	@Override
	public String VisualizzaCentro(boolean access, String sceltaEvento, String cercato, String comune,
			String cercatoTipo, int gravita, String nota)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti {

		Object tmp;

		out.writeObject("CITTADINO");

		if (!access)
			out.writeObject("VISCENTRO");
		else {
			out.writeObject("EVENTOAVV");
		}

		out.writeObject(sceltaEvento);
		out.writeObject(cercato);
		out.writeObject(comune);
		out.writeObject(cercatoTipo);
		out.writeObject(gravita);
		out.writeObject(nota);

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
		} else if (tmp instanceof String) {
			return (String) tmp;
		} else {
			throw new IOException();
		}

	}

	@Override
	public void registraCittadino(String nome, String cognome, String email, String username, String password,
			String cf, int id) throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato {
		Object tmp;

		out.writeObject("CITTADINO");
		out.writeObject("REGCITT");

		out.writeObject(nome);
		out.writeObject(cognome);
		out.writeObject(email);
		out.writeObject(username);
		out.writeObject(password);
		out.writeObject(cf);
		out.writeObject(id);

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

	@Override
	public boolean Login(String id, String pw) throws SQLException, IOException {

		Object tmp;

		out.writeObject("CITTADINO");

		out.writeObject("LOGIN");

		out.writeObject(id);
		out.writeObject(pw);

		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof Boolean) {
			return (Boolean) tmp;
		} else {
			throw new IOException();
		}
	}
}
