package centrivaccinaliServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;

import common.CapErrato;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleService;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;
import common.CittadinoNonVaccinatoNelCentro;
import common.CodiceFiscaleErrato;
import common.UserEmailGiaUsato;

/**
 * Classe skeleton del server. Contiene vari metodi per la comunicazione con il client e l'esecuzione delle operazioni richieste.
 * Sono presenti due menu, cittadino e operatore. Sono, inoltre, presenti vari metodi per l'interpretazione dei comandi ricevuti
 * dal client.
 * 
 * <p>
 * <code>
 * s Variabile globale relativa all'oggetto di tipo Socket
 * in Variabile globale relativa allo stream di input
 * out Variabile globale relativa allo stream di output
 * g Variabile globale relativa all'oggetto dell'interfaccia
 * </code>
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public class CentroVaccinaleServiceSkel extends Thread {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private CentroVaccinaleService g;

	/**
	 * Costruttore della classe
	 * @param s Variabile globale relativa all'oggetto di tipo Socket
	 * @param c Variabile globale relativa allo stream di input
	 * @throws NullPointerException Eccezione
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 */
	public CentroVaccinaleServiceSkel(Socket s, CentroVaccinaleService c) throws NullPointerException, IOException {
		if (s == null || c == null)
			throw new NullPointerException();
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
		g = c;
	}

	// skeleton eredita la classe thread quindi esegue fino a quando non viene
	// killato il processo
	/**
	 * Metodo run che continua ad essere eseguito permettendo di accedere al menu operatore o a quello 
	 * cittadino
	 * 
	 */
	public void run() {
		while (true) {
			try {
				int user = interpretaUser((String) in.readObject()); // chiama la funzione per interpretare l'identita'
																		// dell'utente
				switch (user) {
				case 1:
					MenuOperatore(); // l'utente che utilizza l'app e' un operatore
					break;

				case 2:
					MenuCittadino(); // l'utente che utilizza l'app e' un cittadino
					break;
				}
			} catch (ClassNotFoundException | IOException | CentroVaccinaleNonEsistente | SQLException
					| CittadinoNonVaccinatoNelCentro | CapErrato | CodiceFiscaleErrato | UserEmailGiaUsato e) { // varie
																												// eccezioni
																												// sollevate
				System.err.println(e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}

	// operazioni che puo' svolgere un operatore
	/**
	 * Metodo che permette di accedere alle varie funzioni eseguibili dall'operatore. Vengono interpreati i
	 * comandi ricevuti dal client e viene richiamato il metodo ipportuno.
	 * 
	 * @param scelta Variabile che contiene l'interpretazione dell'operazione ricevuta dal client. Corrispondente a 1 per registrare un nuovo centro
	 * vaccinale o 2 per registrare un vaccinato
	 * @throws CentroVaccinaleNonEsistente Nel caso in cui il centro vaccinale non esista
	 * @throws CapErrato Nel caso in cui il cap sia errato
	 * @throws CodiceFiscaleErrato Nel caso in cui il codice fiscale sia errato
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws UserEmailGiaUsato Nel caso in cui username o email siano già stati usati
	 */
	private void MenuOperatore()
			throws CentroVaccinaleNonEsistente, CapErrato, CodiceFiscaleErrato, IOException, UserEmailGiaUsato {
		try {

			int scelta = interpretaOperatore((String) in.readObject()); // interpreto l'operazione selezionata

			switch (scelta) {
			case 1: // registrazione di un nuovo centro, vengono presi i dati dallo stream di
					// comunicazione
				try {
					g.registraCentroVaccinale((String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (int) in.readObject(), (String) in.readObject());
					out.writeObject("OK"); // protocollo applicativo --> conferma
				} catch (CentroVaccinaleGiaRegistrato | CapErrato e) { // eccezioni sollevate
					out.writeObject(e);
				}
				break;
			case 2: // registrazione di un nuovo vaccinato, vengono presi i dati dallo stream di
					// comunicazione
				try {
					g.registraVaccinato((String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (Date) in.readObject());
					out.writeObject("OK"); // protocollo applicativo --> conferma
				} catch (CentroVaccinaleNonEsistente | CodiceFiscaleErrato | SQLException e) { // eccezioni sollevate
					out.writeObject(e);
					e.printStackTrace();
				}
				break;
			default: {
				// si e' verificato un errore
				MenuOperatore();
			}
			}

		} catch (IOException | NumberFormatException | ClassNotFoundException e) {
			out.writeObject(e);
		}
	}

	// operazioni che puo' svolgere un cittadino
	/**
	 * Metodo che permette di accedere alle varie funzioni eseguibili dal cittadino. Vengono interpreati i
	 * comandi ricevuti dal client e viene richiamato il metodo ipportuno.
	 * 
	 * @param scelta Variabile che contiene l'interpretazione dell'operazione ricevuta dal client. Corrispondente a 1 per 
	 * visualizzare le informazioni di un centro vaccinale, 2 per effettuare la registrazione, 3 per effettuare il login, 4 per 
	 * ricercare un centro tramite comune e tipologia e 5 per inserire un evento avverso
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CittadinoNonVaccinatoNelCentro Nel caso in cui il cittadino non sia stato registrato in quel centro vaccinale specifico
	 * @throws UserEmailGiaUsato Nel caso in cui username o email siano già stati usati
	 */
	private void MenuCittadino() throws SQLException, CittadinoNonVaccinatoNelCentro, UserEmailGiaUsato {
		try {
			int scelta = interpretaCittadino((String) in.readObject()); // interpreto l'operazione selezionata

			switch (scelta) {
			case 1: // visualizza informazioni centro vaccinale, nome fornito dal cittadino lato
					// client
				try {

					out.writeObject(g.VisualizzaCentro(false, (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (Integer) in.readObject(),
							(String) in.readObject()));
				} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti
						| CittadinoNonVaccinatoNelCentro e) {
					out.writeObject(e);
				}
				break;
			case 2: // il cittadino si vuole registrare
				try {
					g.registraCittadino((String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(Integer) in.readObject());
					out.writeObject("OK");
				} catch (IOException | CittadinoGiaRegistrato | SQLException | CittadinoNonVaccinato
						| CodiceFiscaleErrato | UserEmailGiaUsato e) { // eccezioni sollevate
					out.writeObject(e);
				}
				break;
			case 3: // il cittadino richiede di accedere fornendo i dati con cui si e' registrato
				try {
					out.writeObject(g.Login((String) in.readObject(), (String) in.readObject()));
				} catch (IOException | SQLException e) { // eccezioni sollevate
					out.writeObject(e);
				}
				break;
			case 4: // effettuare ricerca tramite comune e tipologia forniti da cittadino lato
					// client
				try {
					out.writeObject(g.RicercaCentroComuneTipologia((String) in.readObject(), (String) in.readObject()));
				} catch (SQLException e) { // eccezioni sollevate
					out.writeObject(e);
				}
				break;
			case 5: // il cittadino sta richiedendo di inserire un evento avverso nel database
				try {

					out.writeObject(g.VisualizzaCentro(true, (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (Integer) in.readObject(),
							(String) in.readObject()));
				} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti
						| CittadinoNonVaccinatoNelCentro e) { // eccezioni sollevate
					out.writeObject(e);
				}
				break;

			}

		} catch (IOException e) {
			System.err.println("Si � verificato un errore.");
		} catch (NumberFormatException e) {
			System.err.println(
					"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
		} catch (ClassNotFoundException e1) {
			System.err.println("Si � verificato un errore.");
			e1.printStackTrace();
		}
	}

	/**
	 * Metodo che interpreta il comando ricevuto dal client lato cittadino e lo traduce in base al protocollo di comunicazione
	 * @param com Variabile che contiene il comando ricevuto dal client
	 * @return Viene ritornato un valore numerico (da 1 a 5) in base alla richiesta oppure -1 in caso il comando non sia
	 * valido
	 */
	private int interpretaCittadino(String com) {
		if (com.equals("VISCENTRO")) // protocollo di comunicazione --> visualizza informazioni centro vaccianale
			return 1;
		if (com.equals("REGCITT")) // protocollo di comunicazione --> registrazione cittadino
			return 2;
		if (com.equals("LOGIN")) // protocollo di comunicazione --> richiesta di accesso
			return 3;
		if (com.equals("RICERCACOMUNETIPOLOGIA")) // protocollo di comunicazione --> ricerca centro tramite comune e
													// tipologia
			return 4;
		if (com.equals("EVENTOAVV")) // protocollo di comunicazione --> visualizza centro + evento avverso
			return 5;
		return -1;
	}

	// qui viene interpretata la richiesta fatta dall'operatore lato client
	/**
	 * Metodo che interpreta il comando ricevuto dal client lato operatore e lo traduce in base al protocollo di comunicazione
	 * @param com Variabile che contiene il comando ricevuto dal client
	 * @return Viene ritornato un valore numerico (1 o 2) in base alla richiesta oppure -1 in caso il comando non sia
	 * valido
	 */
	private int interpretaOperatore(String com) {
		if (com.equals("REGCENTRO")) // protocollo applicativo --> inserimento nuovo centro
			return 1;
		if (com.equals("REGVACC")) // protocollo applicativo --> inserimento nuova vaccinazione
			return 2;
		return -1;
	}

	/**
	 * Metodo che interpreta il comando ricevuto dal client e lo traduce in base al protocollo di comunicazione
	 * @param com Variabile che contiene il comando ricevuto dal client
	 * @return Viene ritornato un valore numerico (1 o 2) in base alla richiesta oppure -1 in caso il comando non sia
	 * valido
	 */
	private int interpretaUser(String com) {
		if (com.equals("OPERATORE"))
			return 1;
		if (com.equals("CITTADINO"))
			return 2;
		return -1;
	}

}
