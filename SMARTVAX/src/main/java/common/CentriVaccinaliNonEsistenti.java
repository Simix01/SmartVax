package common;

/**
 * Classe relativa all'eccezione che viene sollevata in caso i centri vaccinali in questione non esistano
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CentriVaccinaliNonEsistenti extends Exception{
	/**
	 * Costruttore della classe, genera messaggio d'errore
	 */
	public CentriVaccinaliNonEsistenti(){
		super("Centri vaccinali non esistenti");
	}
}