/**
 * Application class that acts as the main
 * process of which contains a queue od subprocesses
 * to be processed.
 */

import java.util.*;

public class Application
{
	private String code;
	private String descriptor;
	private int cycle;
	private PCB pcb;
	public Queue<SubProcess> subProcesses;
	private int IOoperations;
	private int processNumber;


	/**
	 * Constructs an Application object.
	 *
	 * @param      code        The code
	 * @param      descriptor  The descriptor
	 * @param      cycle       The cycle
	 */
	public Application()
	{
		
	}

	/**
	 * Constructs the object.
	 *
	 * @param      a     { parameter_description }
	 */
	public Application(Application a)
	{
		this.code = a.getCode();
		this.descriptor = a.getDescriptor();
		this.cycle = a.getCycle();
		this.processNumber = a.getProcessNumber();
		this.IOoperations = a.getIOamount();
		this.pcb = new PCB(a.getPCB());
		this.subProcesses = new LinkedList<SubProcess>(a.subProcesses);

	}

	/**
	 * Constructs an Application object.
	 *
	 * @param      code        The code
	 * @param      descriptor  The descriptor
	 * @param      cycle       The cycle
	 */
	public Application(String code, String descriptor, int cycle, int processNumber)
	{
		this.code = code;
		this.descriptor = descriptor;
		this.cycle = cycle;
		this.processNumber = processNumber;
		this.subProcesses = new LinkedList<SubProcess>();
	}

	/**
	 * Gets the process number.
	 *
	 * @return     The process number.
	 */
	public int getProcessNumber()
	{
		return processNumber;
	}
	/**
	 * Gets IO amount.
	 *
	 * @return     I oamount.
	 */
	public int getIOamount()
	{
		return IOoperations;
	}

	public String getCode()
	{
		return code;
	}
	
	public String getDescriptor()
	{
		return descriptor;
	}	

	public int getCycle()
	{
		return cycle;
	}

	public PCB getPCB()
	{
		return pcb;
	}

	public SubProcess getSubProcess()
	{
		return subProcesses.poll();
	}

	public boolean empty()
	{
		return subProcesses.isEmpty();
	}

	/**
	 * Adds a new sub process the sub process queue.
	 *
	 * @param      s  subprocess object
	 */
	public void add(SubProcess s)
	{
		subProcesses.add(s);
		if(s.getCode().equals("I") || s.getCode().equals("O"))
		{
			IOoperations++;
		}
	}

	public void run()
	{
		
	}
}