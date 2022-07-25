public class BankAccount 
{
	private long money;
	public long getMoney() 
	{
		return money;
	}
	public void setMoney(long money) 
	{
		this.money = money;
	}
	public synchronized void deposit(long money) 
	{
		this.money += money;
		notify();

	}
	public synchronized void withdraw(long money)
	{
		while(this.money-money<0)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.money -= money;
	}
}