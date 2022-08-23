package centrivaccinaliServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**Questa classe consente di creare l'oggetto collegato al singolo vaccinato.
 * E' inoltre possibile ricavare la data e l'id della vaccinazione.
 * La classe viene inoltre utilizzata per salvare gli id delle vaccinazioni in modo
 * incrementale attraverso un counter salvato su un file.
 * 
 * <p>
 * <code>
 * nomeCentro Variabile globale utile a salvare il nome del centro in cui il cittadino è stato vaccinato
 * nome Variabile globale utile a salvare il nome del vaccinato
 * cognome Variabile globale utile a salvare il cognome del vaccinato
 * codFiscale Variabile globale utile a salvare il codice fiscale del vaccinato
 * data Variabile globale utile a salvare la data di vaccinazione
 * vaccino Variabile globale utile a salvare il tipo di vaccinazione effettuato
 * </code>
 * 
 * @author Mirko Pomata
 * @author Joshua Perez
 * @author Simone Bernaschina
 * @author Elena Perkoska
 *
 */
public class Vaccinato {
	String nomeCentro;
	String nome;
	String cognome;
	String codFiscale;
	String data;
	String vaccino;
	int idVaccinazione;
	
	static short instanceCounter=1;
	
	/**
	 * Costruttore della classe
	 * 
	 * @param nomeCentro Variabile globale utile a salvare il nome del centro in cui il cittadino è stato vaccinato
	 * @param nome Variabile globale utile a salvare il nome del vaccinato
	 * @param cognome Variabile globale utile a salvare il cognome del vaccinato
	 * @param codFiscale Variabile globale utile a salvare il codice fiscale del vaccinato
	 * @param vaccino Variabile globale utile a salvare il tipo di vaccinazione effettuato
	 */
	public Vaccinato(String nomeCentro,String nome,String cognome,String codFiscale,String vaccino) {
		this.nome=nome;
		this.cognome=cognome;
		this.nomeCentro=nomeCentro;
		this.codFiscale=codFiscale;
		this.vaccino=vaccino;
		
		if(instanceCounter==1)
		{
			if(!isFileEmpty())
				instanceCounter=getCounter();
		}
		
		idVaccinazione=instanceCounter++;
		
		
		Date now = new Date();										//utilizzo SimpleDateFormat per accedere alla data
		SimpleDateFormat dateForm=new SimpleDateFormat("dd/MM/y");  //attuale sul dispositivo al momento dell'esecuzione
		data=dateForm.format(now);
		
		saveCounter(); 
	}
	
	/**
	 * Funzione che permette di ritornare la data di vaccinazione
	 * @return Viene ritornata la data di vaccinazione
	 */
	public String getData()
	{
		return data;
	}
	
	/**
	 * Funzione che permette di ritornare l'id della vaccinazione
	 * @return Viene ritornato l'id della vaccinazione
	 */
	public int getId()
	{
		return idVaccinazione;
	}
	
	/**
	 * Funzione che salva l'id delle vaccinazione in modo incrementale su un file
	 */
	public void saveCounter() 
	{
		try
		{
			FileOutputStream out = new FileOutputStream(getPath()+"Counter.txt");
			ObjectOutputStream s = new ObjectOutputStream(out);
			s.writeObject(instanceCounter);
		}
		catch(IOException e)
		{
			System.err.println("Si � verificato un errore.");
		}
	}
	
	/**
	 * Funzione che permette di ritornare il counter relativo all'ultimo vaccinato
	 * @return Viene ritornato il valore del counter nel caso sia salvato sul file
	 */
	public short getCounter()
	{
		short val = 0;
		try
		{
			FileInputStream in = new FileInputStream(getPath()+"Counter.txt");
			ObjectInputStream s = new ObjectInputStream(in);
			val = (Short) s.readObject(); 
		}
		catch(IOException e)
		{
			System.out.println("Si � verificato un errore.");
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("Si � verificato un errore.");
		}
		return val;
	}
	
	/**
	 * Funzione utile a determinare se il file è vuoto, oppure no
	 * @return Viene ritornato true se il file è vuoto, altrimenti false
	 */
	
	public boolean isFileEmpty()
	{
		File file = new File(getPath()+"Counter.txt");
		return file.length()==0;
	}
	
	/**
	 * Funzione utile a ritornare il percorso in cui si trova il file per il salvataggio del counter
	 * @return Viene ritornato il percorso del file.
	 */
	public String getPath()
	{
		File currentDir = new File("");
		//System.out.println(currentDir.getAbsolutePath());
		String path = currentDir.getAbsolutePath();
		
		//Path pathOneFolderBack = Paths.get(path,"../"); //Serve quando vogliamo eseguire dal command prompt**
		
		return path+"/data/";
	}
	
}
