package cittadini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.login.LoginContext;

import centrivaccinaliServer.Server;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleService;
import common.CentroVaccinaleServiceCittadino;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;
import common.ConnectDB;

public class CentroVaccinaleServiceStubCittadino implements CentroVaccinaleServiceCittadino {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));
	String CF;

	public CentroVaccinaleServiceStubCittadino() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	public void menuCittadino() throws IOException, SQLException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int scelta, scelta2;
		out.writeObject("CITTADINO");
		System.out.println("1 - ACCESSO LIBERO");
		System.out.println("2 - REGISTRAZIONE CITTADINO");
		System.out.println("3 - SEI GIA' REGISTRATO? EFFETTUA L'ACCESSO");
		System.out.print("-> ");

		scelta = Integer.parseInt(ins.readLine());
		switch (scelta) {
		case 1:
			try {
				System.out.println("1 - RICERCA CENTRO TRAMITE NOME ");
				System.out.println("2 - RICERCA CENTRO TRAMITE INDIRIZZO (COMUNE E TIPOLOGIA)");
				System.out.print("-> ");
				scelta2 = Integer.parseInt(in.readLine());
				String cercato = null, cercatoTipo = null, comune = null;
				if (scelta2 == 1) {
					System.out.print("Inserisci nome del centro vaccinale cercato: ");
					cercato = in.readLine();
				} else {
					System.out.print("Inserisci tipologia del centro vaccinale cercato: ");
					cercatoTipo = in.readLine();
					System.out.print("Inserisci comune centro: ");
					comune = in.readLine();
				}

				System.out.println(
						VisualizzaCentro(false, null, scelta2, -1, cercato, comune, cercatoTipo, -1, null, null));
			} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {

			}
			break;
		case 2:
			try {
				System.out.print("Inserisci nome: ");
				String nome = in.readLine();
				System.out.print("Inserisci cognome: ");
				String cognome = in.readLine();
				System.out.print("Inserisci email: ");
				String email = in.readLine();
				System.out.print("Inserisci username: ");
				String username = in.readLine();
				System.out.print("Inserisci password: ");
				String password = in.readLine();
				System.out.print("Inserisci codice fiscale: ");
				String cf;
				do {
					cf = in.readLine();
					if (cf.length() != 16)
						System.out.println("Numero di caratteri del codice fiscale errato, reinserire...");
				} while (cf.length() != 16);

				System.out.print("Inserisci id univoco vaccinazione: ");
				int id = Integer.parseInt(in.readLine());

				registraCittadino(nome, cognome, email, username, password, cf, id);
			} catch (IOException | CittadinoGiaRegistrato | SQLException | CittadinoNonVaccinato e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			menuLogin();
			break;
		}
	}

	@Override
	public String VisualizzaCentro(boolean access, String codFiscale, int scelta, int scelta2, String cercato,
			String comune, String cercatoTipo, int gravita, String nota, String scelta3)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti {

		Object tmp;

		out.writeObject("CITTADINO");
		
		if (!access)
			out.writeObject("VISCENTRO");
		else {
			out.writeObject("EVENTOAVV");
			out.writeObject(CF);
		}

		out.writeObject(scelta);
		out.writeObject(scelta2);
		out.writeObject(cercato);
		out.writeObject(comune);
		out.writeObject(cercatoTipo);
		out.writeObject(gravita);
		out.writeObject(nota);
		out.writeObject(scelta3);

		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CentroVaccinaleNonEsistente) {
			throw (CentroVaccinaleNonEsistente) tmp;
		} else if (tmp instanceof CentriVaccinaliNonEsistenti) {
			throw new CentriVaccinaliNonEsistenti();
		} else if (tmp instanceof String) {
			return (String) tmp;
		} else {
			throw new IOException();
		}

	}

	@Override
	public void registraCittadino(String nome, String cognome, String email, String username, String password,
			String cf, int id) throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato {
		Object tmp;

		out.writeObject("CITTADINO");
		out.writeObject("REGCITT");

		out.writeObject(nome);
		out.writeObject(cognome);
		out.writeObject(email);
		out.writeObject(username);
		out.writeObject(password);
		out.writeObject(cf);
		out.writeObject(id);

		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof CittadinoGiaRegistrato) {
			throw (CittadinoGiaRegistrato) tmp;
		} else if (tmp instanceof CittadinoNonVaccinato) {
			throw new CittadinoNonVaccinato();
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) {
			return;
		} else {
			throw new IOException();
		}
	}

	private void menuLogin() throws IOException, SQLException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		out.writeObject("LOGIN");
		try {
			while (true) {
				System.out.print("Inserisci userId: ");
				String id = in.readLine();
				System.out.print("Inserisci password: ");
				String pw = in.readLine();
				if (Login(id, pw))
					break;
				else
					System.out.println("Accesso negato, reinserire le credenziali");
			}

			System.out.println("Accesso eseguito\n");

			while (true) {
				System.out.println("1 - VISUALIZZA CENTRO");
				System.out.println("2 - INSERIRE EVENTO AVVERSO");
				System.out.print("-> ");
				int scelta = Integer.parseInt(in.readLine());

				System.out.println("1 - RICERCA CENTRO TRAMITE NOME ");
				System.out.println("2 - RICERCA CENTRO TRAMITE INDIRIZZO (COMUNE E TIPOLOGIA)");
				System.out.print("-> ");
				int scelta2 = Integer.parseInt(in.readLine());
				String cercato = null, comune = null, cercatoTipo = null;
				if (scelta2 == 1) {
					System.out.print("Inserisci nome del centro vaccinale cercato: ");
					cercato = in.readLine();
				} else {
					System.out.print("Inserisci tipologia del centro vaccinale cercato: ");
					cercatoTipo = in.readLine();
					System.out.print("Inserisci comune centro: ");
					comune = in.readLine();
				}

				switch (scelta) {
				case 1:
					try {
						System.out.println(VisualizzaCentro(false, null, scelta2, -1, cercato, comune, cercatoTipo, -1,
								null, null));
					} catch (SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // qui non è possibile inserire effetti collaterali per via del passaggio del
						// valore booleano false
					break;
				case 2:
					try {
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
						int scelta3 = Integer.parseInt(in.readLine());
						int gravita;
						do {
							System.out.print("Inserisci gravità da 1-5: ");
							gravita = Integer.parseInt(in.readLine());
						} while (gravita < 1 && gravita > 5);

						System.out.println("Vuoi scrivere note opzionali? s/n");
						String scelta4 = in.readLine();
						String nota;
						if (scelta4.equals("s")) {
							do {
								System.out.println("(Massimo 255 caratteri)");
								nota = in.readLine();
							} while (nota.length() > 255);
						} else
							nota = null;

						System.out.println("Registrazione avvenuta con successo!");

						System.out.println("Vuoi ancora registrare un evento avverso? s/n");
						String scelta5 = in.readLine();

						VisualizzaCentro(true, CF, scelta2, scelta3, cercato, comune, cercatoTipo,
								gravita, nota, scelta5);

					} catch (SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // qui è possibile inserire effetti collaterali per via del passaggio del valore
						// booleano true
					break;
				default:
					System.out.println("SCELTA NON VALIDA!!!");
					menuCittadino();
					break;
				}
			}
		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
		} catch (NumberFormatException e) {
			System.err.println(
					"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
		}
	}

	private boolean Login(String id, String pw) throws SQLException {

		ConnectDB c = new ConnectDB("postgres", "Nhuari062799!");
		Connection connection = c.connect();

		String queryVerificaLogin = "SELECT codfiscale" + " FROM cittadiniregistrati" + " WHERE username='" + id
				+ "' AND password= '" + pw + "'";

		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery(queryVerificaLogin);

		if (rs.isBeforeFirst()) {
			rs.next();
			CF = rs.getString("codFiscale");
			return true;
		} else
			return false;
	}

}
