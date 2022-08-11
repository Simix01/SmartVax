import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleNonEsistente;
import common.CentroVaccinaleServiceImpl;
import common.CittadinoGiaRegistrato;
import common.CittadinoNonVaccinato;
import common.ConnectDB;

import java.sql.Connection;

public class PROVA4 {

	public static void main(String[] args) throws IOException, CentroVaccinaleGiaRegistrato, SQLException, CittadinoGiaRegistrato, CittadinoNonVaccinato, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti {
		// TODO Auto-generated method stub

		System.out.println("---- Main Class1 -----");
		if (args.length > 0)
			Arrays.asList(args).forEach(System.out::println);
		else
			System.out.println("No arguments recieved!");

		ConnectDB c = new ConnectDB("postgres", "Nhuari062799!");

		Connection conn = c.connect();

		CentroVaccinaleServiceImpl prova = new CentroVaccinaleServiceImpl(conn);
		//prova.registraCentroVaccinale();

		//prova.registraVaccinato();
		
		//prova.registraCittadino();
		
		//prova.VisualizzaCentro(false,"1234567890123456");

	}

}
