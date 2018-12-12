/**
 * Operating System class
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Os
{
	private Config config;
	private MetaData metaData;
	private Queue<Application> apps;
	private Resource[] projectors;
	private Resource[] hdd;


	/**
	 * Constructs operating system (Os) object and puts meta data into
	 * queue of Application class objects.
	 *
	 * @param      metaData  The meta data
	 */
	public Os(String[] args)
	{
		//Set simulator configuration
		//
		config = new Config(args);

		//Parse and aquire meta data
		//
		metaData = new MetaData(config.getMetaFile());

		//Instansiate queue of processes
		//
		apps = new LinkedList<Application>();
		Application a = new Application();
		SubProcess s = new SubProcess();
		int processNumber = 1;

		for (int i = 1; i < metaData.size() - 1; i++) 
		{
			//If code is A then we have a new process or end of process.
			//
			if(metaData.getCode(i).equals("A"))
			{
				if(metaData.getDescriptor(i).equals("finish") || metaData.getDescriptor(i).equals("end"))
				{
					//Add Application object to application queue
					//
					apps.add(a);
				}
				else 
				{
					a = new Application(metaData.getCode(i), metaData.getDescriptor(i), metaData.getCycle(i), processNumber);
					processNumber++;
				}
			}
			else //Else subprocess of process
			{
				s = new SubProcess(metaData.getCode(i), metaData.getDescriptor(i), metaData.getCycle(i));

				//Add sub process
				//
				a.add(s);
			}
		}
	}

	/**
	 * Reschedules processes based on CPU schedule type.
	 */
	private void schedule()
	{
		if(config.getScheduleType().equals("FIFO"))
		{
			return;
		}
		else if((config.getScheduleType()).equals("PS"))
		{
			//Reschedules processes by priority
			//
			int size = apps.size();
			Application[] a = new Application[size];
		
			for (int i = 0; i < size; i++) 
			{
				a[i] = new Application(apps.peek());
				apps.remove();
			}

			apps.clear();

			//Reschedule processes based on IO amount
			//
			Application temp;

			for (int i = 0; i < size-1; i++) 
			{
				if(a[i].getIOamount() < a[i+1].getIOamount())
				{
					temp = new Application(a[i]);
					a[i] = new Application(a[i+1]);
					a[i+1] = new Application(temp);
				}
			}

			for (int i = 0; i < size; i++) 
			{
				apps.add(a[i]);
			}
		}
		else if(config.getScheduleType().equals("SJF"))
		{
			//Reschedules processes by shortest to most amount of tasks
			//
			int size = apps.size();
			Application[] a = new Application[size];
		
			for (int i = 0; i < size; i++) 
			{
				a[i] = new Application(apps.peek());
				apps.remove();
			}
			apps.clear();

			//Reschedule processes based on task amount
			//
			Application temp;

			for (int i = 0; i < size-1; i++) 
			{
				if(a[i].subProcesses.size() > a[i+1].subProcesses.size())
				{
					temp = new Application(a[i]);
					a[i] = new Application(a[i+1]);
					a[i+1] = new Application(temp);
				}
			}

			for (int i = 0; i < size; i++) 
			{
				apps.add(a[i]);
			}
		}
	}

	private void log(double time, String phrase)
	{
		if(config.logToMonitor)
		{
			System.out.println(time + " - " + phrase);
		}
		if(config.logToFile)
		{
			try
			{
    			BufferedWriter writer = new BufferedWriter(new FileWriter(config.logFile, true));
   				writer.append(time + " - " + phrase + "\n");
     
    			writer.close();
    		}
    		catch(IOException e)
    		{

    		}
		}
	}

	private void logTaskStart(double time, String phrase, SubProcess task)
	{
		if(config.logToMonitor)
		{
			if(task.getCode().equals("P"))
			{
				System.out.println(time + " - " + phrase + "start processing action");
			}
			else if(task.getCode().equals("I"))
			{
				if(task.getDescriptor().equals("hard drive"))
				{
					System.out.println(time + " - " + phrase + "start " + task.getDescriptor() + " input on HDD " + allocateHDD());
				}
				else
				{
					System.out.println(time + " - " + phrase + "start " + task.getDescriptor() + " input");
				}
			}
			else if(task.getCode().equals("O"))
			{
				if(task.getDescriptor().equals("projector"))
				{
					System.out.println(time + " - " + phrase + "start " + task.getDescriptor() + " output on PROJ " + allocatePROJ());
				}
				else if(task.getDescriptor().equals("hard drive"))
				{
					System.out.println(time + " - " + phrase + "start " + task.getDescriptor() + " output on HDD " + allocateHDD());
				}
				else
				{
					System.out.println(time + " - " + phrase + "start " + task.getDescriptor() + " output");
				}
			}
			else if(task.getCode().equals("M"))
			{
				if(task.getDescriptor().equals("block"))
				{
					System.out.println(time + " - " + phrase + "start memory blocking");
				}
				else if(task.getDescriptor().equals("allocate"))
				{
					System.out.println(time + " - " + phrase + "allocating memory");
				}
				
			}

		}
		if(config.logToFile)
		{
			try
			{
    			BufferedWriter writer = new BufferedWriter(new FileWriter(config.logFile, true));

    			if(task.getCode().equals("P"))
				{
					writer.append(time + " - " + phrase + "start processing action\n");
				}
				else if(task.getCode().equals("I"))
				{
					if(task.getDescriptor().equals("hard drive"))
					{
						writer.append(time + " - " + phrase + "start " + task.getDescriptor() + " input on HDD " + allocateHDD() + "\n");
					}
					else
					{
						writer.append(time + " - " + phrase + "start " + task.getDescriptor() + " input\n");
					}
				}
				else if(task.getCode().equals("O"))
				{
					if(task.getDescriptor().equals("projector"))
					{
						writer.append(time + " - " + phrase + "start " + task.getDescriptor() + " output on PROJ " + allocatePROJ() + "\n");
					}
					else if(task.getDescriptor().equals("hard drive"))
					{
						writer.append(time + " - " + phrase + "start " + task.getDescriptor() + " output on HDD " + allocateHDD() + "\n");
					}
					else
					{
						writer.append(time + " - " + phrase + "start " + task.getDescriptor() + " output\n");
					}
				}
				else if(task.getCode().equals("M"))
				{
					if(task.getDescriptor().equals("block"))
					{
						writer.append(time + " - " + phrase + "start memory blocking\n");
					}
					else if(task.getDescriptor().equals("allocate"))
					{
						writer.append(time + " - " + phrase + "allocating memory\n");
					}
				}
     
    			writer.close();
    		}
    		catch(IOException e)
    		{

    		}
		}
	}

	private void logTaskEnd(double time, String phrase, SubProcess task)
	{
		if(config.logToMonitor)
		{
			if(task.getCode().equals("P"))
			{
				System.out.println(time + " - " + phrase + "end processing action");
			}
			else if(task.getCode().equals("I"))
			{
				System.out.println(time + " - " + phrase + "end " + task.getDescriptor() + " input");
			}
			else if(task.getCode().equals("O"))
			{
				System.out.println(time + " - " + phrase + "end " + task.getDescriptor() + " output");
			}
			else if(task.getCode().equals("M"))
			{
				if(task.getDescriptor().equals("block"))
				{
					System.out.println(time + " - " + phrase + " end memory blocking");
				}
				else if(task.getDescriptor().equals("allocate"))
				{
					System.out.println(time + " - " + phrase + " memory allocated at 0x" + getRandomNumberInRange(0, config.memorySize));
				}
			}

		}
		if(config.logToFile)
		{
			try
			{
    			BufferedWriter writer = new BufferedWriter(new FileWriter(config.logFile, true));
    			if(task.getCode().equals("P"))
				{
					writer.append(time + " - " + phrase + "end processing action\n");
				}
				else if(task.getCode().equals("I"))
				{
					writer.append(time + " - " + phrase + "end " + task.getDescriptor() + " input\n");
				}
				else if(task.getCode().equals("O"))
				{
					writer.append(time + " - " + phrase + "end " + task.getDescriptor() + " output\n");
				}
				else if(task.getCode().equals("M"))
				{
					if(task.getDescriptor().equals("block"))
					{
						writer.append(time + " - " + phrase + " end memory blocking\n");
					}
					else if(task.getDescriptor().equals("allocate"))
					{
						writer.append(time + " - " + phrase + " memory allocated at " + getRandomNumberInRange(0, config.memorySize) + "\n");
					}
				}
    			writer.close();
    		}
    		catch(IOException e)
    		{

    		}
		}
	}

	/**
	 * Compares the descriptor value and returns the value that was
	 * read in from the configuration file into the simulator.
	 *
	 * @param      descriptor  The descriptor
	 *
	 * @return     Value of descriptor
	 */
	private int compareDescriptor(String descriptor)
	{
		if(descriptor.equals("run"))
		{
			return config.processor;
		}
		else if(descriptor.equals("monitor"))
		{
			return config.monitor;
		}
		else if(descriptor.equals("hard drive")) 
		{
			return config.hardDrive;
		}
		else if(descriptor.equals("projector"))
		{
			return config.projector;
		}
		else if(descriptor.equals("scanner"))
		{
			return config.scanner;
		}
		else if(descriptor.equals("keyboard"))
		{
			return config.keyboard;
		}
		else if(descriptor.equals("block") || descriptor.equals("allocate"))
		{
			return config.memory;
		}

		return 0;
	}

	public int allocateHDD()
	{
		int found = -1;
		for(int i = 0; i < config.hardDriveQty; i++)
		{
			if(!hdd[i].locked)
			{
				found = i;
				hdd[i].locked = true;
				return found;
			}
		}

		if(found == -1)
		{
			for (int i = 1; i < config.hardDriveQty; i++) 
			{
				hdd[i].locked = false;	
			}
		}
		return 0;
	}

	public int allocatePROJ()
	{
		int found = -1;
		for(int i = 0; i < config.projectorQty; i++)
		{
			if(!projectors[i].locked)
			{
				found = i;
				projectors[i].locked = true;
				return found;
			}
		}

		if(found == -1)
		{
			for (int i = 1; i < config.projectorQty; i++) 
			{
				projectors[i].locked = false;	
			}
		}
		return 0;
	}

	/**
	 * Returns a random number in hexadeciaml format that is between
	 * 0 and the specified memory size.
	 *
	 * @param      min         The minimum memory
	 * @param      memorySize  The memory size
	 *
	 * @return     The random number in range.
	 */
	private static String getRandomNumberInRange(int min, int memorySize) {

		Random r = new Random();
		return Long.toHexString(Long.valueOf(r.nextInt((memorySize - min) + 1) + min));
	}

	public void run()
	{
		//Begin running Os
		//
		double time = 0.0;
		log(time, "Simulator program starting");

		//Prepare resources
		//
		projectors = new Resource[config.projectorQty];
		hdd = new Resource[config.hardDriveQty];
		for (int i = 0; i < config.projectorQty; i++) 
		{
			projectors[i] = new Resource("PROJ");
		}
		for (int i = 0; i < config.hardDriveQty; i++) 
		{
			hdd[i] = new Resource("HDD");
		}

		//Sort processes based on CPU scheduling algo
		//
		schedule();

		//Run Processes
		//
		while(!apps.isEmpty())
		{
			//Get Process
			//
			Application newProcess = new Application(apps.peek());

			log(time/1000, "OS: preparing process " + newProcess.getProcessNumber());
			log(time/1000, "OS: starting process " + newProcess.getProcessNumber());

			//Run Process tasks till queue is empty.
			//
			while(!newProcess.subProcesses.isEmpty())
			{
				SubProcess task = new SubProcess(newProcess.getSubProcess());

				logTaskStart(time/1000, "Process " + newProcess.getProcessNumber() + ": " , task);

				if(task.getCode().equals("I") || task.getCode().equals("O"))
				{
					Thread t = new Thread(task);
					t.start();
					try
					{
						t.join();
					}
					catch(InterruptedException e)
					{

					}
				}

				time = time + (compareDescriptor(task.getDescriptor()) * task.getCycle());
				logTaskEnd(time/1000, "Process " + newProcess.getProcessNumber() + ": " , task);

			}

			//Remove finished process from queue
			//
			apps.remove();
		}



	}
}
