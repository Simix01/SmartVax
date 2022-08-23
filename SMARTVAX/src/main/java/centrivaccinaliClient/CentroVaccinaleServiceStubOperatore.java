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

/**
 * Classe stub relativa all'operatore. Contiene i vari metodi per la comuncazione con il server, inviando le richieste e ricevendo 
 * le risposte dal server. Sono presenti due metodi corrispondenti ai servizi che può richiedere l'operatore al server quali la 
 * registrazione di un centro vaccinale e la registrazione di un vaccinato.
 * 
 * <p>
 * <code>
 * s Variabile globale relativa all'oggetto di tipo Socket
 * in Variabile globale relativa allo stream di input
 * out Variabile globale relativa allo stream di output
 * ins Variabile globale relativa al BufferedReader
 * </code>
 * 
 *
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public class CentroVaccinaleServiceStubOperatore implements CentroVaccinaleServiceOperatore {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Costruttore della classe
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 */
	public CentroVaccinaleServiceStubOperatore() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT); //per connettersi ad un network diffrente usare indirizzo IP esterno invece di localhost
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	@Override
	//funzione per registrare un nuovo centro vaccinale, prende come parametri i valori inseriti nella GUI
	/**
	 * Metodo che permette di inviare una richiesta al server per inserire un nuovo centro vaccinale.
	 * 
	 * @param nome Variabile che identifica il nome del centro vaccinale
	 * @param tipoVia Variabile che identifica il tipo di via del centro vaccinale
	 * @param nomeVia Variabile che identifica il nome della via del centro vaccinale
	 * @param numCiv Variabile che identifica il numero civico della via del centro vaccinale
	 * @param comune Variabile che identifica il comune del centro vaccinale
	 * @param sigProv Variabile che identifica la sigla della provincia del centro vaccinale
	 * @param cap Variabile che identifica il CAP del centro vaccinale
	 * @param tipologia Variabile che identifica la tipologia del centro vaccinale
	 * @throws IOException Nel caso in cui l'utente non inserisca nessun valore
	 * @throws CentroVaccinaleGiaRegistrato Nel caso in cui il centro vaccinale risulti già registrato
	 * @throws CapErrato Nel caso il CAP inserito risulti errato
	 * 
	 */
	
	public void registraCentroVaccinale(String nome, String tipoVia, String nomeVia, String numCiv, String comune,
			String sigProv, int cap, String tipologia) throws IOException, CentroVaccinaleGiaRegistrato, CapErrato {
		Object tmp;
		out.writeObject("OPERATORE"); //protocollo di comunicazione --> seleziona operatore
		out.writeObject("REGCENTRO"); //protocollo di comunicazione --> registrare un centro
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

	@Override
	//funzione per registrare una nuova vaccinazione, prende come parametri i valori inseriti nella GUI
	/**
	 * Metodo che permette di inviare una richiesta al server per registrare un nuovo vaccinato.
	 * 
	 * @param nome Variabile che identifica il nome del vaccinato
	 * @param cognome Variabile che identifica il cognome del vaccinato
	 * @param nomeCentro Variabile che identifica il nome del centro vaccinale in cui il cittadino
	 * è stato vaccinato
	 * @param codFiscale Variabile che identifica il codice fiscale del vaccinato
	 * @param vaccino Variabile che identifica il tipo di vaccino del vaccinato
	 * @param date Variabile che identifica la data di vaccinazione
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CentroVaccinaleNonEsistente Nel caso in cui il centro vaccinale inserito non esista nel database
	 * @throws CodiceFiscaleErrato Nel caso in cui il codice fiscale inserito sia errato
	 */ 
	
	public void registraVaccinato(String nome, String cognome, String nomeCentro, String codFiscale, String vaccino,
			Date date) throws IOException, SQLException, CentroVaccinaleNonEsistente, CodiceFiscaleErrato {
		Object tmp;
		out.writeObject("OPERATORE"); //protocollo di comunicazione --> selezione operatore
		out.writeObject("REGVACC"); //protocollo di comunicazione --> registra vaccinato
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
