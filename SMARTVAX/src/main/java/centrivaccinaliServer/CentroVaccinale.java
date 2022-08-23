package centrivaccinaliServer;

import java.util.ArrayList;

/**Questa classe consente di creare l'oggetto relativo al singolo centro vaccinale
 * 
 * <p>
 * <code>
 * nome Variabile globale utile a salvare il nome del centro vaccinale
 * tipoVia Variabile globale utile a salvare il tipo di via (via/viale/piazza)
 * viaNome Variabile globale utile a salvare il nome della via
 * numCiv Variabile globale utile a salvare il numero civico del centro vaccinale
 * comune Variabile globale utile a salvare il comune in cui si trova il centro vaccinale
 * sigProv Variabile globale utile a salvare la sigla della provincia del centro vaccinale
 * cap Variabile globale utile a salvare il cap del comune in cui si trova il centro vaccinale
 * tipologia Variabile globale utile a salvare la tipologia del centro vaccinale (ospedaliero/hub/aziendale)
 * </code>
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 */

public class CentroVaccinale {
	
	String nome;
	String tipoVia;
	String viaNome;
	String numCiv;
	String comune;
	String sigProv;
	String cap;
	String tipologia;
	
	/**
	 * Costruttore della classe
	 * 
	 * @param nome Variabile globale utile a salvare il nome del centro vaccinale
	 * @param tipoVia Variabile globale utile a salvare il tipo di via (via/viale/piazza)
	 * @param viaNome Variabile globale utile a salvare il nome della via
	 * @param numCiv Variabile globale utile a salvare il numero civico del centro vaccinale
	 * @param comune Variabile globale utile a salvare il comune in cui si trova il centro vaccinale
	 * @param sigProv Variabile globale utile a salvare la sigla della provincia del centro vaccinale
	 * @param cap Variabile globale utile a salvare il cap del comune in cui si trova il centro vaccinale
	 * @param tipologia Variabile globale utile a salvare la tipologia del centro vaccinale (ospedaliero/hub/aziendale)
	 */
	public CentroVaccinale(String nome,String tipoVia,String viaNome,String numCiv,String comune,
			String sigProv,String cap,String tipologia) {
		
		this.nome=nome;
		this.tipoVia=tipoVia;
		this.viaNome=viaNome;
		this.numCiv=numCiv;
		this.comune=comune;
		this.sigProv=sigProv;
		this.cap=cap;
		this.tipologia=tipologia;
	}
	
}
