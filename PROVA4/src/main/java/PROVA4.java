import java.util.Arrays;

public class PROVA4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	    System.out.println("---- Main Class1 -----");
	    if(args.length > 0)
	      Arrays.asList(args).forEach(System.out::println);
	    else
	      System.out.println("No arguments recieved!");
	}
}
