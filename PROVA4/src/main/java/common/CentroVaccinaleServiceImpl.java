package common;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.NonReadableChannelException;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;

import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import centrivaccinaliServer.CentroVaccinale;
import centrivaccinaliServer.Vaccinato;
import cittadini.Cittadino;

public class CentroVaccinaleServiceImpl implements CentroVaccinaleService {

	private Connection conn;

	@Override
	public void VisualizzaCentro(boolean access, String codFiscale)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] trovato = null;
		try {
			System.out.println("1 - RICERCA CENTRO TRAMITE NOME ");
			System.out.println("2 - RICERCA CENTRO TRAMITE INDIRIZZO (COMUNE E TIPOLOGIA)");
			System.out.print("-> ");
			int scelta = Integer.parseInt(in.readLine());
			switch (scelta) {
			case 1:
				do {
					trovato = RicercaCentroNome();
					if (trovato == null)
						throw new CentroVaccinaleNonEsistente();
				} while (trovato == null);
				break;
			case 2:
				do {
					trovato = RicercaCentroComuneTipologia();
					if (trovato == null)
						throw new CentriVaccinaliNonEsistenti();
				} while (trovato == null);
				break;
			default:
				System.out.println("SCELTA NON VALIDA!!!");
				VisualizzaCentro(access, "dfsadsdf");
				break;

			}

			System.out.println("\nNome centro -> " + trovato[0]);
			System.out.println("Indirizzo -> " + trovato[1] + " " + trovato[2] + " " + trovato[3] + " " + trovato[4]
					+ " " + trovato[5] + " " + trovato[6]);
			System.out.println("Tipologia -> " + trovato[7]);

			if (access) // controllo che sia avvenuto l'accesso tramite il login
				inserisciEventiAvversi(trovato[0], codFiscale); // funzione per l'inserimento degli eventi avversi (solo
																// tramite login effettuato)
			else
				StatisticheEventiAvversi(trovato[0]); // stampa delle statistiche relative agli eventi avversi

		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
		} catch (NumberFormatException e) {
			System.err.println(
					"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
		} catch (NullPointerException e) {
			System.err.println("Si è verificato un errore.");
		}

	}

	@Override
	public synchronized void registraCentroVaccinale() throws IOException, CentroVaccinaleGiaRegistrato {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.print("Inserisci nome centro vaccinale: ");
			String nome = in.readLine();
			System.out.println(
					"Inserisci indirizzo (via/v.le/piazza, nome, numero civico, comune, sigla provincia, cap) centro vaccinale :");
			System.out.print("Inserisci tipologia via: ");
			String tipoVia = in.readLine();
			System.out.print("Via: ");
			String nomeVia = in.readLine();
			System.out.print("Numero civico: ");
			String numCiv = in.readLine();
			System.out.print("Comune: ");
			String comune = in.readLine();
			System.out.print("Sigla provincia: ");
			String sigProv = in.readLine();
			System.out.print("CAP: ");
			String cap = in.readLine();
			System.out.print("Inserisci tipologia (ospedaliero, aziendale, hub) centro vaccinale: ");
			String tipologia = in.readLine();

			CentroVaccinale centroVaccinale = new CentroVaccinale(nome, tipoVia, nomeVia, numCiv, comune, sigProv, cap,
					tipologia);

			if (!findCentroVac(nome)) {
				String queryInsert = "insert into centrivaccinali (nomecentro,tipoluogo,nomeluogo,numcivico,comune,siglaprovincia,cap,tipologia) "
						+ "values ('" + nome + "','" + tipoVia + "','" + nomeVia + "','" + numCiv + "','" + comune
						+ "','" + sigProv + "'," + cap + ",'" + tipologia + "')";

				PreparedStatement preparedStmt = conn.prepareStatement(queryInsert);
				preparedStmt.executeUpdate();
				conn.close();

				System.out.println(" ");
				System.out.println("Operazione eseguita con successo");

				conn.close();

			} else
				throw new CentroVaccinaleGiaRegistrato();

		} catch (IOException e) {
			System.err.println("Si è verificato un errore");
		} catch (SQLException e) {
			System.err.println("Si è verificato un errore con il database");
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void registraVaccinato() throws IOException, SQLException {

		Statement preparedStmt = conn.createStatement();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String scelta2 = null;
		String codiceFiscale;
		do {
			try {
				System.out.println(" ");
				System.out.print("Inserisci nome: ");
				String nome = in.readLine();
				System.out.print("Inserisci cognome: ");
				String cognome = in.readLine();
				String nomeCentro;

				while (true) {
					System.out.print("Inserisci nome del centro vaccinale: ");
					nomeCentro = in.readLine();
					try {
						if (findCentroVac(nomeCentro))
							break;
						else
							System.err.println("CENTRO VACCINALE NON TROVATO");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				System.out.print("Inserisci codice fiscale: ");
				do {
					codiceFiscale = in.readLine();
					if (codiceFiscale.length() != 16)
						System.out.println("Numero di caratteri del codice fiscale errato, reinserire");
				} while (codiceFiscale.length() != 16);

				String vaccino = null;

				System.out.print("Inserisci vaccino somministrato: 1=AstraZeneca 2=Moderna 3=Pfizer 4=J&J: ");
				int scelta = Integer.parseInt(in.readLine());

				switch (scelta) {
				case 1:
					vaccino = "AstraZeneca";
					break;
				case 2:
					vaccino = "Moderna";
					break;
				case 3:
					vaccino = "Pfizer";
					break;
				case 4:
					vaccino = "J&J";
					break;
				}

				Vaccinato vaccinato = new Vaccinato(nomeCentro, nome, cognome, codiceFiscale, vaccino);

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
							+ "nomeVaccino varchar(20) NOT NULL," + "dataVaccino date NOT NULL,"
							+ "Nome VARCHAR(25) NOT NULL," + "Cognome VARCHAR(40) NOT NULL,"
							+ "codFiscale VARCHAR(16) NOT NULL,"
							+ "nomeCentro varchar(50) REFERENCES CentriVaccinali(nomeCentro))";

					preparedStmt.addBatch(queryCrea);
				}

				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(vaccinato.getData());

				String queryAggiungiVaccinato = "INSERT INTO vaccinati_" + nomeCentro.toLowerCase()
						+ "(idvaccinazione,nomevaccino,datavaccino,nome,cognome,codfiscale,nomecentro)" + " VALUES("
						+ vaccinato.getId() + ",'" + vaccino + "','" + date + "','" + nome + "','" + cognome + "','"
						+ codiceFiscale + "','" + nomeCentro + "')";

				preparedStmt.addBatch(queryAggiungiVaccinato);

				String querySiVaccinaString = "INSERT INTO vaccinazioni VALUES ('" + codiceFiscale + "',"
						+ vaccinato.getId() + ")";

				preparedStmt.addBatch(querySiVaccinaString);

				preparedStmt.executeBatch();

				System.out.println("Vuoi ancora registrare un vaccinato? s/n");
				scelta2 = in.readLine();

			} catch (IOException e) {
				System.err.println("Si è verificato un errore.");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (scelta2.equals("s"));
		conn.close();

	}

	@Override
	public synchronized void registraCittadino()
			throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato {
		try {
			String email, username, cf;
			int id;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Inserisci nome: ");
			String nome = in.readLine();
			System.out.print("Inserisci cognome: ");
			String cognome = in.readLine();
			System.out.print("Inserisci email: ");
			email = in.readLine();
			System.out.print("Inserisci username: ");
			username = in.readLine();
			System.out.print("Inserisci password: ");
			String password = in.readLine();
			System.out.print("Inserisci codice fiscale: ");

			do {
				cf = in.readLine();
				if (cf.length() != 16)
					System.out.println("Numero di caratteri del codice fiscale errato, reinserire...");
			} while (cf.length() != 16);

			System.out.print("Inserisci id univoco vaccinazione: ");
			id = Integer.parseInt(in.readLine());
			if (controlloId(id, cf)) {

				String queryInserimento = "INSERT INTO cittadiniregistrati(nome,cognome,codfiscale,mail,username,password) "
						+ "VALUES ('" + nome + "','" + cognome + "','" + cf + "','" + email + "','" + username + "','"
						+ password + "')";

				PreparedStatement stmt = conn.prepareStatement(queryInserimento);

				stmt.executeUpdate();

				conn.close();
				System.out.println("Registrazione avvenuta con successo!");
			}
			throw new CittadinoNonVaccinato();
		} catch (IOException e) {
			e.printStackTrace();
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

	private String[] RicercaCentroNome() throws SQLException, CentroVaccinaleNonEsistente {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] vettore = new String[8];
		boolean trovato = false;
		ResultSet rs = null;
		try {
			System.out.print("Inserisci nome del centro vaccinale cercato: ");
			String cercato = in.readLine();

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

		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
			e.printStackTrace();
		}

		rs.close();
		if (!trovato)
			return null;
		else
			return vettore;

	}

	private String[] RicercaCentroComuneTipologia() throws SQLException {
		Statement stmt = conn.createStatement();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] vettore;
		LinkedList<String[]> infoCentri = new LinkedList<String[]>();
		ResultSet rs = null;
		int scelta = 0;
		try {
			System.out.print("Inserisci tipologia del centro vaccinale cercato: ");
			String cercatoTipo = in.readLine();
			System.out.print("Inserisci comune centro: ");
			String comune = in.readLine();

			String query = "SELECT * FROM centrivaccinali where comune = '" + comune + "' AND tipologia = '"
					+ cercatoTipo + "'";

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
				int c = 1;
				System.out.println("Seleziona centro");
				for (String[] tmp : infoCentri) {
					System.out.print(c++ + " -> ");
					for (int i = 0; i < tmp.length; i++)
						System.out.print(tmp[i] + " ");
					System.out.println();
				}
				System.out.print("SCELTA: ");
				scelta = Integer.parseInt(in.readLine());
			} else
				return null;
		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
			e.printStackTrace();
		}
		rs.close();
		if (!infoCentri.isEmpty())
			return infoCentri.get(scelta - 1);
		else
			return null;
	}

	private void inserisciEventiAvversi(String nomeCentro, String CF) throws SQLException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("SCEGLI L'EVENTO AVVERSO: ");
		String evento = "", scelta2, scelta3, nota = null;
		int gravita;
		if (verificaVaccinato(nomeCentro, CF)) {
			try {
				do {
					System.out.println("1 - mal di testa");
					System.out.println("2 - febbre");
					System.out.println("3 - dolore muscolari");
					System.out.println("4 - spossatezza");
					System.out.println("5 - dolori intestinali");
					System.out.println("6 - dolori muscolari");
					System.out.println("7 - tosse");
					System.out.println("8 - mal di gola");
					System.out.println("9 - perdita del gusto o dell'olfatto");
					System.out.println("10 - diarrea");
					System.out.println("11 - difficoltà respiratoria");

					System.out.print("-> ");
					int scelta = Integer.parseInt(in.readLine());
					evento = "";
					switch (scelta) {
					case 1:
						evento = "mal di testa";
						break;
					case 2:
						evento = "febbre";
						break;
					case 3:
						evento = "dolore muscolari";
						break;
					case 4:
						evento = "spossatezza";
						break;
					case 5:
						evento = "dolori intestinali";
						break;
					case 6:
						evento = "dolori muscolari";
						break;
					case 7:
						evento = "tosse";
						break;
					case 8:
						evento = "mal di gola";
						break;
					case 9:
						evento = "perdita del gusto e/o olfatto";
						break;
					case 10:
						evento = "diarrea";
						break;
					case 11:
						evento = "difficoltà respiratoria";
						break;
					}

					do {
						System.out.print("Inserisci gravità da 1-5: ");
						gravita = Integer.parseInt(in.readLine());
					} while (gravita < 1 && gravita > 5);

					System.out.println("Vuoi scrivere note opzionali? s/n");
					scelta2 = in.readLine();

					if (scelta2.equals("s")) {
						do {
							System.out.println("(Massimo 255 caratteri)");
							nota = in.readLine();
						} while (nota.length() > 255);
					} else
						nota = null;

					String queryAddEvento = "INSERT INTO eventiavversi(tipoevento,valoregravita,commento,nomecentro) "
							+ "VALUES ('" + evento + "','" + gravita + "','" + nota + "','" + nomeCentro + "')";

					PreparedStatement stmt = conn.prepareStatement(queryAddEvento);

					stmt.executeUpdate();

					System.out.println("Registrazione avvenuta con successo!");

					System.out.println("Vuoi ancora registrare un evento avverso? s/n");
					scelta3 = in.readLine();
				} while (scelta3.equals("s"));

			} catch (NumberFormatException | IOException e) {
				System.err.println(
						"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
			}
		}
		conn.close();
	}

	private void StatisticheEventiAvversi(String nomeCentro) throws SQLException {
		Statement stmt = conn.createStatement();

		String queryStatisticaString = "select avg(valoregravita)as mediagravita, tipoevento, count(*) as numcases  from eventiavversi group by tipoevento";

		ResultSet rs = stmt.executeQuery(queryStatisticaString);

		System.out.println("---------------------------------------------------------------------");

		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				System.out.println("" + rs.getString("tipoevento") + " -->  N° Casi: " + rs.getInt("numcases")
						+ "    Media gravità: " + new DecimalFormat("#.#").format(rs.getDouble("mediagravita")));
			}
		}
		rs.close();
		conn.close();
	}
}
