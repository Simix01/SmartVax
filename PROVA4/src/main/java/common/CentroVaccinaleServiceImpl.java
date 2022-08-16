package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	private Connection conn;
	private static String CF;

	@Override
	public synchronized boolean Login(String id, String pw) throws SQLException {
		String queryVerificaLogin = "SELECT codfiscale" + " FROM cittadiniregistrati" + " WHERE username='" + id
				+ "' AND password= '" + pw + "'";

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery(queryVerificaLogin);

		if (rs.isBeforeFirst()) {
			rs.next();
			CF = rs.getString("codFiscale");
			return true;
		} else
			return false;
	}

	@Override
	public synchronized LinkedList<String[]> RicercaCentroComuneTipologia(String comune, String cercatoTipo)
			throws SQLException {
		Statement stmt = conn.createStatement();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] vettore;
		LinkedList<String[]> infoCentri = new LinkedList<String[]>();
		ResultSet rs = null;
		String query = "SELECT * FROM centrivaccinali where comune = '" + comune + "' AND tipologia = '"
				+ cercatoTipo.toLowerCase() + "'";

		rs = stmt.executeQuery(query);

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
		if (!infoCentri.isEmpty())
			return infoCentri;
		else
			return null;
	}

	@Override
	public synchronized String VisualizzaCentro(boolean access, String sceltaEvento, String cercato, String comune,
			String cercatoTipo, int gravita, String nota)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti {
		String[] trovato = null;
		String info = "";

		try {

			do {
				trovato = RicercaCentroNome(cercato);
				if (trovato == null)
					throw new CentroVaccinaleNonEsistente();
			} while (trovato == null);

			info += "\nNome centro -> " + trovato[0] + "\n" + "Indirizzo -> " + trovato[1] + " " + trovato[2] + " "
					+ trovato[3] + " " + trovato[4] + " " + trovato[5] + " " + trovato[6] + "\n" + "Tipologia -> "
					+ trovato[7];

			if (access) // controllo che sia avvenuto l'accesso tramite il login
				inserisciEventiAvversi(trovato[0], sceltaEvento, gravita, nota); // funzione per
			// l'inserimento
			// degli eventi
			// avversi (solo
			// tramite login effettuato)
			else
				info += StatisticheEventiAvversi(); // stampa delle statistiche relative agli eventi avversi

		} catch (NumberFormatException e) {
			System.err.println(
					"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
		} catch (NullPointerException e) {
			System.err.println("Si è verificato un errore.");
		}

		return info;
	}

	@Override
	public synchronized void registraCentroVaccinale(String nome, String tipoVia, String nomeVia, String numCiv,
			String comune, String sigProv, int cap, String tipologia) throws IOException, CentroVaccinaleGiaRegistrato {

		try {

			if (!findCentroVac(nome)) {
				String queryInsert = "insert into centrivaccinali (nomecentro,tipoluogo,nomeluogo,numcivico,comune,siglaprovincia,cap,tipologia) "
						+ "values ('" + nome + "','" + tipoVia + "','" + nomeVia + "','" + numCiv + "','" + comune
						+ "','" + sigProv + "'," + cap + ",'" + tipologia + "')";

				PreparedStatement preparedStmt = conn.prepareStatement(queryInsert);
				preparedStmt.executeUpdate();

			} else
				throw new CentroVaccinaleGiaRegistrato();

		} catch (SQLException e) {
			System.err.println("Si è verificato un errore con il database");
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void registraVaccinato(String nome, String cognome, String nomeCentro, String codFiscale,
			String vaccino, Date date) throws IOException, SQLException, CentroVaccinaleNonEsistente {

		Statement preparedStmt = conn.createStatement();
		new BufferedReader(new InputStreamReader(System.in));

		if (!findCentroVac(nomeCentro))
			throw new CentroVaccinaleNonEsistente();

		Vaccinato vaccinato = new Vaccinato(nomeCentro, nome, cognome, codFiscale, vaccino);

		String queryString = "select exists" + "(select * from information_schema.tables\r\n"
				+ "where table_schema = 'public' AND table_name = 'vaccinati_" + nomeCentro.toLowerCase()
				+ "') as value";

		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery(queryString);

		rs.next();
		boolean exist = rs.getBoolean("value");
		rs.close();
		if (!exist) {

			String queryCrea = "CREATE TABLE Vaccinati_" + nomeCentro + " (" + "idVaccinazione int PRIMARY KEY,"
					+ "nomeVaccino varchar(20) NOT NULL," + "dataVaccino date NOT NULL," + "Nome VARCHAR(25) NOT NULL,"
					+ "Cognome VARCHAR(40) NOT NULL," + "codFiscale VARCHAR(16) NOT NULL,"
					+ "nomeCentro varchar(50) REFERENCES CentriVaccinali(nomeCentro))";

			preparedStmt.addBatch(queryCrea);
		}

		// Date date = new SimpleDateFormat("dd/MM/yyyy").parse(vaccinato.getData());

		String queryAggiungiVaccinato = "INSERT INTO vaccinati_" + nomeCentro.toLowerCase()
				+ "(idvaccinazione,nomevaccino,datavaccino,nome,cognome,codfiscale,nomecentro)" + " VALUES("
				+ vaccinato.getId() + ",'" + vaccino + "','" + date + "','" + nome + "','" + cognome + "','"
				+ codFiscale + "','" + nomeCentro + "')";

		preparedStmt.addBatch(queryAggiungiVaccinato);

		String querySiVaccinaString = "INSERT INTO vaccinazioni VALUES ('" + codFiscale + "'," + vaccinato.getId()
				+ ")";

		preparedStmt.addBatch(querySiVaccinaString);

		preparedStmt.executeBatch();

	}

	@Override
	public synchronized void registraCittadino(String nome, String cognome, String email, String username,
			String password, String cf, int id)
			throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato {
		try {

			if (controlloId(id, cf)) {

				String queryInserimento = "INSERT INTO cittadiniregistrati(nome,cognome,codfiscale,mail,username,password) "
						+ "VALUES ('" + nome + "','" + cognome + "','" + cf + "','" + email + "','" + username + "','"
						+ password + "')";

				PreparedStatement stmt = conn.prepareStatement(queryInserimento);

				stmt.executeUpdate();

				System.out.println("Registrazione avvenuta con successo!");
			} else
				throw new CittadinoNonVaccinato();
		} catch (SQLException e) {
			System.err.print("Username/Email già usato oppure " + new CittadinoGiaRegistrato());
		}
	}

	public CentroVaccinaleServiceImpl(Connection conn) {

		this.conn = conn;
	}

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

	private String[] RicercaCentroNome(String cercato) throws SQLException, CentroVaccinaleNonEsistente {
		new BufferedReader(new InputStreamReader(System.in));
		String[] vettore = new String[8];
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

	private void inserisciEventiAvversi(String nomeCentro, String sceltaEvento, int gravita, String nota)
			throws SQLException {

		if (verificaVaccinato(nomeCentro, CF)) {

			String queryAddEvento = "INSERT INTO eventiavversi(tipoevento,valoregravita,commento,nomecentro) "
					+ "VALUES ('" + sceltaEvento + "','" + gravita + "','" + nota + "','" + nomeCentro + "')";

			PreparedStatement stmt = conn.prepareStatement(queryAddEvento);

			stmt.executeUpdate();

		}
	}

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
