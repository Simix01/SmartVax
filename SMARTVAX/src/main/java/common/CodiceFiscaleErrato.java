package common;

import javax.management.remote.SubjectDelegationPermission;

/**
 * 
 * Classe relativa all'eccezione che viene sollevata in caso il codice fiscale risulti errato
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */

public class CodiceFiscaleErrato extends Exception {

	/**
	 * Costruttore dell'eccezione, genera messaggio d'errore
	 */
	public CodiceFiscaleErrato() {
		super("Codice Fiscale Errato");
	}
}
