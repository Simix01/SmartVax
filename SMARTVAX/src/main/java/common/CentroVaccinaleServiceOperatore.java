package common;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import centrivaccinaliServer.CentroVaccinale;
import centrivaccinaliServer.Vaccinato;

public interface CentroVaccinaleServiceOperatore {

	public void registraCentroVaccinale(String nome, String tipoVia, String nomeVia, String numCiv, String comune,
			String sigProv, int cap, String tipologia) throws IOException, CentroVaccinaleGiaRegistrato;

	public void registraVaccinato(String nome, String cognome, String nomeCentro, String codFiscale, String vaccino,
			Date date) throws IOException, SQLException,CentroVaccinaleNonEsistente;

}