package common;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import centrivaccinaliServer.CentroVaccinale;
import centrivaccinaliServer.Vaccinato;

/**Interfaccia relativa alle operazione che un operatore può effettuare, cioè registrare un nuovo centro vaccinale o registrare
 * un vaccinato.
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public interface CentroVaccinaleServiceOperatore {

	/**Metodo che permette di registrare un nuovo centro vaccinale
	 * 
	 * @param nome Variabile che contiene il nome del centro vaccinale
	 * @param tipoVia Variabile che contiene il tipo di via in cui si trova il centro vaccinale
	 * @param nomeVia Variabile che contiene il nome della via in cui si trova il centro vaccinale
	 * @param numCiv Variabile che contiene il numero civico della via in cui si trova il centro vaccinale
	 * @param comune Variabile che contiene il nome del comune in cui si trova il centro vaccinale
	 * @param sigProv Variabile che contiene la sigla della provincia in cui si trova il centro vaccinale
	 * @param cap Variabile che contiene il cap in cui si trova il centro vaccinale
	 * @param tipologia Variabile che contiene la tipologia del centro vaccinale
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws CentroVaccinaleGiaRegistrato Nel caso in cui il centro vaccinale sia già registrato
	 * @throws CapErrato Nel caso in cui il cap inserito sia errato
	 */
	public void registraCentroVaccinale(String nome, String tipoVia, String nomeVia, String numCiv, String comune,
			String sigProv, int cap, String tipologia) throws IOException, CentroVaccinaleGiaRegistrato,CapErrato;

	/**Metodo che permette di registrare un cittadino vaccinato
	 * 
	 * @param nome Variabile che contiene il nome del vaccinato
	 * @param cognome Variabile che contiene il cognome del vaccinato
	 * @param nomeCentro Variabile che contiene il nome del centro vaccinale
	 * @param codFiscale Variabile che contiene il codice fiscale del vaccinato
	 * @param vaccino Variabile che contiene il vaccino con il quale è stato effettuata la vaccinazione
	 * @param date Variabile che contiene la data in cui è stata effettuata la vaccinazione
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CentroVaccinaleNonEsistente Nel caso in cui venga cercato un centro vaccinale che non esiste
	 * @throws CodiceFiscaleErrato Nel caso in cui il codice fiscale inserito sia errato
	 */
	public void registraVaccinato(String nome, String cognome, String nomeCentro, String codFiscale, String vaccino,
			Date date) throws IOException, SQLException,CentroVaccinaleNonEsistente,CodiceFiscaleErrato;

}
