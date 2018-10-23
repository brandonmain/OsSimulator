/**
 * Operating System class
 */

import java.util.*;
import java.lang.*;

public class Os
{
	private static Config config;
	private static MetaData metaData;
	private static boolean run;
	private static Queue<Application> apps;
	boolean keyboardMutex;
	boolean projectorMutex;
	boolean hardDriveMutex;
	boolean scannerMutex;
	boolean monitorMutex;

	/**
	 * Constructs operating system (Os) object and puts meta data into
	 * queue of Application class objects.
	 *
	 * @param      metaData  The meta data
	 */
	public Os(String[] args)
	{
		//Set simulator configuration
		config = new Config(args);

		//Parse and aquire meta data
		metaData = new MetaData(config.getMetaFile());

		//Instansiate queue of processes
		Queue<Application> apps = new LinkedList<>();
		Application a;
		SubProcess s;

		for (int i = 1; i < metaData.size() - 1; i++) 
		{
			//If code is A then we have a new process
			if(metaData.getCode(i).equals("A"))
			{
				a = new Application(metaData.getCode(i), metaData.getDescriptor(i), metaData.getCycle(i));

				//Add sub processes to process queue the process until process end
				while(!metaData.getDescriptor(i).equals("finish") || !metaData.getDescriptor(i).equals("end"))
				{
					i++;

					s = new SubProcess(metaData.getCode(i), metaData.getDescriptor(i), metaData.getCycle(i));

					//Add sub process
					a.add(s);
				}

				//Add Application object to application queue
				apps.add(a);
			}
		}
	}

	private static void log()
	{

	}

	public static void run()
	{

		//log("Simulator program starting");
	}
}
