package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import centrivaccinaliServer.CentroVaccinale;
import centrivaccinaliServer.Vaccinato;
import cittadini.Cittadino;

public class CentroVaccinaleServiceImpl implements CentroVaccinaleService {

	private Connection conn;

	@Override
	public CentroVaccinale VisualizzaCentro() throws IOException, SQLException, CentroVaccinaleNonEsistente {

		return null;
	}

	@Override
	public void registraCentroVaccinale() throws IOException, CentroVaccinaleGiaRegistrato {

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
			System.err.println("Si � verificato un errore");
		} catch (SQLException e) {
			System.err.println("Si � verificato un errore con il database");
			e.printStackTrace();
		}
	}

	@Override
	public void registraVaccinato() throws IOException, SQLException {

		Statement preparedStmt = conn.createStatement();
		ResultSet rSet;
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

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

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

					String queryConstraint = "ALTER TABLE Vaccinati_" + nomeCentro
							+ " ADD CONSTRAINT cf_unique2 UNIQUE(codFiscale);";

					preparedStmt.addBatch(queryConstraint);
				}

				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(vaccinato.getData());

				String queryAggiungiVaccinato = "INSERT INTO vaccinati_" + nomeCentro.toLowerCase()
						+ "(idvaccinazione,nomevaccino,datavaccino,nome,cognome,codfiscale,nomecentro)" + " VALUES("
						+ vaccinato.getId() + ",'" + vaccino + "','" + date + "','" + nome + "','" + cognome + "','"
						+ codiceFiscale + "','" + nomeCentro + "')";

				preparedStmt.addBatch(queryAggiungiVaccinato);

				String querySiVaccinaString = "INSERT INTO si_vaccina VALUES ('"+codiceFiscale+"',"+vaccinato.getId()+",'"+nomeCentro+"')";

				preparedStmt.addBatch(querySiVaccinaString);
				
				preparedStmt.executeBatch();

				System.out.println("Vuoi ancora registrare un vaccinato? s/n");
				scelta2 = in.readLine();

			} catch (IOException e) {
				System.err.println("Si � verificato un errore.");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (scelta2.equals("s"));
		conn.close();

	}

	@Override
	public void registraCittadino() throws IOException, CittadinoGiaRegistrato {
		// TODO Auto-generated method stub

	}

	public CentroVaccinaleServiceImpl(Connection conn) {

		this.conn = conn;
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
}
