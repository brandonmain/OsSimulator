/**
 * Runnable class to implement threading on the "Input/Output" 
 * operations done by the OsSimulator.
 * 
 * by: Brandon Main
 */

public class MyRunnable implements Runnable
{

	private int descriptor;
	private double cycles;
	private double startTime;

	public  MyRunnable(int d, double c, double s)
	{
		descriptor = d;
		cycles = c;
		startTime = s;

	}
	public void run()
	{
      	double runTime = (System.nanoTime()/1000000.0) + (Double.valueOf(descriptor)*cycles);
		//Wait till system time reaches how long it's supposed
		//to wait for.
		while(System.nanoTime()/1000000.0 < runTime){}

    }


	
}