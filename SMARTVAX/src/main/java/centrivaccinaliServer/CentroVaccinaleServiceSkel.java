package centrivaccinaliServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;

import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleService;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;
import common.CittadinoNonVaccinatoNelCentro;

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

	public void run() {
		while (true) {
			try {
				int user = interpretaUser((String) in.readObject());
				switch (user) {
				case 1:
					MenuOperatore();
					break;

				case 2:
					MenuCittadino();
					break;
				}
			} catch (ClassNotFoundException | IOException | CentroVaccinaleNonEsistente | SQLException
					| CittadinoNonVaccinatoNelCentro e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}

	private void MenuOperatore() throws CentroVaccinaleNonEsistente {
		try {

			int scelta = interpretaOperatore((String) in.readObject());

			switch (scelta) {
			case 1:
				try {
					g.registraCentroVaccinale((String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (int) in.readObject(), (String) in.readObject());
					out.writeObject("OK");
				} catch (CentroVaccinaleGiaRegistrato e) {
					out.writeObject(e);
				}
				break;
			case 2:
				try {
					g.registraVaccinato((String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (Date) in.readObject());
					out.writeObject("OK");
				} catch (CentroVaccinaleNonEsistente e) {
					out.writeObject(e);
					e.printStackTrace();
				} catch (SQLException e) {
					out.writeObject(e);
					e.printStackTrace();
				}
				break;
			default: {
				System.err.println(
						"Nessuna operazione disponibile con il codice da lei inserito, proceda ad un nuovo inserimento");
				MenuOperatore();
			}
			}

		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
		} catch (NumberFormatException e) {
			System.err.println(
					"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
		} catch (ClassNotFoundException e1) {
			System.err.println("Si è verificato un errore.");
			e1.printStackTrace();
		}
	}

	private void MenuCittadino() throws SQLException, CittadinoNonVaccinatoNelCentro {
		try {
			int scelta = interpretaCittadino((String) in.readObject());

			switch (scelta) {
			case 1:
				try {

					out.writeObject(g.VisualizzaCentro(false, (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (Integer) in.readObject(),
							(String) in.readObject()));
				} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti
						| CittadinoNonVaccinatoNelCentro e) {
					out.writeObject(e);
				}
				break;
			case 2:
				try {
					g.registraCittadino((String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (String) in.readObject(),
							(Integer) in.readObject());
					out.writeObject("OK");
				} catch (IOException | CittadinoGiaRegistrato | SQLException | CittadinoNonVaccinato e) {
					out.writeObject(e);
				}
				break;
			case 3:
				try {
					out.writeObject(g.Login((String) in.readObject(), (String) in.readObject()));
				} catch (IOException | SQLException e) {
					out.writeObject(e);
				}
				break;
			case 4:
				try {
					out.writeObject(g.RicercaCentroComuneTipologia((String) in.readObject(), (String) in.readObject()));
				} catch (SQLException e) {
					out.writeObject(e);
				}
				break;
			case 5:
				try {

					out.writeObject(g.VisualizzaCentro(true, (String) in.readObject(), (String) in.readObject(),
							(String) in.readObject(), (String) in.readObject(), (Integer) in.readObject(),
							(String) in.readObject()));
				} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti
						| CittadinoNonVaccinatoNelCentro e) {
					out.writeObject(e);
				}
				break;

			}

		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
		} catch (NumberFormatException e) {
			System.err.println(
					"HAI INSERITO UN CARATTERE E NON UN NUMERO/HAI SCHIACCIATO INVIO SENZA SCEGLIERE UN NUMERO!!!");
		} catch (ClassNotFoundException e1) {
			System.err.println("Si è verificato un errore.");
			e1.printStackTrace();
		}
	}

	private int interpretaCittadino(String com) {
		if (com.equals("VISCENTRO"))
			return 1;
		if (com.equals("REGCITT"))
			return 2;
		if (com.equals("LOGIN"))
			return 3;
		if (com.equals("RICERCACOMUNETIPOLOGIA"))
			return 4;
		if (com.equals("EVENTOAVV"))
			return 5;
		return -1;
	}

	private int interpretaOperatore(String com) {
		if (com.equals("REGCENTRO"))
			return 1;
		if (com.equals("REGVACC"))
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
