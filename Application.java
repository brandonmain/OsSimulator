import java.util.*;

public class Application
{
	private static String code;
	private static String descriptor;
	private static int cycle;
	private static PCB pcb;
	private static Queue<SubProcess> subProcesses;

	public Application(String code, String descriptor, int cycle)
	{
		this.code = code;
		this.descriptor = descriptor;
		this.cycle = cycle;
	}

	/**
	 * Adds a new sub process the sub process queue.
	 *
	 * @param      s  subprocess object
	 */
	public static void add(SubProcess s)
	{
		subProcesses.add(s);
	}
}