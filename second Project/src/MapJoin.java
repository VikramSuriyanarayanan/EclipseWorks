package MapSideJoin;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;



public class MapJoin {
	static HashMap < String , String  > ht = new HashMap < String ,  String >() ;

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(MapJoin.class); // Create a JobConf object
		conf.setJobName("wordcount"); // job name

		conf.setOutputKeyClass(Text.class); // see output.collect in map --> this and the following line are reduce input pair so reduce should have Text-IntWritable pair as input
		conf.setOutputValueClass(IntWritable.class); // see output.collect

		conf.setClass(null, configure.class, null);
		conf.setMapperClass(Map.class);
		conf.setInputFormat(TextInputFormat.class); // input type is text, key is line no., value is the line words
		conf.setOutputFormat(TextOutputFormat.class); // output type is text

		FileInputFormat.setInputPaths(conf, new Path(args[1])); // specifies the Input directory /home/<anyname_or_netid>/input
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[2])); // specifies the Output directory /home/<anyname_or_netid>/output


		JobClient.runJob(conf); 


	}
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		Map(){
		}
		public void map( LongWritable lineNumber , Text value ,OutputCollector < Text , Text > output , Reporter reporter )throws IOException {
			String [] rightRecord = value . toString () . split (" ",5 );
			if( rightRecord . length == 5)
			{
				if(ht.containsKey(rightRecord[0])){
					output . collect (new Text ( rightRecord [0]) , new Text
							(  rightRecord [0] + "\t" + rightRecord [1] + rightRecord [2] + rightRecord [3] +rightRecord [4]) );
				}
				/*for( String leftRecord : ht.g )
			{
			output . collect (new Text ( rightRecord [0]) , new Text
			( leftRecord + "\t" + rightRecord [1] + rightRecord [2] + rightRecord [3] +rightRecord [4]) );
			}*/

			}
		}
	}
	public class configure{
		public void configure ( JobConf conf ) {

			//Read the broadcasted file
			File T1 = new File ( conf.get ("new Path(args[0])"));
			//Hashtable to store the tuples

			BufferedReader br = null;
			String line = null;
			try{
				br = new BufferedReader (new FileReader ( T1 ));
				while(( line = br . readLine () ) !=null)
				{
					String record [] = line . split ("\t" , 2) ;
					if( record.length == 2)
					{
						//Insert into Hashtable
						/*if( ht.containsKey ( record [0]) )
						{
							ht . get ( record [0]) . add ( record [1]) ;
						}
						else
						{
							ArrayList < String > value = new ArrayList <
									String >() ;
							value . add ( record [1]) ;
							ht . put ( record [0] , value );
						}*/
						ht.put(record[0], record[1]);
					}
				}
			}
			catch( Exception e)
			{
				e. printStackTrace () ;
			}
		}
	}
}
