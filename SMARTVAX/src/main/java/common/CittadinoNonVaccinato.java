package common;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso il cittadino in questione non sia vaccinato
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CittadinoNonVaccinato extends Exception{
	
	/**
	 * Costruttore dell'eccezione, genera messaggio
	 */
	public CittadinoNonVaccinato() {
		super("Cittadino non ancora vaccinato/Id vaccinazione errato");
	}
	
}
