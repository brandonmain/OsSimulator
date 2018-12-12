/**
 * Process Control Block class file.
 * 
 * by: Brandon Main
 */

public class PCB 
{
	/**
	 * Describes state of the Process.
	 * 	
	 * 		Start -> 0
	 * 		Ready -> 1
	 * 		Running -> 2
	 * 		Wait -> 3
	 * 		Exit -> 4
	 */
	public static int processState;

	public PCB()
	{
		//Declare process is ready to run.
		//
		processState = 1;
	}

	public PCB(PCB p)
	{
		//Declare process is ready to run.
		//
		processState = p.processState;
	}
}
