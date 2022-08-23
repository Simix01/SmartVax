package common;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso il cittadino non sia registrato
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CittadinoNonRegistrato extends Exception{
	
	/**
	 * Costruttore dell'eccezione, genera messaggio
	 */
	public CittadinoNonRegistrato() {
		super("Cittadino non registrato, procedere alla registrazione");
	}

}
