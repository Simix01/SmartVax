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

public class CentroVaccinaleServiceImpl implements CentroVaccinaleService {

	private Connection conn; //connessione 
	private static String CF;

	//viene effettuata la query per vedere se i dati inseriti dall'utente che richiede di accedere siano corretti
	@Override
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

	//funzione per cercare centro tramite comune e tipologia
	@Override
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

	//funzione per visualizzare i dati del centro vaccinale
	@Override
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
				info += StatisticheEventiAvversi(); //ritorna valori + statistiche ricavate dalla funzione

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return info;
	}

	//funzione per registrare centro vaccinale
	@Override
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

	//funzione per registrare centro vaccinale
	@Override
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

	//funzione per registrare un cittadino
	@Override
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

	public CentroVaccinaleServiceImpl(Connection conn) { //costruisce oggetto centrovaccinaleserviceimpl fornendo dati della connessione

		this.conn = conn;
	}

	//funzione per controllare che l'id inserito dall'utente in fase di registrazione corrisponda all'id assegnato in fase di vaccinazione
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
	private String StatisticheEventiAvversi() throws SQLException {
		Statement stmt = conn.createStatement();

		String queryStatisticaString = "select avg(valoregravita)as mediagravita, tipoevento, count(*) as numcases  from eventiavversi group by tipoevento";

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
