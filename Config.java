/**
 * Class that defines the configuration of 
 * the operating system simulation.
 */
import java.io.*;
import java.util.*;

public class Config
{
	public float version;
	public File metaFilePath;
	public int quantom;
	public String scheduleType;
	public int monitor;
	public int processor;
	public int scanner;
	public int hardDrive;
	public int keyboard;
	public int memory;
	public int projector;
	public boolean logToMonitor;
	public boolean logToFile;
	public String log;
	public File logFile;
	public int memorySize;
	public int memoryBlockSize;
	public int projectorQty;
	public int hardDriveQty;

	/**
	 * Constructs the configuration.
	 *
	 * @param      args  The arguments
	 */
	public Config(String[] args)
	{
		readConfigFile(args);
	}

    /**
     * Reads in configuration file.
     *
     * @param      args  The arguments
     */
	private void readConfigFile(String[] args)
	{
        if(args.length == 0)
        {
            System.out.println("Not enough command line arguments.\nPlease rerun and enter only one configuration file.");
        }
        else if(args.length > 1)
        {
            System.out.println("Too many command line arguments.\nPlease rerun and enter only one configuration file.");
        }
        else //File is good, setup simulator configuration.
        {
                File file = new File(args[0]);
                //Hold values to put into configuration
                //
    			String[] hold = new String[19];
   				int[] index = new int[1];

   				Arrays.fill(hold, null);
       			index[0] = 0;

                try
                {
               		Scanner scanFile = new Scanner(file);

                    //Scan file till we have no more data.
                    //
        	       	while (scanFile.hasNext()) 
                    {

                        String s = scanFile.nextLine();
                        Scanner scanString = new Scanner(s);
                        scanString.useDelimiter(": ");
    
                        scanStringAndToss(scanString, hold);

                        scanStringAndKeep(scanString, hold, index);

        	        }
        		    scanFile.close();
                }
                catch (IOException e) 
                {
                    e.printStackTrace();
                }

          		//Set the Simulators config variables.
          		//
                setConfig(hold);
    	} 
	}

	/**
	 * Sets the configuration variables.
	 *
	 * @param      hold  Temporarily holds varibale values.
	 */
	private void setConfig(String[] hold)
	{	
		//Set all elements of configuration.
		//
		version = Float.parseFloat(hold[0]);
		metaFilePath = new File(hold[1]);
		quantom = Integer.parseInt(hold[2]);
		scheduleType = hold[3];
		processor = Integer.parseInt(hold[4]);
		monitor = Integer.parseInt(hold[5]);
		hardDrive = Integer.parseInt(hold[6]);
		projector = Integer.parseInt(hold[7]);
		keyboard = Integer.parseInt(hold[8]);
		memory = Integer.parseInt(hold[9]);
		scanner = Integer.parseInt(hold[10]);
		memorySize = Integer.parseInt(hold[11]);
		memoryBlockSize = Integer.parseInt(hold[12]);
		projectorQty = Integer.parseInt(hold[13]);
		hardDriveQty =Integer.parseInt(hold[14]);
		getMemSize(hold[17], hold[18]);
		log = hold[15];

			
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

		if(hold[16] != null)
		{
			logFile = new File(hold[16]);
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
     * Helper function that scans a string in solely to
     * advance the scanner to the next string in the file.
     *
     * @param      scanString  The scan string
     */
    private void scanStringAndToss(Scanner scanString, String[] hold){

        if(scanString.hasNext())
        {
            String throwAway = scanString.next();
            Scanner s = new Scanner(throwAway);
            s.useDelimiter("\\{|\\}");

            if(s.hasNext())
            {
                String temp = s.next();
            }
            if(s.hasNext())
            {
                String temp = s.next();
                
                if(temp.equals("kbytes") || temp.equals("Mbytes") || temp.equals("Gbytes"))
                {
                	if(hold[17] == null)
                	{
                		hold[17] = temp;
                	}
                	else
                	{
                		hold[18] = temp;
                	}
                }
            }
        }
    }

    /**
     * Helper function that scans a string and keeps the data
     * to be loaded into configuring the Operating System
     * simulation.
     *
     * @param      scanString  The scan string
     * @param      hold        String array that holds the config data
     * @param      index       Index used to keep track of hold[]
     */
    private void scanStringAndKeep(Scanner scanString, String[] hold, int[] index){

       if(scanString.hasNext())
        {
            hold[index[0]] = scanString.next();
            index[0]++;
        }

    }

    /**
	 * Converts the given memory size to its respective size in bits.
	 *
	 * @param      memoryType  The memory type
	 */
	private void getMemSize(String memoryType, String blockMemoryType)
	{
		//System memory size
		//
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


		//Block memory size
		//
		if(blockMemoryType.equals("kbytes"))
		{
			memoryBlockSize = memoryBlockSize*1000*8;
		}
		else if(blockMemoryType.equals("Mbytes"))
		{
			memoryBlockSize = memoryBlockSize*1000000*8;
		}
		else if(blockMemoryType.equals("Gbytes"))
		{
			memoryBlockSize = memoryBlockSize*1000000000*8;
		}
	}

	/**
	 * Print the contents of all the config varibales.
	 * Used for debugging.
	 */
	private void print()
	{
		System.out.println(version);
		System.out.println(metaFilePath);
		System.out.println(quantom);
		System.out.println(scheduleType);
		System.out.println(processor);
		System.out.println(monitor);
		System.out.println(hardDrive);
		System.out.println(projector);
		System.out.println(keyboard);
		System.out.println(memory);
		System.out.println(scanner);
		System.out.println(memorySize);
		System.out.println(memoryBlockSize);
		System.out.println(projectorQty);
		System.out.println(hardDriveQty);
		System.out.println(log);
		System.out.println(logFile);
	}

	/**
	 * Returns the meta data file.
	 *
	 * @return     The meta file.
	 */
	public File getMetaFile()
	{
		return metaFilePath;
	}


	/**
	 * Gets the CPU schedule type.
	 *
	 * @return     The schedule type.
	 */
	public String getScheduleType()
	{
		return scheduleType;
	}


}