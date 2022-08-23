package common;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso il cittadino non risulti vaccinato nel centro specificato
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CittadinoNonVaccinatoNelCentro extends Exception {
	
	/**
	 * Costruttore dell'eccezione, genera messaggio
	 */
	public CittadinoNonVaccinatoNelCentro(){
		super("Cittadino non vaccinato in questo centrovaccinale");
	}

}
