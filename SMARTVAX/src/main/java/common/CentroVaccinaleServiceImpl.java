package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CodingErrorAction;
import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import centrivaccinaliServer.Vaccinato;

/**Classe che fornisce l'implementazione dei vari metodi utili al cittadino e all'operatore.
 * L'operatore può registrare un nuovo centro vaccinale oppure un nuovo vaccinato.
 * Il cittadino può registrarsi, effettuare il login, ricercare un centro vaccinale per nome o comune e tipologia
 * e vedere le statistiche dei relativi eventi avversi.
 * 
 * <p>
 * <code>
 * conn Variabile globale relativa ad un oggetto di tipo Connection 
 * utile a stabilire la connessione con il database
 * CF Variabile globale che permette di salvare il codice fiscale del cittadino
 * </code>
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 */
public class CentroVaccinaleServiceImpl implements CentroVaccinaleService {

	private Connection conn; //connessione 
	private static String CF;

	@Override
	//viene effettuata la query per vedere se i dati inseriti dall'utente che richiede di accedere siano corretti
	/**
	 * Metodo che permette di effettuare il login controllando se esiste nel db una corrispondenza con gli username e
	 * password inseriti dall'utente. In caso positivo viene salvato il cofice fiscale.
	 * 
	 * @param id Variabile che contiene l'username
	 * @param pw Variabile che contiene la password
	 * @param queryVerificaLogin Stringa in cui è contenuta la query per verificare che il cittadino sia registrato.
	 * @param stmt Oggetto di tipo Statement
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @return Viene ritornato true se il cittadino è gia registrato, altrimenti false
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * 
	 */
	
	public synchronized boolean Login(String id, String pw) throws SQLException {
		String queryVerificaLogin = "SELECT codfiscale" + " FROM cittadiniregistrati" + " WHERE username='" + id
				+ "' AND password= '" + pw + "'"; //stringa che contiene la query

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 

		ResultSet rs = stmt.executeQuery(queryVerificaLogin); //viene eseguita la query

		if (rs.isBeforeFirst()) { //controlla se il result set fornito dalla query e' vuoto
			rs.next();
			CF = rs.getString("codFiscale"); //prelevo il valore del codice fiscale e lo salvo in una variabile globale
			return true;
		} else
			return false;
	}

	@Override
	//funzione per cercare centro tramite comune e tipologia
	/**
	 * Metodo che permette di ricercare un centro vaccinale tramite il comune e la tipologia.
	 * Se viene trovata una corrispondenza viene tornata una linked list contenente i vari centri vaccinali.
	 * 
	 * @param comune Variabile che contiene il comune del centro da ricercare
	 * @param cercatoTipo Variabile che contiene il tipo di centro da ricercare
	 * @param stmt Oggetto di tipo Statement
	 * @param vettore Array di tipo stringa in cui vengono salvati i valori relativi al comune e alla tipologia
	 * @param infoCentri Lista che contiene i centri vaccinali
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @param query Stringa che contiene la query per la ricerca dei centri nel database
	 * @return Viene ritornata la lista dei centri in caso di ricerca andata a buon fine, altrimenti null se 
	 * non sono stati trovati centri vaccinali
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 *
	 */
	
