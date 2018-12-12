/**
 * SubProcess class to run the processes contained within the 
 * application process. this class implements the Runnable
 * interface so that it can be threaded when an I/O
 * operation occurs.
 */

public class SubProcess implements Runnable
{
	private String code;
	private String descriptor;
	private int cycle;

	public SubProcess()
	{
		this.code = "X";
		this.descriptor = "X";
		this.cycle = 0;
	}

	public SubProcess(SubProcess s)
	{
		this.code = s.code;
		this.descriptor = s.descriptor;
		this.cycle = s.cycle;
	}


	public SubProcess(String code, String descriptor, int cycle)
	{
		this.code = code;
		this.descriptor = descriptor;
		this.cycle = cycle;
	}

	/**
	 * Gets the code.
	 *
	 * @return     The code.
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * Gets the descriptor.
	 *
	 * @return     The descriptor.
	 */
	public String getDescriptor()
	{
		return descriptor;
	}

	/**
	 * Gets the cycle.
	 *
	 * @return     The cycle.
	 */
	public int getCycle()
	{
		return cycle;
	}

	/**
	 * Print contents of subprocess.
	 */
	public void print()
	{
		System.out.println(this.code);
		System.out.println(this.descriptor);
		System.out.println(this.cycle);
	}

	/**
	 * Implement run for Runnable
	 */
	public void run()
	{

	}
}