package common;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso il centro vaccinale in questione sia già registrato
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public class CentroVaccinaleGiaRegistrato extends Exception{
	
	/**
	 * Costruttore della classe, genera messaggio d'errore
	 */
	public CentroVaccinaleGiaRegistrato() {
		super("Centro vaccinale gia registrato");
	}

}
