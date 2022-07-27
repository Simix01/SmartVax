import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.sql.Connection;


public class PROVA4 {
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	    System.out.println("---- Main Class1 -----");
	    if(args.length > 0)
	      Arrays.asList(args).forEach(System.out::println);
	    else
	      System.out.println("No arguments recieved!");
	    
	    ConnectDB c = new ConnectDB(args[0],args[1]);
	    
	    Connection conn=c.connect();
	    
	    String query = "insert into persons values (4,'ciao','ciao','ciao','ciao')";

	    try {
	    	
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    
	}
	
}
