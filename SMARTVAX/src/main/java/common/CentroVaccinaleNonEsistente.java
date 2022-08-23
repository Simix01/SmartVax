package common;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso il centro vaccinale non esista nel database
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CentroVaccinaleNonEsistente extends Exception{
	
	/**
	 * Costruttore della classe, genera messaggio d'errore
	 */
	public CentroVaccinaleNonEsistente() {
		super("Centro vaccinale non esistente");
	}

}
