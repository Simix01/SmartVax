package centrivaccinaliServer;

import java.util.ArrayList;

public class CentroVaccinale {
	
	String nome;
	String tipoVia;
	String viaNome;
	String numCiv;
	String comune;
	String sigProv;
	String cap;
	String tipologia;
	
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
