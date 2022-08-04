package cittadini;

import java.io.IOException;
import java.sql.SQLException;

public class ClientCittadino {

	public static void main(String[] args) throws IOException, SQLException {
		CentroVaccinaleServiceStubCittadino c = new CentroVaccinaleServiceStubCittadino();
		c.menuCittadino();

	}

}
