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

public class Vaccinato {
	String nomeCentro;
	String nome;
	String cognome;
	String codFiscale;
	String data;
	String vaccino;
	int idVaccinazione;
	
	static short instanceCounter=1;
	
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
	
	public String getData()
	{
		return data;
	}
	
	public int getId()
	{
		return idVaccinazione;
	}
	
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
			System.err.println("Si è verificato un errore.");
		}
	}
	
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
			System.out.println("Si è verificato un errore.");
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("Si è verificato un errore.");
		}
		return val;
	}
	
	public boolean isFileEmpty()
	{
		File file = new File(getPath()+"Counter.txt");
		return file.length()==0;
	}
	
	public String getPath()
	{
		File currentDir = new File("");
		//System.out.println(currentDir.getAbsolutePath());
		String path = currentDir.getAbsolutePath();
		
		//Path pathOneFolderBack = Paths.get(path,"../"); //Serve quando vogliamo eseguire dal command prompt**
		
		return path+"/data/";
	}
	
}
