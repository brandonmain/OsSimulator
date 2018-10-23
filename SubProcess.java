/**
 * SubProcess class to run the processes contained within the 
 * application process. this class implements the Runnable
 * interface so that it can be threaded when an I/O
 * operation occurs.
 */

public class SubProcess implements Runnable
{

	private static String code;
	private static String descriptor;
	private static int cycle;

	public SubProcess(String code, String descriptor, int cycle)
	{
		this.code = code;
		this.descriptor = descriptor;
		this.cycle = cycle;
	}

	/**
	 * Gets the descriptor.
	 *
	 * @return     The descriptor.
	 */
	public static String getDescriptor()
	{
		return descriptor;
	}

	/**
	 * Implement run for Runnable
	 */
	public void run()
	{

	}
}