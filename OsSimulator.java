/**
 * Main code of the Operating System Simulator.
 * Here is where the meta data is read in and 
 * where all the processing happens.
 * 
 * by: Brandon Main
 */


import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.lang.Thread;

public class OsSimulator{

	private static float version;
	private static File metaFilePath;
	private static int monitor;
	private static int processor;
	private static int scanner;
	private static int hardDrive;
	private static int keyboard;
	private static int memory;
	private static int projector;
	private static boolean logToMonitor;
	private static boolean logToFile;
	private static String log;
	private static File logFile;
	private static int memorySize;
	private static LinkedList<String> metaData;

	/**
	 * Constructs the OsSimulator object and takes a string
	 * containing the elements of the simulator configuration.
	 *
	 * @param      hold  
	 */
	public OsSimulator(String[] hold){

		//Set all elements of configuration.
		version = Float.parseFloat(hold[0]);
		metaFilePath = new File(hold[1]);
		monitor = Integer.parseInt(hold[2]);
		processor = Integer.parseInt(hold[3]);
		scanner = Integer.parseInt(hold[4]);
		hardDrive = Integer.parseInt(hold[5]);
		keyboard = Integer.parseInt(hold[6]);
		memory = Integer.parseInt(hold[7]);
		projector = Integer.parseInt(hold[8]);
		memorySize = Integer.parseInt(hold[9]);
		getMemSize(hold[14]);
		log = hold[10];

			
		if(log.equals("Log to File"))
		{
			logToFile = true;
			logToMonitor = false;
		}
		else if(log.equals("Log to Monitor"))
		{
			logToMonitor = true;
			logToFile = false;
		}
		else if(log.equals("Log to Both"))
		{
			logToFile = true;
			logToMonitor = true;
		}

		if(hold[11] != null)
		{
			logFile = new File(hold[11]);
			if(!logFile.exists())
			{
				try
				{
					logFile.createNewFile();
				}
				catch(IOException e)
				{
					System.out.println("Error: Could not open log file.");
				}
			}
		}
	}

	/**
	 * Scans meta data file and starts and runs processes based
	 * on the file contents.
	 */
	public static void run(){

		metaData = new LinkedList<String>();

		try
        {
       		Scanner scanFile = new Scanner(metaFilePath);

            //Scan file till we have no more data
            if(scanFile.hasNext())
            {
            	String temp = scanFile.nextLine();
            }
        	while (scanFile.hasNext()) 
            {

                String s = scanFile.nextLine();
                Scanner scanString = new Scanner(s);
                scanString.useDelimiter("(; )|;|\\.");


                while(scanString.hasNext())
                {
                	metaData.add(scanString.next());

                }
    			

        	}
        	scanFile.close();

        	metaData.removeLast();
        }
        catch (IOException e) 
        {
           e.printStackTrace();
        }

        parseMetaData(metaData);

        //runApplications();
	}

