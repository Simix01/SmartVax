package common;

import java.io.IOException;
import java.sql.SQLException;

import centrivaccinaliServer.CentroVaccinale;
import centrivaccinaliServer.Vaccinato;
import cittadini.Cittadino;

public interface CentroVaccinaleService {
	
	public void VisualizzaCentro(boolean access,String codFiscale) throws IOException,SQLException,CentroVaccinaleNonEsistente,CentriVaccinaliNonEsistenti;
	
	public void registraCentroVaccinale()throws IOException,CentroVaccinaleGiaRegistrato;
	
	public void registraVaccinato() throws IOException,SQLException;
	
	public void registraCittadino() throws IOException,CittadinoGiaRegistrato,SQLException,CittadinoNonVaccinato;

}
