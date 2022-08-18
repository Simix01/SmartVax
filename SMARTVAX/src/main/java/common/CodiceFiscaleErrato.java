package common;

import javax.management.remote.SubjectDelegationPermission;

public class CodiceFiscaleErrato extends Exception {

	public CodiceFiscaleErrato() {
		super("Codice Fiscale Errato");
	}
}
