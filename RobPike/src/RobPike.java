public class RobPike
{
	public static void main( String[] args )
	{
		BankAccount bankAccount = new BankAccount();
		SimpleThread t1 = new SimpleThread(bankAccount, Operation.DEPOSIT);
		SimpleThread t2 = new SimpleThread(bankAccount, Operation.WITHDRAW);
		t1.start();
		t2.start();
		try 
		{
			t1.join();
			t2.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		long money = bankAccount.getMoney();
		System.out.println(money);
	}
}
