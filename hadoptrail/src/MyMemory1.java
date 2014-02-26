import java.io.*;
import java.util.*;

import org.apache.hadoop.mapred.JobConf;


public class MyMemory1 {

	private ht<String, String> = new HashMap<String, String>();
	
	public void configure ( JobConf conf ) {
		//Read the broadcasted file
		T1 = new File ( conf.get (" broadcast.file "));
		//Hashtable to store the tuples
		
		BufferedReader br = null;
		String line = null;
		try{
			br = new BufferedReader (new FileReader ( T1 ));
			while(( line = br . readLine () ) !=null)
			{
				String record [] = line . split ("\t" , 2) ;
				if( record . length == 2)
				{
					//Insert into Hashtable
					if( ht . containsKey ( record [0]) )
					{
						ht . get ( record [0]) . add ( record [1]) ;
					}
					else
					{
						ArrayList < String > value = new ArrayList <
								String >() ;
						value . add ( record [1]) ;
						ht . put ( record [0] , value );
					}
				}
			}
		}
		catch( Exception e)
		{
			e. printStackTrace () ;
		}
	}

	public static void main(String[] args) {


	}

}
