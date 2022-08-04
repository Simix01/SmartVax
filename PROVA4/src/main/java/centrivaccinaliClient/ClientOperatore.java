package centrivaccinaliClient;

public class ClientOperatore {
	
	public static void main(String[] args) {
		try {
			
			CentroVaccinaleServiceStubOperatore c = new CentroVaccinaleServiceStubOperatore();
			c.menuOperatori();
			
		}catch(Exception e) {
			
		}
	}
}
