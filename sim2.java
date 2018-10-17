/**
 * The main class where the user enters the simulation.
 * This will be considered the "kernel" of this simulated
 * operating system.
 * 
 * by: Brandon Main
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;



public class sim2{

    //Hold values to put into configuration
    private static String[] hold = new String[15];

    
    public static void main(String[] args){

        ReadInConfigFile(args);

        //Begin Operating System
        OsSimulator simulation = new OsSimulator(hold);

        simulation.run();
        
    }

    /**
     * Reads in configuration file.
     *
     * @param      args  The arguments
     */
    private static void ReadInConfigFile(String[] args)
    {
        int[] index = new int[1];
        index[0] = 0;

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

                try
                {
               		Scanner scanFile = new Scanner(file);

                    //Scan file till we have no more data
        	       	while (scanFile.hasNext()) 
                    {

                        String s = scanFile.nextLine();
                        Scanner scanString = new Scanner(s);
                        scanString.useDelimiter(": ");
    
                        scanStringAndToss(scanString);

                        scanStringAndKeep(scanString, hold, index);

        	        }
        		    scanFile.close();
                }
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
    	} 


    }

    /**
     * Helper function that scans a string in solely to
     * advance the scanner to the next string in the file.
     *
     * @param      scanString  The scan string
     */
    private static void scanStringAndToss(Scanner scanString){

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
                    hold[14] = temp;
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
    private static void scanStringAndKeep(Scanner scanString, String[] hold, int[] index){

       if(scanString.hasNext())
        {
            hold[index[0]] = scanString.next();
            index[0]++;
        }

    }


}
