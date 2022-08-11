package common;

import java.io.IOException;
import java.sql.SQLException;

public interface CentroVaccinaleServiceCittadino {

	public void registraCittadino(String nome, String cognome, String email, String username, String password,
			String cf, int id) throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato;

	public String VisualizzaCentro(boolean access, String codFiscale, int scelta, int scelta2, String cercato,
			String comune, String cercatoTipo, int gravita,String nota, String scelta3)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti;

}
