package common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public interface CentroVaccinaleServiceCittadino {

	public void registraCittadino(String nome, String cognome, String email, String username, String password,
			String cf, int id) throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato;

	public String VisualizzaCentro(boolean access, String sceltaEvento, String cercato, String comune,
			String cercatoTipo, int gravita, String nota)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti;

	public LinkedList<String[]> RicercaCentroComuneTipologia(String comune, String cercatoTipo)
			throws IOException, SQLException;

	public boolean Login(String id, String pw) throws SQLException, IOException;
}
