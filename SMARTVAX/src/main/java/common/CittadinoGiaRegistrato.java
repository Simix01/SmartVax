package common;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso il cittadino risulti già registrato
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CittadinoGiaRegistrato extends Exception{
	
	/**
	 * Costruttore dell'eccezione, genera messaggio
	 */
	public CittadinoGiaRegistrato() {
		super("Il cittadino risulta gia registrato");
	}

}
