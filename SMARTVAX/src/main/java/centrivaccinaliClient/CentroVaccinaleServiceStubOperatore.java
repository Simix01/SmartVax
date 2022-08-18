package centrivaccinaliClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;

import centrivaccinaliServer.Server;
import common.CapErrato;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleServiceOperatore;
import common.CodiceFiscaleErrato;

public class CentroVaccinaleServiceStubOperatore implements CentroVaccinaleServiceOperatore {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));

	public CentroVaccinaleServiceStubOperatore() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT); //per connettersi ad un network diffrente usare indirizzo IP esterno invece di localhost
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	//funzione per registrare un nuovo centro vaccinale, prende come parametri i valori inseriti nella GUI
	@Override
	public void registraCentroVaccinale(String nome, String tipoVia, String nomeVia, String numCiv, String comune,
			String sigProv, int cap, String tipologia) throws IOException, CentroVaccinaleGiaRegistrato, CapErrato {
		Object tmp;
		out.writeObject("OPERATORE"); //protocollo applicativo --> seleziona operatore
		out.writeObject("REGCENTRO"); //protocollo applicativo --> registrare un centro
		out.writeObject(nome);
		out.writeObject(tipoVia);
		out.writeObject(nomeVia);
		out.writeObject(numCiv);
		out.writeObject(comune);
		out.writeObject(sigProv);
		out.writeObject(cap);
		out.writeObject(tipologia);

		try {
			tmp = in.readObject(); //riceve una risposta dallo skeleton centrivaccinaliserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof CentroVaccinaleGiaRegistrato) { //varie eccezioni sollevate 
			throw (CentroVaccinaleGiaRegistrato) tmp;
		} else if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof CapErrato) {
			throw (CapErrato) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) { //protocollo applicativo --> conferma
			return;
		} else {
			throw new IOException();
		}
	}

	//funzione per registrare una nuova vaccinazione, prende come parametri i valori inseriti nella GUI
	@Override
	public void registraVaccinato(String nome, String cognome, String nomeCentro, String codFiscale, String vaccino,
			Date date) throws IOException, SQLException, CentroVaccinaleNonEsistente, CodiceFiscaleErrato {
		Object tmp;
		out.writeObject("OPERATORE"); //protocollo applicativo --> selezione operatore
		out.writeObject("REGVACC"); //protocollo applicativo --> registra vaccinato
		out.writeObject(nome);
		out.writeObject(cognome);
		out.writeObject(nomeCentro);
		out.writeObject(codFiscale);
		out.writeObject(vaccino);
		out.writeObject(date);
		try {
			tmp = in.readObject(); //riceve risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof CentroVaccinaleNonEsistente) //eccezioni sollevate
			throw (CentroVaccinaleNonEsistente) tmp;
		else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CodiceFiscaleErrato) {
			throw (CodiceFiscaleErrato) tmp;
		} else if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) { //protocollo applicativo --> conferma
			return;
		} else {
			throw new IOException();
		}

	}
}
