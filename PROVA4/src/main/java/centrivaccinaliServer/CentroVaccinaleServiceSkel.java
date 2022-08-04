package centrivaccinaliServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleService;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;

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
				default:
					System.err.println("Error");
					break;
				}

			} catch (ClassNotFoundException e) {
				System.err.println("Class not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Error");
				e.printStackTrace();
			}
		}
	}

	private void MenuOperatore() {
		try {

			int scelta = interpretaOperatore((String) in.readObject());

			switch (scelta) {
			case 1:
				try {
					g.registraCentroVaccinale();
					out.writeObject("OK");
				} catch (CentroVaccinaleGiaRegistrato e) {
					out.writeObject(e);
				}
				break;
			case 2:
				try {
					g.registraVaccinato();
					out.writeObject("OK");
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

	private void MenuCittadino() {
		try {
			int scelta = interpretaCittadino((String) (in).readObject());

			switch (scelta) {
			case 1:
				try {
					g.VisualizzaCentro(false, null);
					out.writeObject("OK");
				} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {
					out.writeObject(e);
				}
				break;
			case 2:
				try {
					g.registraCittadino();
					out.writeObject("OK");
				} catch (IOException| CittadinoGiaRegistrato | SQLException | CittadinoNonVaccinato e) {
					out.writeObject(e);
				}
				break;
			case 3:
				AreaCittadino();
				break;
			default:
				System.out.println("SCELTA NON VALIDA!!!");
				MenuCittadino();
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
	
	private void AreaCittadino() {
		try {
			int scelta = interpretaCittadinoRegistrato((String) in.readObject());
			switch (scelta) {
			case 1:
				try {
					g.VisualizzaCentro(false, null);
					out.writeObject("OK");
				} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {
					out.writeObject(e);
				}
				break;
			case 2:
				try {
					g.VisualizzaCentro(true, (String)in.readObject());
					out.writeObject("OK");
				} catch (IOException | SQLException | CentroVaccinaleNonEsistente | CentriVaccinaliNonEsistenti e) {
					out.writeObject(e);
				}
				break;
			default:
				System.out.println("SCELTA NON VALIDA!!!");
				MenuCittadino();
				break;
			}
			
		} catch (ClassNotFoundException e) {
			System.err.println("Si è verificato un errore.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Si è verificato un errore.");
			e.printStackTrace();
		}
	}
	
	
	
	private int interpretaCittadinoRegistrato(String com) {
		if (com.equals("VISCENTRO"))
			return 1;
		if (com.equals("EVENTOAVV"))
			return 1;
		return -1;
	}

	private int interpretaCittadino(String com) {
		if (com.equals("VISCENTRO"))
			return 1;
		if (com.equals("REGCITT"))
			return 1;
		if (com.equals("LOGIN"))
			return 2;
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
