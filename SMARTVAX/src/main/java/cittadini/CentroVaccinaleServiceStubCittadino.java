package cittadini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;

import centrivaccinaliServer.Server;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleServiceCittadino;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;
import common.CittadinoNonVaccinatoNelCentro;
import common.CodiceFiscaleErrato;

public class CentroVaccinaleServiceStubCittadino implements CentroVaccinaleServiceCittadino {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));

	public CentroVaccinaleServiceStubCittadino() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT); //per connettersi ad un network diffrente usare indirizzo IP esterno invece di localhost
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	//funzione per cercare centri vaccinali per comune e tipologia, prende come parametri i valori inseriti nella GUI
	@SuppressWarnings("unchecked")
	@Override
	public LinkedList<String[]> RicercaCentroComuneTipologia(String comune, String cercatoTipo)
			throws IOException, SQLException {
		Object tmp;
		out.writeObject("CITTADINO"); //protocollo applicativo --> selezione cittadino

		out.writeObject("RICERCACOMUNETIPOLOGIA"); //protocollo applicativo --> ricerca per comune e tipologia

		out.writeObject(comune);
		out.writeObject(cercatoTipo);

		try {
			tmp = in.readObject(); //legge risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { //varie eccezioni sollevate
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof LinkedList<?>) {
			return (LinkedList<String[]>) tmp;
		} else {
			throw new IOException();
		}

	}

	//funzione per visualizzare informazioni relative al centro vaccinale ricercato, prende come parametri i valori inseriti nella GUI
	@Override
	public String VisualizzaCentro(boolean access, String sceltaEvento, String cercato, String comune,
			String cercatoTipo, int gravita, String nota) throws IOException, SQLException, CentroVaccinaleNonEsistente,
			CentriVaccinaliNonEsistenti, CittadinoNonVaccinatoNelCentro {

		Object tmp;

		out.writeObject("CITTADINO"); //protocollo applicativo --> selezione cittadino

		if (!access) //se non e' stato effettuato il login si accede all'area ad accesso libero
			out.writeObject("VISCENTRO"); //protocollo applicativo --> visualizza informazioni centro vaccinale
		else {
			out.writeObject("EVENTOAVV"); //protocollo applicativo --> visualizza centro + evento avverso
		}

		out.writeObject(sceltaEvento);
		out.writeObject(cercato);
		out.writeObject(comune);
		out.writeObject(cercatoTipo);
		out.writeObject(gravita);
		out.writeObject(nota);

		try {
			tmp = in.readObject(); //legge risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { //varie eccezioni sollevate
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CentroVaccinaleNonEsistente) {
			throw (CentroVaccinaleNonEsistente) tmp;
		} else if (tmp instanceof CentriVaccinaliNonEsistenti) {
			throw (CentriVaccinaliNonEsistenti) tmp;
		} else if (tmp instanceof CittadinoNonVaccinatoNelCentro) {
			throw (CittadinoNonVaccinatoNelCentro) tmp;
		} else if (tmp instanceof String) {
			return (String) tmp;
		} else {
			throw new IOException();
		}

	}

	//funzione per registrare un cittadino nel sistema, vengono usati come parametri i dati inseriti nella GUI
	@Override
	public void registraCittadino(String nome, String cognome, String email, String username, String password,
			String cf, int id)
			throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato, CodiceFiscaleErrato {
		Object tmp;

		out.writeObject("CITTADINO"); //protocollo applicativo --> selezione cittadino
		out.writeObject("REGCITT"); //protocollo applicativo --> registrazione nuovo cittadino

		out.writeObject(nome);
		out.writeObject(cognome);
		out.writeObject(email);
		out.writeObject(username);
		out.writeObject(password);
		out.writeObject(cf);
		out.writeObject(id);

		try {
			tmp = in.readObject(); //legge risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { //varie eccezioni sollevate
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CittadinoGiaRegistrato) {
			throw (CittadinoGiaRegistrato) tmp;
		} else if (tmp instanceof CittadinoNonVaccinato) {
			throw (CittadinoNonVaccinato) tmp;
		} else if (tmp instanceof CodiceFiscaleErrato) {
			throw (CodiceFiscaleErrato) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) //protocollo applicativo --> conferma

		{
			return;
		} else {
			throw new IOException();
		}
	}

	//funzione per loggarsi all'interno del sistema, viene effettuato un controllo sui dati inseriti nel frame HomeCittadino_Frame
	@Override
	public boolean Login(String id, String pw) throws SQLException, IOException {

		Object tmp;

		out.writeObject("CITTADINO"); //protocollo applicativo --> selezione cittadino

		out.writeObject("LOGIN"); //protocollo applicativo --> richiesta di accesso

		out.writeObject(id);
		out.writeObject(pw);

		try {
			tmp = in.readObject(); //ricevo risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { //varie eccezioni sollevate
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
