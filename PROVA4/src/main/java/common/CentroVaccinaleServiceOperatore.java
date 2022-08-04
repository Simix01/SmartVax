package common;

import java.io.IOException;
import java.sql.SQLException;

import centrivaccinaliServer.CentroVaccinale;
import centrivaccinaliServer.Vaccinato;

public interface CentroVaccinaleServiceOperatore {
	
	
	
	public void registraCentroVaccinale()throws IOException,CentroVaccinaleGiaRegistrato;
	
	public void registraVaccinato() throws IOException,SQLException;
	
	

}
