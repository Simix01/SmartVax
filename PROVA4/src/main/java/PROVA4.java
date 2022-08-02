import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import common.CentroVaccinaleGiaRegistrato;
import common.CentroVaccinaleServiceImpl;

import java.sql.Connection;

public class PROVA4 {

	public static void main(String[] args) throws IOException, CentroVaccinaleGiaRegistrato, SQLException {
		// TODO Auto-generated method stub

		System.out.println("---- Main Class1 -----");
		if (args.length > 0)
			Arrays.asList(args).forEach(System.out::println);
		else
			System.out.println("No arguments recieved!");

		ConnectDB c = new ConnectDB("postgres", "Nhuari062799!");

		Connection conn = c.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ResultSet rs;


		String queryControllo = "select exists"
				+ "(select * from information_schema.tables\r\n"
				+ "where table_schema = 'public' AND table_name = 'si_vaccin') as value";
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		rs = stmt.executeQuery(queryControllo);

		rs.next();
		System.out.print(rs.getBoolean("value"));

		//CentroVaccinaleServiceImpl prova=new CentroVaccinaleServiceImpl(conn);
		//prova.registraCentroVaccinale();
		
		

	}

}
