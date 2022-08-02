package common;

import java.io.IOException;
import java.sql.SQLException;

import centrivaccinaliServer.CentroVaccinale;
import centrivaccinaliServer.Vaccinato;
import cittadini.Cittadino;

public interface CentroVaccinaleService {
	
	public CentroVaccinale VisualizzaCentro() throws IOException,SQLException,CentroVaccinaleNonEsistente;
	
	public void registraCentroVaccinale()throws IOException,CentroVaccinaleGiaRegistrato;
	
	public void registraVaccinato() throws IOException;
	
	public void registraCittadino() throws IOException,CittadinoGiaRegistrato;

}
