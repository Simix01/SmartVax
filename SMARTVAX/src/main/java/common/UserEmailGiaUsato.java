package common;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso l'username o l'email siano già state utilizzate per
 * una registrazione
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class UserEmailGiaUsato extends Exception {
	/**
	 * Costruttore dell'eccezione, genera messaggio di errore
	 */
	public UserEmailGiaUsato() {
		super("User/Email gi�' utilizzato");
	}
	

}
