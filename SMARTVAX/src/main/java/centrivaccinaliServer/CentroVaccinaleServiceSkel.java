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

public class CentroVaccinaleServiceSkel extends Thread {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private CentroVaccinaleService g;

	public CentroVaccinaleServiceSkel(Socket s, CentroVaccinaleService c) throws NullPointerException, IOException {
		if (s == null || c == null)
			throw new NullPointerException();
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
		g = c;
	}

	// skeleton eredita la classe thread quindi esegue fino a quando non viene
	// killato il processo
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

	private int interpretaCittadino(String com) {
		if (com.equals("VISCENTRO")) // protocollo applicativo --> visualizza informazioni centro vaccianale
			return 1;
		if (com.equals("REGCITT")) // protocollo applicativo --> registrazione cittadino
			return 2;
		if (com.equals("LOGIN")) // protocollo applicativo --> richiesta di accesso
			return 3;
		if (com.equals("RICERCACOMUNETIPOLOGIA")) // protocollo applicativo --> ricerca centro tramite comune e
													// tipologia
			return 4;
		if (com.equals("EVENTOAVV")) // protocollo applicativo --> visualizza centro + evento avverso
			return 5;
		return -1;
	}

	// qui viene interpretata la richiesta fatta dall'operatore lato client
	private int interpretaOperatore(String com) {
		if (com.equals("REGCENTRO")) // protocollo applicativo --> inserimento nuovo centro
			return 1;
		if (com.equals("REGVACC")) // protocollo applicativo --> inserimento nuova vaccinazione
			return 2;
		return -1;
	}

	private int interpretaUser(String com) {
		if (com.equals("OPERATORE"))
			return 1;
		if (com.equals("CITTADINO"))
			return 2;
		return -1;
	}

}
