package centrivaccinaliServer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Vaccinato {
	String nomeCentro;
	String nome;
	String cognome;
	String codFiscale;
	String data;
	String vaccino;
	int idVaccinazione;
	
	public Vaccinato(String nomeCentro,String nome,String cognome,String codFiscale,String vaccino) {
		this.nome=nome;
		this.cognome=cognome;
		this.nomeCentro=nomeCentro;
		this.codFiscale=codFiscale;
		this.vaccino=vaccino;
		
		Date now = new Date();										//utilizzo SimpleDateFormat per accedere alla data
		SimpleDateFormat dateForm=new SimpleDateFormat("dd/MM/y");  //attuale sul dispositivo al momento dell'esecuzione
		data=dateForm.format(now);
	}
	
	public String getData()
	{
		return data;
	}
	
}
