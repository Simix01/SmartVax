package cittadini;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import common.CentriVaccinaliNonEsistenti;
import common.CentroVaccinaleNonEsistente;

public class ClientCittadino {

	public static void main(String[] args) throws IOException, SQLException, CentroVaccinaleNonEsistente, CentriVaccinaliNonEsistenti {
		LinkedList<String[]> centriLinkedList;
		CentroVaccinaleServiceStubCittadino c = new CentroVaccinaleServiceStubCittadino();
c.Login("joshua", "joshua");
c.VisualizzaCentro(false, "Febbre", "Como", null, null, 5, " ");

System.out.print("CIAOOOOOOOOOO");

	}
}
