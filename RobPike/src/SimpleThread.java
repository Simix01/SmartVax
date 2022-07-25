public class SimpleThread extends Thread 
{
	static final long iterations = 10_000;
	private BankAccount bankAccount;
	private Operation operation;
	
	public SimpleThread(BankAccount bankAccount,Operation operation) 
	{
		this.bankAccount = bankAccount;
		this.operation = operation;
	}
	
	public void run() 
	{
		for (int i = 0; i < iterations; i++) 
		{
			if (operation == Operation.DEPOSIT)
				bankAccount.deposit(10);
			else
				bankAccount.withdraw(10);
		}
	}
}