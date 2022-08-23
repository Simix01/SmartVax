package common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

/**Interfaccia relativa ai servizi a cui può accedere il cittadino
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 */
public interface CentroVaccinaleServiceCittadino {

	/**Metodo che permette di registrate un cittadino
	 * 
	 * @param nome Variabile che contiene il nome del cittadino
	 * @param cognome Variabile che contiene il cognome del cittadino
	 * @param email Variabile che contiene l'email del cittadino
	 * @param username Variabile che contiene l'username che il cittadino vuole inserire
	 * @param password Variabile che contiene la password che il cittadino vuole inserire
	 * @param cf Variabile che contiene il codice fiscale del cittadino
	 * @param id Variabile che contiene l'id vaccinazione del cittadino
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws CittadinoGiaRegistrato Nel caso in cui il cittadino sia già registrato
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CittadinoNonVaccinato Nel caso in cui il cittadino non sia ancora vaccinato
	 * @throws CodiceFiscaleErrato Nel caso in cui il codice fiscale sia errato
	 * @throws UserEmailGiaUsato Nel caso in cui username o email siano già state utilizzate
	 */
	public void registraCittadino(String nome, String cognome, String email, String username, String password,
			String cf, int id) throws IOException, CittadinoGiaRegistrato, SQLException, CittadinoNonVaccinato,CodiceFiscaleErrato,UserEmailGiaUsato;

	/**Metodo per cercare un centro vaccinale ed eventualmente inserire gli eventi avversi
	 * 
	 * @param access Variabile che abilita l'inserimento degli eventi avversi in base al login
	 * @param sceltaEvento Variabile che contiene il tipo di evento avverso
	 * @param cercato Variabile che contine il nome del centro vaccinale da cercare
	 * @param comune Variabile che contiene il nome del comune del centro vaccinale
	 * @param cercatoTipo Variabile che contiene il tipo di centro vaccinale(hub/ospedaliero/aziendale)
	 * @param gravita Variabile che contiene la gravità dell'evento avverso (da 1 a 5)
	 * @param nota Variabile che contiene eventuali note opzionali che il vaccinato può inserire
	 * @return Ritorna le informazioni del centro vaccinale con le eventuali statistiche
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws CentroVaccinaleNonEsistente Nel caso in cui venga cercato un centro vaccinale che non esiste
	 * @throws CentriVaccinaliNonEsistenti Nel caso in cui vengano cercati centri vaccinali che non esistono
	 * @throws CittadinoNonVaccinatoNelCentro Nel caso in cui il cittadino non sia stato registrato nel centro selezionato
	 */
	public String VisualizzaCentro(boolean access, String sceltaEvento, String cercato, String comune,
			String cercatoTipo, int gravita, String nota)
			throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti,CittadinoNonVaccinatoNelCentro;

	/**Metodo che permette di ricercare i centri vaccinali in base a comune e tipologia
	 * 
	 * @param comune Variabile che contiene il comune del centro da ricercare
	 * @param cercatoTipo Variabile che contiene il tipo di centro da ricercare
	 * @return Viene ritornata la lista dei centri in caso di ricerca andata a buon fine, altrimenti null se 
	 * non sono stati trovati centri vaccinali
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 */
	public LinkedList<String[]> RicercaCentroComuneTipologia(String comune, String cercatoTipo)
			throws IOException, SQLException;
	
	/**Metodo che permette di effettuare il login
	 * 
	 * @param id Variabile che contiene l'username
	 * @param pw Variabile che contiene la password
	 * @return Viene ritornato true se il cittadino è gia registrato, altrimenti false
	 * @throws SQLException Nel caso in cui il database restituisca un errore
	 * @throws IOException Nel caso in cui l'utente non inserisce nessun valore
	 */
	public boolean Login(String id, String pw) throws SQLException, IOException;
}
