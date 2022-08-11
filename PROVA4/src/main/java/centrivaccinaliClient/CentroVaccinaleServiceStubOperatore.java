package centrivaccinaliClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

import centrivaccinaliServer.Server;
import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleService;
import common.CentroVaccinaleServiceOperatore;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;

public class CentroVaccinaleServiceStubOperatore implements CentroVaccinaleServiceOperatore {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	BufferedReader ins = new BufferedReader(new InputStreamReader(System.in));

	public CentroVaccinaleServiceStubOperatore() throws IOException {
		s = new Socket(InetAddress.getLocalHost(), Server.PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	public void menuOperatori() throws NumberFormatException, IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String scelta2;

		while (true) {
			out.writeObject("OPERATORE");
			System.out.println("1 - REGISTRAZIONE CENTRO VACCINALE");
			System.out.println("2 - REGISTRAZIONE VACCINATO");
			System.out.print("-> ");
			int scelta = Integer.parseInt(ins.readLine());

			switch (scelta) {
			case 1:
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
					registraCentroVaccinale(nome, tipoVia, nomeVia, numCiv, comune, sigProv, cap, tipologia);
				} catch (IOException | CentroVaccinaleGiaRegistrato e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					System.out.println(" ");
					System.out.print("Inserisci nome: ");
					String nome = in.readLine();
					System.out.print("Inserisci cognome: ");
					String cognome = in.readLine();
					System.out.print("Inserisci nome del centro vaccinale: ");
					String nomeCentro = in.readLine();
					String codFiscale;
					do {
						System.out.print("Inserisci codice fiscale: ");
						codFiscale = in.readLine();
						if (codFiscale.length() != 16)
							System.out.println("Numero di caratteri del codice fiscale errato, reinserire");
					} while (codFiscale.length() != 16);

					String vaccino = null;

					System.out.print("Inserisci vaccino somministrato: 1=AstraZeneca 2=Moderna 3=Pfizer 4=J&J: ");
					int scelta1 = Integer.parseInt(in.readLine());

					switch (scelta1) {
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

					registraVaccinato(nome, cognome, nomeCentro, codFiscale, vaccino);
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				System.err.println("ERRORE");
				break;
			}
			System.out.println("Vuoi uscire? s/n");
			scelta2 = ins.readLine();
			if (scelta2.equals("s"))
				break;
		}
	}

	@Override
	public void registraCentroVaccinale(String nome, String tipoVia, String nomeVia, String numCiv, String comune,
			String sigProv, String cap, String tipologia) throws IOException, CentroVaccinaleGiaRegistrato {
		Object tmp;
		out.writeObject("REGCENTRO");
		out.writeObject(nome);
		out.writeObject(tipoVia);
		out.writeObject(nomeVia);
		out.writeObject(numCiv);
		out.writeObject(comune);
		out.writeObject(sigProv);
		out.writeObject(cap);
		out.writeObject(tipologia);

		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof CentroVaccinaleGiaRegistrato) {
			throw (CentroVaccinaleGiaRegistrato) tmp;
		} else if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) {
			return;
		} else {
			throw new IOException();
		}
	}

	@Override
	public void registraVaccinato(String nome, String cognome, String nomeCentro, String codFiscale, String vaccino)
			throws IOException, SQLException {
		Object tmp;
		out.writeObject("REGVACC");
		out.writeObject(nome);
		out.writeObject(cognome);
		out.writeObject(nomeCentro);
		out.writeObject(codFiscale);
		out.writeObject(vaccino);
		try {
			tmp = in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}

		if (tmp instanceof SQLException) {
			throw (SQLException) tmp;
		} else if (tmp instanceof IOException) {
			throw (IOException) tmp;
		} else if (tmp instanceof String && ((String) tmp).equals("OK")) {
			return;
		} else {
			throw new IOException();
		}

	}
}
