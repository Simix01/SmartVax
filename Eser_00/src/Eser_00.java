
public class Eser_00 
{
	public static void main(String[] args) 
	{
		Thread t = Thread.currentThread();
		System.out.println("Hello World"+t.getId());
		
		Thread th1 = new MyThread(1);
		Thread th2 = new MyThread(2);
		
		th1.start();
		th2.start();
		
	}
}
