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
import common.UserEmailGiaUsato;

/**
 * 
 * Classe stub relativa al cittadino. Contiene i vari metodi per la comuncazione con il server, inviando le richieste e ricevendo 
 * le risposte dal server. Sono presenti due metodi corrispondenti ai servizi che può richiedere il cittadino al server quali la 
 * ricerca di un centro vaccinale per comune e tipologia, visualizzare le informazioni di un centro e inserire un evento avverso, 
 * registrarsi a sistema ed effettuare il login.
 * 
 * <p>
 * <code>
 * s Variabile globale relativa all'oggetto di tipo Socket
 * in Variabile globale relativa allo stream di input
 * out Variabile globale relativa allo stream di output
 * ins Variabile globale relativa al BufferedReader
 * </code>
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public class CentroVaccinaleServiceStubCittadino implements CentroVaccinaleServiceCittadino {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Costruttore della classe
	 * @throws IOException Eccezione inserimento dati
	 */
	public CentroVaccinaleServiceStubCittadino() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT); // per connettersi ad un network diffrente usare
																	// indirizzo IP esterno invece di localhost
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	@SuppressWarnings("unchecked")
	@Override
	// funzione per cercare centri vaccinali per comune e tipologia, prende come
	// parametri i valori inseriti nella GUI
	/**
	 * Metodo utile a ricercare un centro vaccinale utilizzando comune e tipologia. La ricerca può restituire uno o più
	 * centri vaccinali oppure sollevare delle eccezioni.
	 * 
	 * @param comune Variabile che contiene il comune del centro vaccinale da ricercare
	 * @param cercatoTipo Variabile che contine il tipo di centro vaccinale da ricercare
	 * @param tmp Variabile d'appoggio di tipo Object
	 * @return Viene ritornato una lista i vari centri o in alternativa vengono 
	 * sollevate delle eccezioni
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * 
	 */
	
	public LinkedList<String[]> RicercaCentroComuneTipologia(String comune, String cercatoTipo)
			throws IOException, SQLException {
		Object tmp;
		out.writeObject("CITTADINO"); // protocollo di comunicazione --> selezione cittadino

		out.writeObject("RICERCACOMUNETIPOLOGIA"); // protocollo di comunicazione --> ricerca per comune e tipologia

		out.writeObject(comune);
		out.writeObject(cercatoTipo);

		try {
			tmp = in.readObject(); // legge risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { // varie eccezioni sollevate
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
	// funzione per visualizzare informazioni relative al centro vaccinale
	// ricercato, prende come parametri i valori inseriti nella GUI
	/**
	 * Metodo utile a ricercare un centro vaccinale per nome. In caso l'utente abbia effettuato l'accesso, ha anche
	 * la possibilità di inserire uno o più eventi avversi, alternativamente può solo visualizzare le informazioni
	 * del centro.
	 * 
	 * @param access Variabile booleana che indica se l'utente ha effettuato l'accesso oppure no
	 * @param sceltaEvento Variabile che contiene il tipo di evento avverso
	 * @param cercato Variabile che contiene il nome del centro vaccinale
	 * @param comune Variabile che contiene il comune in cui si trova il centro vaccinale
	 * @param cercatoTipo Variabile che identifica il tipo di centro vaccinale
 	 * @param gravita Variabile che contiene la gravità dell'evento avverso (da 1 a 5)
	 * @param nota Variabile che contiene eventuali commenti opzionali riguardo l'evento avverso
	 * @return Vengono ritornate le informazioni relative al centro vaccinale o in alternativa vengono sollevate delle
	 * eccezioni
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CentroVaccinaleNonEsistente Nel caso in cui il centro vaccinale non sia registrato in database 
	 * @throws CentriVaccinaliNonEsistenti
	 * @throws CittadinoNonVaccinatoNelCentro Nel caso in cui il cittadino non risulti vaccinato nel centro
	 * dichiarato
	 * 
	 */
	
	public String VisualizzaCentro(boolean access, String sceltaEvento, String cercato, String comune,
			String cercatoTipo, int gravita, String nota) throws IOException, SQLException, CentroVaccinaleNonEsistente,
			CentriVaccinaliNonEsistenti, CittadinoNonVaccinatoNelCentro {

		Object tmp;

		out.writeObject("CITTADINO"); // protocollo di comunicazione --> selezione cittadino

		if (!access) // se non e' stato effettuato il login si accede all'area ad accesso libero
			out.writeObject("VISCENTRO"); // protocollo di comunicazione --> visualizza informazioni centro vaccinale
		else {
			out.writeObject("EVENTOAVV"); // protocollo di comunicazione --> visualizza centro + evento avverso
		}

		out.writeObject(sceltaEvento);
		out.writeObject(cercato);
		out.writeObject(comune);
		out.writeObject(cercatoTipo);
		out.writeObject(gravita);
		out.writeObject(nota);

		try {
			tmp = in.readObject(); // legge risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { // varie eccezioni sollevate
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

	@Override
	// funzione per registrare un cittadino nel sistema, vengono usati come
	// parametri i dati inseriti nella GUI
	/**
	 * Metodo utile ad inviare la richiesta di registrazione di un cittadino a sistema.
	 * 
	 * @param nome Variabile che contiene il nome del cittadino
	 * @param cognome Variabile che contiene il cognome del cittadino
	 * @param email Variabile che contiene l'email del cittadino
	 * @param username Variabile che contiene l'username con il quale avviene la registrazione
	 * @param password Variabile che contiene la password con la quale avviene la registrazione
	 * @param cf Variabile che contiene il codice fiscale del cittadino
	 * @param id Variabile che contiene l'id vaccinazione del cittadino
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws CittadinoGiaRegistrato Nel caso in cui il cittadino risulti già registrato
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CittadinoNonVaccinato Nel caso in cui il cittadino non risulti vaccinato
	 * @throws CodiceFiscaleErrato Nel caso in cui il codice fiscale risulti errato
 	 * @throws UserEmailGiaUsato Nel caso username e password risultino già utilizzati
	 */
	
	public void registraCittadino(String nome, String cognome, String email, String username, String password,
			String cf, int id) throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato,
			CodiceFiscaleErrato, UserEmailGiaUsato {
		Object tmp;

		out.writeObject("CITTADINO"); // protocollo di comunicazione --> selezione cittadino
		out.writeObject("REGCITT"); // protocollo di comuncazione --> registrazione nuovo cittadino

		out.writeObject(nome);
		out.writeObject(cognome);
		out.writeObject(email);
		out.writeObject(username);
		out.writeObject(password);
		out.writeObject(cf);
		out.writeObject(id);

		try {
			tmp = in.readObject(); // legge risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { // varie eccezioni sollevate
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CittadinoGiaRegistrato) {
			throw (CittadinoGiaRegistrato) tmp;
		} else if (tmp instanceof UserEmailGiaUsato) {
			throw (UserEmailGiaUsato) tmp;

		} else if (tmp instanceof CittadinoNonVaccinato) {
			throw (CittadinoNonVaccinato) tmp;
		} else if (tmp instanceof CodiceFiscaleErrato) {
			throw (CodiceFiscaleErrato) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) // protocollo applicativo --> conferma

		{
			return;
		} else {
			throw new IOException();
		}
	}
	
	
	@Override
	// funzione per loggarsi all'interno del sistema, viene effettuato un controllo
	// sui dati inseriti nel frame HomeCittadino_Frame
	
	/**
	 * Metodo necessario ad inviare la richiesta di login al server.
	 * 
	 * @param id Variabile che contiene l'username del cittadino
	 * @param pw Variabile che contiene la password del cittadino
	 * @return Viene ritornato true se l'accesso va a buon fine, altrimenti false.
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * 
	 */
	
	public boolean Login(String id, String pw) throws SQLException, IOException {

		Object tmp;

		out.writeObject("CITTADINO"); // protocollo di comunicazione --> selezione cittadino

		out.writeObject("LOGIN"); // protocollo di comunicazione --> richiesta di accesso

		out.writeObject(id);
		out.writeObject(pw);

		try {
			tmp = in.readObject(); // ricevo risposta dallo skeleton in centrovaccinaleserviceskel
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) { // varie eccezioni sollevate
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