	/**
	 * Parses the metaData that was read in from the meta data file.
	 * Uses a linked list to and iterates through the list to desginate
	 * the next task for the simulation.
	 *
	 * @param      metaData  The meta data
	 */
	public static void parseMetaData(LinkedList metaData)
	{
		//Convert linked list object to a string to be parsed.
		String temp = null;
		String descriptor = null;
		Double cycles = 0.0;
		Iterator index = metaData.iterator();
		Object o = index.next();
		String s = o.toString();
		double startTime = 0;
		double newtime = 0;
		boolean isDone = false;

		Scanner scan = new Scanner(s);
		scan.useDelimiter("\\{|\\}");
		while(isDone == false)
		{
			if(scan.hasNext())
			{
				temp = scan.next();
			}
			//Start Operating system
			if(temp.equals("S"))
			{
				descriptor = scan.next();
				if(descriptor.equals("finish") || descriptor.equals("end"))
				{
					isDone = true;
				}
				else
				{
					startTime = System.nanoTime();
					//Get operaton multiplier
					cycles = Double.parseDouble(scan.next());

					loggingTime("Starting Operating System", startTime, cycles, descriptor);
				}

			}
			else if(temp.equals("A"))
			{
				//Initialize process control block
				PCB proc = new PCB();

				//If process is ready then start process.
				if(proc.processState == 1 && scan.hasNext())
				{
					descriptor = scan.next();

					//Check if process is ready to begin
					if(descriptor == "start" || descriptor == "begin")
					{
						//Start running process
						proc.processState = 2;
					}

					cycles = Double.parseDouble(scan.next());

					loggingTime("OS: Preparing Process 1", startTime, cycles, descriptor);


					//Get the next meta data element
					if(index.hasNext())
					{
						o = index.next();
					}
					s = o.toString();
					scan = new Scanner(s);
					scan.useDelimiter("\\{|\\}");

					loggingTime("OS: Running Process 1", startTime, cycles, descriptor);

					//While proceess is not done keep running
					while(proc.processState != 4)
					{
						//Get the type of descriptor
						temp = scan.next();

						//Check discriptor and do apporiate action.
						//
						if(temp.equals("P"))
						{
							if(scan.hasNext())
							{
								descriptor = scan.next();
							}
							cycles = Double.parseDouble(scan.next());

							loggingStartTime("Process 1: Starting processing action",startTime);
							loggingTime("Process 1: Ending processing action", startTime, cycles, descriptor);

						}
						else if(temp.equals("I"))
						{

							if(scan.hasNext())
							{
								descriptor = scan.next();
							}

							cycles = Double.parseDouble(scan.next());

							MyRunnable r = new MyRunnable(compareDescriptor(descriptor), cycles, startTime);
							Thread t = new Thread(r);
							t.start();
					
							loggingStartTime("Process 1: Starting " + descriptor + " input", startTime);
							//Log again after thread finishes
							loggingStartTime("Process 1: Ending " + descriptor + " input", startTime);	
						}
						else if(temp.equals("O"))
						{
							if(scan.hasNext())
							{
								descriptor = scan.next();
							}

							cycles = Double.parseDouble(scan.next());

							MyRunnable r = new MyRunnable(compareDescriptor(descriptor), cycles, startTime);
							Thread t = new Thread(r);
							t.start();

							loggingStartTime("Process 1: Starting " + descriptor + " ouput", startTime);
							//Log again after thread finishes
							loggingStartTime("Process 1: Ending " + descriptor + " ouput", startTime);	
						}
						else if(temp.equals("M"))
						{
							if(scan.hasNext())
							{
								descriptor = scan.next();
							}	

							cycles = Double.parseDouble(scan.next());

							if(descriptor.equals("allocate"))
							{
								loggingStartTime("Process 1: Starting to " + descriptor + " memory at 0x" + getRandomNumberInRange(0, memorySize), startTime);
							}
							loggingStartTime("Process 1: Starting to " + descriptor + " memory", startTime);
							loggingTime("Process 1: Ending memory " + descriptor, startTime, cycles, descriptor);					
						}
						else if(temp.equals("A"))
						{
							if(scan.hasNext())
							{
								descriptor = scan.next();
								if(descriptor.equals("finish") || descriptor.equals("end"))
								{
									//End Process
									proc.processState = 4;
								}

								cycles = Double.parseDouble(scan.next());

								loggingTime("OS: Terminating Process 1", startTime, cycles, descriptor);	
						
							}
						}
						else
						{
							System.out.println("Invalid meta data.");
						}

						//Get next meta data object
						if(index.hasNext())
						{
							o = index.next();
						}
						s = o.toString();
						scan = new Scanner(s);
						scan.useDelimiter("\\{|\\}");

					}
				}
				
			}
			else
			{
				System.out.println("Invalid meta data, skipping to next input.");
			}

			if(index.hasNext())
			{
				o = index.next();
			}
			s = o.toString();
			scan = new Scanner(s);
			scan.useDelimiter("\\{|\\}");
		}

	}

	/**
	 * Logs the time a task begins at either to the monitor, a file,
	 * or both.
	 *
	 * @param      s          String to be logged
	 * @param      startTime  The start time of the simulator
	 */
	public static void loggingStartTime(String s, double startTime)
	{
		double time = (System.nanoTime() - startTime)/1000000.0;

		if(logToMonitor)
		{
			System.out.println(time + " - " + s);
		}
		if(logToFile)
		{
			try{

    		BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
   			writer.append(time + " - " + s + "\n");
     
    		writer.close();

    		}
    		catch(IOException e)
    		{

    		}

		}

	}

	/**
	 * Logs the time a task ends running at to the monitor, a file
	 * or both.
	 *
	 * @param      s           String to be printed
	 * @param      startTime   The start time of the simulation
	 * @param      cycles      Cycles specified from meta data file
	 * @param      descriptor  The descriptor
	 */
	public static void loggingTime(String s, double startTime, double cycles, String descriptor)
	{
		double runTime = (System.nanoTime()/1000000.0) + (Double.valueOf(compareDescriptor(descriptor))*cycles);
		
		//Wait till system time reaches how long it's supposed
		//to wait for.
		while(System.nanoTime() <= runTime);

		double time = (System.nanoTime() - startTime)/1000000.0;

		if(logToMonitor)
		{
			System.out.println(time + " - " + s);
		}
		if(logToFile)
		{
			try{

    		BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
   			writer.append(time + " - " + s + "\n");
     
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
	private static int compareDescriptor(String descriptor)
	{
		if(descriptor == "run")
		{
			return processor;
		}
		else if(descriptor == "monitor")
		{
			return monitor;
		}
		else if(descriptor == "hard drive") 
		{
			return hardDrive;
		}
		else if(descriptor == "projector")
		{
			return projector;
		}
		else if(descriptor == "scanner")
		{
			return scanner;
		}
		else if(descriptor == "keyboard")
		{
			return keyboard;
		}
		else if(descriptor == "block" || descriptor == "allocate")
		{
			return memory;
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

	/**
	 * Converts the given memory size to its respective size in bits.
	 *
	 * @param      memoryType  The memory type
	 */
	private static void getMemSize(String memoryType)
	{
		if(memoryType.equals("kbytes"))
		{
			memorySize = memorySize*1000*8;
		}
		else if(memoryType.equals("Mbytes"))
		{
			memorySize = memorySize*1000000*8;
		}
		else if(memoryType.equals("Gbytes"))
		{
			memorySize = memorySize*1000000000*8;
		}
	}
}

