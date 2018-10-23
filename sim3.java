
/**
 * Operating System Simulator
 * 
 * 
 * date: October 24, 2018
 * 
 * by: Brandon Main
 * 
 * 
 */

public class sim3
{
	private static Os operatingSystem;

	public static void main(String[] args)
	{
		//Run the simulation
		Os operatingSystem = new Os(args);
		operatingSystem.run();

	}

}