	public synchronized LinkedList<String[]> RicercaCentroComuneTipologia(String comune, String cercatoTipo)
			throws SQLException {
		Statement stmt = conn.createStatement();
		String[] vettore; //salvo i valori presi dalla GUI in un vettore
		LinkedList<String[]> infoCentri = new LinkedList<String[]>(); //salvo le informazioni dei centri in una linkedlist
		ResultSet rs = null;
		String query = "SELECT * FROM centrivaccinali where comune = '" + comune + "' AND tipologia = '" + cercatoTipo
				+ "'";

		rs = stmt.executeQuery(query); //esegue la query

		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				vettore = new String[8];
				vettore[0] = rs.getString("nomecentro");
				vettore[1] = rs.getString("tipoluogo");
				vettore[2] = rs.getString("nomeluogo");
				vettore[3] = rs.getString("numcivico");
				vettore[4] = rs.getString("comune");
				vettore[5] = rs.getString("siglaprovincia");
				vettore[6] = String.valueOf(rs.getInt("cap"));
				vettore[7] = rs.getString("tipologia");

				infoCentri.add(vettore);
			}
		} else
			return null;
		rs.close();
		if (!infoCentri.isEmpty()) //se non e' vuota allora ritorna la lista
			return infoCentri;
		else
			return null;
	}

	@Override
	//funzione per visualizzare i dati del centro vaccinale
	/**
	 * Metodo utile a cercare un centro vaccinale e a visualizzare le statistiche relative agli eventi avversi 
	 * dei vaccinati in tale centro. Inolte se l'utente ha effettuato il login ha la possibilità di inserire
	 * un evento avverso. 
	 * 
	 * @param access Variabile che abilita l'inserimento degli eventi avversi in base al login
	 * @param sceltaEvento Variabile che contiene il tipo di evento avverso
	 * @param cercato Variabile che contine il nome del centro vaccinale da cercare
	 * @param comune Variabile che contiene il nome del comune del centro vaccinale
	 * @param cercatoTipo Variabile che contiene il tipo di centro vaccinale(hub/ospedaliero/aziendale)
	 * @param gravita Variabile che contiene la gravità dell'evento avverso (da 1 a 5)
	 * @param nota Variabile che contiene eventuali note opzionali che il vaccinato può inserire
	 * @param trovato Array utilizzato per salvare le informazioni del centro trovato
	 * @param info Variabile utilizzata per salavre le informazioni relative al centro vaccinale
	 * @return Ritorna le informazioni del centro vaccinale con le eventuali statistiche
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CentroVaccinaleNonEsistente Nel caso in cui venga cercato un centro vaccinale che non esiste
	 * @throws CentriVaccinaliNonEsistenti 
	 * @throws CittadinoNonVaccinatoNelCentro Nel caso in cui il cittadino non sia stato registrato nel centro selezionato
	 */
	
	public synchronized String VisualizzaCentro(boolean access, String sceltaEvento, String cercato, String comune,
			String cercatoTipo, int gravita, String nota) throws IOException, SQLException, CentroVaccinaleNonEsistente,
			CentriVaccinaliNonEsistenti, CittadinoNonVaccinatoNelCentro {
		String[] trovato = null; //salvo le informazioni del centro trovato dentro un vettore di stringhe
		String info = "";

		try {

			do {
				trovato = RicercaCentroNome(cercato); //eseguo la ricerca
				if (trovato == null)
					throw new CentroVaccinaleNonEsistente();
			} while (trovato == null);

			info += "\nNome centro -> " + trovato[0] + "\n" + "Indirizzo -> " + trovato[1] + " " + trovato[2] + " "
					+ trovato[3] + " " + trovato[4] + " " + trovato[5] + " " + trovato[6] + "\n" + "Tipologia -> "
					+ trovato[7]; //salvo le informazioni dentro una stringa

			if (access) //Se l'utente ha effettuato l'accesso puo' inserire un evento avverso
				inserisciEventiAvversi(trovato[0], sceltaEvento, gravita, nota);

			else
				info += StatisticheEventiAvversi(cercato); //ritorna valori + statistiche ricavate dalla funzione

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return info;
	}

	@Override
	//funzione per registrare centro vaccinale
	/**
	 * Metodo utile a registrare un nuovo centro vaccinale. In primo luogo viene verificato che il centro vaccinale
	 * che si vuole inserire non sia già registrato. Se il controllo va a buon fine si procede con l'inserimento.
	 * 
	 * @param nome Variabile che contiene il nome del centro vaccinale
	 * @param tipoVia Variabile che contiene il tipo di via in cui si trova il centro vaccinale
	 * @param nomeVia Variabile che contiene il nome della via in cui si trova il centro vaccinale
	 * @param numCiv Variabile che contiene il numero civico della via in cui si trova il centro vaccinale
	 * @param comune Variabile che contiene il nome del comune in cui si trova il centro vaccinale
	 * @param sigprov Variabile che contiene la sigla della provincia in cui si trova il centro vaccinale
	 * @param cap Variabile che contiene il cap in cui si trova il centro vaccinale
	 * @param tipologia Variabile che contiene la tipologia del centro vaccinale
	 * @param queryInsert Variabile che contiene la query necessaria ad inserire il nuovo centro vaccinale del database
	 * @param preparedStmt Variabile che identifica l'oggetto di tipo PreparedStatement
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws CentroVaccinaleGiaRegistrato Nel caso in cui il centro vaccinale sia già registrato
	 * @throws CapErrato Nel caso in cui il cap inserito sia errato
	 */
	
	public synchronized void registraCentroVaccinale(String nome, String tipoVia, String nomeVia, String numCiv,
			String comune, String sigProv, int cap, String tipologia)
			throws IOException, CentroVaccinaleGiaRegistrato, CapErrato {

		try {

			if (!findCentroVac(nome)) { //se non e' presente il centro vaccinale procede con la registrazione

				if (String.valueOf(cap).length() == 5) { //controllo sul valore del cap, deve essere di 5 caratteri

					String queryInsert = "insert into centrivaccinali (nomecentro,tipoluogo,nomeluogo,numcivico,comune,siglaprovincia,cap,tipologia) "
							+ "values ('" + nome + "','" + tipoVia + "','" + nomeVia + "','" + numCiv + "','" + comune
							+ "','" + sigProv + "'," + cap + ",'" + tipologia + "')";

					PreparedStatement preparedStmt = conn.prepareStatement(queryInsert);
					preparedStmt.executeUpdate();
				} else
					throw new CapErrato();

			} else
				throw new CentroVaccinaleGiaRegistrato();

		} catch (SQLException e) {
			System.err.println("Si è verificato un errore con il database");
			e.printStackTrace();
		}
	}
	
	@Override
	//funzione per registrare un nuovo vaccinato
	/**
	 * Metodo che permette di registrare un vaccinato. Vengono effettuati vari controllo: si verifica che il centro vaccinale
	 * inserito esista, si effettua un controllo sulla lunghezza del codice fiscale, si verifica se la tabella del database
	 * relativa al centro esista e in caso negativo la si crea. A questo punto viene salvato il vaccinato e registrata la
	 * correlazione tra id e codice fiscale.
	 * 
	 * @param nome Variabile che contiene il nome del vaccinato
	 * @param cognome Variabile che contiene il cognome del vaccinato
	 * @param nomeCentro Variabile che contiene il nome del centro vaccinale
	 * @param codFiscale Variabile che contiene il codice fiscale del vaccinato
	 * @param vaccino Variabile che contiene il vaccino con il quale è stato effettuata la vaccinazione
	 * @param date Variabile che contiene la data in cui è stata effettuata la vaccinazione
	 * @param preparedStmt Oggetto di tipo PreparedStatement
	 * @param vaccinato Variabile che identifica un oggetto di tipo Vaccinato
	 * @param queryString Variabile che contiene la query per verificare che la tabella relativa al centro vaccinale esista
	 * @param stmt Oggetto di tipo Statement
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @param exist Variabile che contiene true in caso la tabella relativa al centro vaccinale esista
	 * @param queryCrea Variabile che contiene la query utile a creare la tabella relativa al centro vaccinale
	 * @param queryAggiungiVaccinato Variabile che contiene la query utile ad aggiungere un vaccinato nel database
	 * @param querySiVaccinaString Variabile che contiene la query utile a collegare l'id della vaccinazione al 
	 * codice fiscale nel database
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CentroVaccinaleNonEsistente Nel caso in cui venga cercato un centro vaccinale che non esiste
	 * @throws CodiceFiscaleErrato Nel caso in cui il codice fiscale inserito sia errato
	 */
	
	public synchronized void registraVaccinato(String nome, String cognome, String nomeCentro, String codFiscale,
			String vaccino, Date date)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CodiceFiscaleErrato {

		Statement preparedStmt = conn.createStatement();

		if (!findCentroVac(nomeCentro)) //se il centro inserito non e' presente nel database viene sollevata l'eccezione
			throw new CentroVaccinaleNonEsistente();

		if (codFiscale.length() != 16) //controllo sull'inserimento del codice fiscale, deve essere di 16 caratteri
			throw new CodiceFiscaleErrato();

		Vaccinato vaccinato = new Vaccinato(nomeCentro, nome, cognome, codFiscale, vaccino); //creo oggetto vaccinato con i dati inseriti

		String queryString = "select exists" + "(select * from information_schema.tables\r\n"
				+ "where table_schema = 'public' AND table_name = 'vaccinati_" + nomeCentro.toLowerCase()
				+ "') as value"; //query per verificare se la tabella vaccinati_nomecentrovaccinale e' gia' presente o meno

		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery(queryString);

		rs.next();
		boolean exist = rs.getBoolean("value");
		rs.close();
		if (!exist) { //se non esiste viene creata

			String queryCrea = "CREATE TABLE Vaccinati_" + nomeCentro + " (" + "idVaccinazione int PRIMARY KEY,"
					+ "nomeVaccino varchar(20) NOT NULL," + "dataVaccino date NOT NULL," + "Nome VARCHAR(25) NOT NULL,"
					+ "Cognome VARCHAR(40) NOT NULL," + "codFiscale VARCHAR(16) NOT NULL,"
					+ "nomeCentro varchar(50) REFERENCES CentriVaccinali(nomeCentro))";

			preparedStmt.addBatch(queryCrea);
		}

		String queryAggiungiVaccinato = "INSERT INTO vaccinati_" + nomeCentro.toLowerCase()
				+ "(idvaccinazione,nomevaccino,datavaccino,nome,cognome,codfiscale,nomecentro)" + " VALUES("
				+ vaccinato.getId() + ",'" + vaccino + "','" + date + "','" + nome + "','" + cognome + "','"
				+ codFiscale + "','" + nomeCentro + "')"; //query per aggiungere nuovo vaccinato nel centro giusto

		preparedStmt.addBatch(queryAggiungiVaccinato);

		String querySiVaccinaString = "INSERT INTO vaccinazioni VALUES ('" + codFiscale + "'," + vaccinato.getId()
				+ ")"; //query per registrare correlazione fra codice fiscale e id vaccinazione

		preparedStmt.addBatch(querySiVaccinaString);

		preparedStmt.executeBatch();

	}

	@Override
	//funzione per registrare un cittadino
	/**
	 * Metodo che permette al cittadino di registrarsi. Viene controllato in primo luogo la correttezza del codice fiscale, viene
	 * controllata la corrispondenza tra codice fiscale e id vaccinazione. Infine si procede alla registrazione del cittadino.
	 * 
	 * @param nome Variabile che contiene il nome del cittadino
	 * @param cognome Variabile che contiene il cognome del cittadino
	 * @param email Variabile che contiene l'email del cittadino
	 * @param username Variabile che contiene l'username che il cittadino vuole inserire
	 * @param password Variabile che contiene la password che il cittadino vuole inserire
	 * @param cf Variabile che contiene il codice fiscale del cittadino
	 * @param id Variabile che contiene l'id vaccinazione del cittadino
	 * @param queryInserimento Variabile che contiene la query che permette di registrare il cittadino nel database
	 * @param stmt Oggetto di tipo Statement
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws CittadinoGiaregistrato Nel caso in cui il cittadino sia già registrato
	 * @throws SLQException Nel caso in cui il database restituisca un errore
	 * @throws CittadinoNonVaccinato Nel caso in cui il cittadino non sia ancora vaccinato
	 * @throws CodiceFiscaleErrato Nel caso in cui il codice fiscale sia errato
	 * @throws UserEmailGiausato Nel caso in cui username o email siano già state utilizzate
	 */
	
	public synchronized void registraCittadino(String nome, String cognome, String email, String username,
			String password, String cf, int id)
			throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato, CodiceFiscaleErrato, UserEmailGiaUsato {
		try {

			if (cf.length() == 16) { //controllo su lunghezza codice fiscale

				if (controlloId(id, cf)) { //controllo su id e codice fiscale inserito

					String queryInserimento = "INSERT INTO cittadiniregistrati(nome,cognome,codfiscale,mail,username,password) "
							+ "VALUES ('" + nome + "','" + cognome + "','" + cf + "','" + email + "','" + username
							+ "','" + password + "')";

					PreparedStatement stmt = conn.prepareStatement(queryInserimento);

					stmt.executeUpdate();

					System.out.println("Registrazione avvenuta con successo!");
				} else
					throw new CittadinoNonVaccinato();
			} else
				throw new CodiceFiscaleErrato();
		} catch (SQLException e) {
			throw new UserEmailGiaUsato(); 
		}
	}
	
	/**
	 * Metodo che costruisce l'oggetto CentroVaccinaleServiceImpl fornendo dati della connessione
	 * @param conn Oggetto di tipo Connection per la connessione
	 */

	public CentroVaccinaleServiceImpl(Connection conn) { //costruisce oggetto centrovaccinaleserviceimpl fornendo dati della connessione

		this.conn = conn;
	}

	//funzione per controllare che l'id inserito dall'utente in fase di registrazione corrisponda all'id assegnato in fase di vaccinazione
	/**
	 * Metodo utile a controllare che l'id inserito corrisponda a quello assegnato durante la vaccinazione
	 * @param id Variabile che contiene l'id della vaccinazione
	 * @param cf Variabile che contiene il codice fiscale del vaccinato
	 * @param queryControlloId Variabile che contiene la query utile a controllare la corrispondenza sul database
	 * @param stmt Oggetto di tipo Statement
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @return Viene ritornato true se la cottispondenza è verificata, altrimenti viene ritornato false
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 */
	private boolean controlloId(int id, String cf) throws SQLException {

		String queryControlloId = "select idvaccinazione" + " from vaccinazioni " + " where codfiscale='" + cf + "'";

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery(queryControlloId);

		while (rs.next()) {
			if (id == rs.getInt("idvaccinazione")) {
				rs.close();
				return true;
			}
		}
		rs.close();
		return false;
	}

	//funzione per verificare che il centro vaccinale fornito come parametro sia presente nel database
	/**
	 * Metodo utile a verificare che il centro vaccinale esista
	 * @param nomeCentro Variabile che contiene il nome del centro
	 * @param queryControllo Variabile che contiene la query utile a controllare la corrispondenza sul database
	 * @param stmt Oggetto di tipo Statement
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @return Viene tornato true se il centro vaccinale esiste, altrimenti false
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 */
	private boolean findCentroVac(String nomeCentro) throws SQLException {

		String queryControllo = "select nomecentro from centrivaccinali where nomecentro ='" + nomeCentro + "'";

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery(queryControllo);

		if (!rs.isBeforeFirst()) {
			rs.close();
			return false;
		} else {
			rs.close();
			return true;
		}
	}

	//funzione per verificare che un utente si sia effettivamente vaccinato in un dato centro vaccinale, usata per permettergli di inserire eventi avversi
	/**
	 * Metodo utile a verificare che il cittadino si sia vaccinato nel centro vaccinale che dichiara
	 * @param nomeCentro Variabile che contiene il nome del centro vaccinale
	 * @param CF Variabile che contiene il codice fiscale del vaccinato
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @param queryVerifica Variabile che contiene la query utile a verificare che l'utente sia effettivamente vaccinato
	 * in quel determinato centro vaccinale
	 * @param stmt Oggetto di tipo Statement
	 * @return Viene ritornato ture se la verifica va a buon fine, altrimenti false
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 */
	private boolean verificaVaccinato(String nomeCentro, String CF) throws SQLException {

		ResultSet rs = null;

		String queryVerifica = "SELECT * FROM vaccinati_" + nomeCentro.toLowerCase() + " WHERE codFiscale ='" + CF
				+ "'";

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		rs = stmt.executeQuery(queryVerifica);

		if (rs.isBeforeFirst()) {
			rs.close();
			return true;
		} else {
			rs.close();
			return false;
		}

	}

	//funzione per cercare un centro tramite nome e prelevarne le informazioni
	/**
	 * Metodo utile a cercare un centro vaccinale tramite il nome, inoltre vengono prelevate le informazioni corrispondenti
	 * @param cercato Variabile che contiene il nome del centro vaccinale
	 * @param vettore Array che contiene le informazioni del centro
	 * @param trovato Variabile che contiene true nel caso in cui il centro vaccinale sia stato trovato, altrimenti false
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @param queryRicerca Variabile che contiene la query che consente di ricercare il centro vaccinale nel database
	 * @param stmt Oggetto di tipo Statement
	 * @return Viene ritornato il vettore con le informazioni relative al centro, oppure null nel caso il centro non esista
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CentroVaccinaleNonEsistente Nel caso il centro vaccinale non esista
	 */
	private String[] RicercaCentroNome(String cercato) throws SQLException, CentroVaccinaleNonEsistente {
		new BufferedReader(new InputStreamReader(System.in));
		String[] vettore = new String[8]; //vettore usato per salvare informazioni del centro
		boolean trovato = false;
		ResultSet rs = null;
		String queryRicerca = "select *" + " from centrivaccinali" + " where nomecentro='" + cercato + "'";

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery(queryRicerca);

		if (rs.isBeforeFirst()) {

			rs.next();
			vettore[0] = rs.getString("nomecentro");
			vettore[1] = rs.getString("tipoluogo");
			vettore[2] = rs.getString("nomeluogo");
			vettore[3] = rs.getString("numcivico");
			vettore[4] = rs.getString("comune");
			vettore[5] = rs.getString("siglaprovincia");
			vettore[6] = String.valueOf(rs.getInt("cap"));
			vettore[7] = rs.getString("tipologia");
			trovato = true;
		} else {
			trovato = false;
		}

		rs.close();
		if (!trovato)
			return null;
		else
			return vettore;

	}
	//funzione per inserire eventi avversi
	/**Metodo utile ad inserire gli eventi avversi. Viene verificato in primo luogo che il cittadino sia stato vaccinato
	 * nello specifico centro che dichiara. In caso positivo si procede alla registrazione dell'evento avverso nel database
	 * 
	 * @param nomeCentro Variabile che contiene il nome del centro vaccinale
	 * @param sceltaEvento Variabile che contiene il tipo di evento avversi
	 * @param gravita Variabile che contiene la gravità dell'evento avverso (da 1 a 5)
	 * @param nota Variabile che contiene le eventuali note opzionali riguardo l'evento avverso
	 * @param queryAddEvento Variabile che contiene la query utile a inserire l'evento avverso nel databse
	 * @param stmt Oggetto di tipo Statement
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CittadinoNonVaccinatoNelCentro Nel caso in cui il cittadino non sia stato vaccinato nel centro
	 * specificato
	 */
	private void inserisciEventiAvversi(String nomeCentro, String sceltaEvento, int gravita, String nota)
			throws SQLException, CittadinoNonVaccinatoNelCentro {

		if (verificaVaccinato(nomeCentro, CF)) {

			String queryAddEvento = "INSERT INTO eventiavversi(tipoevento,valoregravita,commento,nomecentro) "
					+ "VALUES ('" + sceltaEvento + "','" + gravita + "','" + nota + "','" + nomeCentro + "')";

			PreparedStatement stmt = conn.prepareStatement(queryAddEvento);

			stmt.executeUpdate();

		} else {
			throw new CittadinoNonVaccinatoNelCentro();
		}
	}

	//funzione per calcolare e fornire dati statistici relativi agli eventi avversi registrati in un dato centro vaccinale
	/**
	 * Metodo utile a calcolare le statistiche degli eventi avversi relative al centro
	 * @param nomeCentro Variabile che contiene il nome del centro vaccinale
	 * @param stmt Oggetto di tipo Statement
	 * @param queryStatisticaString Variabile che contiene la query per prelevare dal database gli eventi avversi relativi
	 * ad un centro vaccinale
	 * @param rs Oggetto che contiene il result set con il risultato della query
	 * @param info Variabile che contiene le statistiche del centro vaccinale
	 * @return Vengono ritornate le statistiche relative al centro, oppure null in caso non siano stati registrati eventi
	 * avversi
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 */
	private String StatisticheEventiAvversi(String nomeCentro) throws SQLException {
		Statement stmt = conn.createStatement();

		String queryStatisticaString = "select avg(valoregravita)as mediagravita, tipoevento, count(*) as numcases  from eventiavversi where nomecentro = '"+nomeCentro+"'  group by tipoevento";

		ResultSet rs = stmt.executeQuery(queryStatisticaString);

		String info = "\n";
		info += "---------------------------------------------------------------------\n";

		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				info += "" + rs.getString("tipoevento") + " -->  N° Casi: " + rs.getInt("numcases")
						+ "    Media gravità: " + new DecimalFormat("#.#").format(rs.getDouble("mediagravita")) + "\n";
			}
		}
		rs.close();
		return info;
	}

}
