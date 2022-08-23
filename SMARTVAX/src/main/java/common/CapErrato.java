package common;

/**
 * Classe relativa all'eccezione che viene sollevata in caso venga inserito un CAP errato
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CapErrato extends Exception {
	
	/**
	 * Costruttore della classe, genera messaggio d'errore
	 */
	public CapErrato() {
		super("CAP errato");
	}
}
