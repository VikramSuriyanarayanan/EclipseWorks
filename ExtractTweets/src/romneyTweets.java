

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.fs.Path;
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

public class romneyTweets{

	romneyTweets(){

	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(romneyTweets.class); // Create a JobConf object
		conf.setJobName("wordcount"); // job name

		conf.setOutputKeyClass(Text.class); // see output.collect in map --> this and the following line are reduce input pair so reduce should have Text-IntWritable pair as input
		conf.setOutputValueClass(Text.class); // see output.collect

		conf.setMapperClass(Map.class); // specifies the mapper class

		conf.setInputFormat(TextInputFormat.class); // input type is text, key is line no., value is the line words
		conf.setOutputFormat(TextOutputFormat.class); // output type is text

		FileInputFormat.setInputPaths(conf, new Path(args[0])); // specifies the Input directory /home/<anyname_or_netid>/input
		FileOutputFormat.setOutputPath(conf, new Path(args[1])); // specifies the Output directory /home/<anyname_or_netid>/output


		JobClient.runJob(conf); // calling the JobClient.runJob to submit the job and monitor progress
		// if (conf.isSuccessful())



	}

	// Mapper implementation --------- processes one line at a time
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		Map(){
		}
		// map method
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

			ArrayList<String> obaList = new ArrayList<String>();
			ArrayList<String> romList = new ArrayList<String>();

			// OBAMA LIST
			obaList.add("obama");
			obaList.add("democrat");
			obaList.add("president");
			obaList.add("barrack");
			obaList.add("michelle");



			//ROMNEY LIST
			romList.add("romney");
			romList.add("republican");
			romList.add("romneyryan");
			romList.add("mitt");

			String line = value.toString();
			String linebkp= value.toString();




			if(line!=null && line.length()>0)
			{

		//		int perCount=0;

				/* line = line.toLowerCase();

	 			    	  for(int p=0;p<execWordList.size();p++){
	 			    		  line=line.replaceAll(execWordList.get(p), "");
	 			    	  } 

	 			    	 // String[] tokens = line.split(" ");

	 			    	 /* for(int i=0;i<tokens.length;i++){
	 			    		  if(obaList.contains(tokens[i])){
	 			    			  perCount++;
	 			    		  }

	 			    	  }*/

				for(int i=0;i<romList.size();i++)
				{
					if(line.contains(romList.get(i)))
						if(!line.contains(obaList.get(i)))
							output.collect(new Text(""),new Text(linebkp));
				}

				/*  if(perCount>0)
	 			    	  {
	 			    		  output.collect(new Text(""),new Text(linebkp));
	 			    	  }*/


			}


		}
	}


}
