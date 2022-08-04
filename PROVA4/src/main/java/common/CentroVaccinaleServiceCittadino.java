package common;

import java.io.IOException;
import java.sql.SQLException;

public interface CentroVaccinaleServiceCittadino {
	
	public void registraCittadino() throws IOException,CittadinoGiaRegistrato,SQLException,CittadinoNonVaccinato;
	
	public void VisualizzaCentro(boolean access,String codFiscale) throws IOException,SQLException,CentroVaccinaleNonEsistente,CentriVaccinaliNonEsistenti;
	

}
