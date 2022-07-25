
public class MyThread extends Thread 
{
	private int id;
	
	public MyThread(int id)
	{
		this.id=id;
	}
	
	public void run() 
	{
		Thread t = Thread.currentThread();
		System.out.println("Hello From Thread"+t.getId());
	}
}
