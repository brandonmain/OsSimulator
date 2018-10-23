/**
 * Class for handling the meta data.
 * 
 */

import java.io.*;
import java.util.*;

public class MetaData
{
	private static String[] metaCodes;
	private static String[] descriptors;
	private static int[] cycles;

	/**
	 * Constructs meta data class object given a meta data file.
	 * Then makes a linked list of the meta data and parses the 
	 * linked list and stores the data into seperate arrays.
	 *
	 * @param      file  The meta data file
	 */
	public MetaData(File file)
	{
		LinkedList<String> metaData;
		metaData = new LinkedList<String>();

		try
        {
       		Scanner scanFile = new Scanner(file);

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
	}

	/**
	 * Parses the metaData read in from the meta data file.
	 * 
	 *
	 * @param      metaData  The meta data
	 */
	public static void parseMetaData(LinkedList metaData)
	{
		//Iterates through meta data objects
		Iterator metaIndex = metaData.iterator();

		//Index of meta data
		int[] index = new int[1];
		index[0] = 0;

		//Variables to convert meta objects to strings
		Object o;
		String s;

		//Varibales to scan and store meta data
		Scanner scan;
		boolean isDone = false;
		metaCodes = new String[metaData.size()];
		descriptors = new String[metaData.size()];
		cycles = new int[metaData.size()];

		//Loop until reach the end of meta data
		while(isDone == false)
		{
			o = metaIndex.next();
			s = o.toString();
			scan = new Scanner(s);
			scan.useDelimiter("\\{|\\}");

			//Get meta code
			metaCodes[index[0]] = scan.next();

			//Get the descriptor
			descriptors[index[0]] = scan.next();

			//Get the cycles
			cycles[index[0]] = Integer.parseInt(scan.next());

			//Check if at the end of meta data
			if(metaCodes[index[0]].equals("S"))
			{
				if(descriptors[index[0]].equals("finish") || descriptors[index[0]].equals("end"))
				{
					//If at the end then set to done
					isDone = true;
				}
			}

			//Increment the index location
			index[0]++;
		}

		print();
	}

	/**
	 * @return    length of meta data
	 */
	public static int size()
	{
		return metaCodes.length;
	}

	/**
	 * Gets the meta code at the given index.
	 *
	 * @param      index  The index
	 *
	 * @return     The code.
	 */
	public static String getCode(int index)
	{
		return metaCodes[index];
	}

	/**
	 * Gets the meta descriptor at the gicen index.
	 *
	 * @param      index  The index
	 *
	 * @return     The descriptor.
	 */
	public static String getDescriptor(int index)
	{
		return descriptors[index];
	}

	/**
	 * Gets the cycle amount at the given index.
	 *
	 * @param      index  The index
	 *
	 * @return     The cycle.
	 */
	public static int getCycle(int index)
	{
		return cycles[index];
	}
	
	/**
	 * Print meta data
	 */
	private static void print()
	{
		for (int i = 0; i < metaCodes.length; i++) 
		{
			System.out.println(metaCodes[i]);
			System.out.println(descriptors[i]);	
			System.out.println(cycles[i]);
		}
	}
}
