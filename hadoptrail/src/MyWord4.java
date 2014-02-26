import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapreduce.Mapper.Context;
public class MyWord4{
	public MyWord4(){};

	// Mapper implementation --------- processes one line at a time
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		public Map(){}; 
		private Text mont = new Text();

		// map method
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

			String strval = "";
			String line = value.toString();
			int val1 = 0 ;
			int val2 = 0;

			String[] splitvalue = line.split(",");

			if(splitvalue.length > 12)
			{
				String[] splitvaltwo = splitvalue[11].split("/");
				if(splitvaltwo.length > 1){
					val1 = Integer.parseInt(splitvaltwo[0].trim());
					
					String[] splitvalthree = splitvaltwo[2].split(" ");
					
					if(splitvalthree.length > 1){
						val2 = Integer.parseInt(splitvaltwo[0].trim());
					}
				}
				}
			
			switch(val1){
			case 1:
				strval = "January";
				break;
			case 2:
				strval = "February";
				break;
			case 3:
				strval = "March";
				break;
			case 4:
				strval = "April";
				break;
			case 5:
				strval = "May";
				break;
			case 6:
				strval = "June";
				break;
			case 7:
				strval = "July";
				break;
			case 8:
				strval = "August";
				break;
			case 9:
				strval = "September";
				break;
			case 10:
				strval = "October";
				break;
			case 11:
				strval = "November";
				break;
			case 12:
				strval = "December";
				break;
			default :
				strval = "invalid";
				break;
			}

			if(!(strval.equals("invalid")))
			{
				mont.set(strval);
				
				output.collect(mont, new IntWritable(val2));
			}
		}
	}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
		public Reduce(){}; 		 
		
		HashSet<Integer> m1 = new HashSet();
		
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			int sum = 0; // reduce method
			int avg = 0; 
			while (values.hasNext()) {
				m1.add(values.next().get());
				sum ++;
			}
			
			avg = sum/(m1.size());
			output.collect(key, new IntWritable(avg));
		}
	}


	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(MyWord4.class); // Create a JobConf object
		conf.setJobName("wordcount"); // job name

		conf.setOutputKeyClass(Text.class); // see output.collect in map --> this and the following line are reduce input pair so reduce should have Text-IntWritable pair as input
		conf.setOutputValueClass(IntWritable.class); // see output.collect

		conf.setMapperClass(Map.class); // specifies the mapper class
	
		conf.setReducerClass(Reduce.class); // specifies the reducer

		conf.setInputFormat(TextInputFormat.class); // input type is text, key is line no., value is the line words
		conf.setOutputFormat(TextOutputFormat.class); // output type is text

		FileInputFormat.setInputPaths(conf, new Path(args[0])); // specifies the Input directory /home/<anyname_or_netid>/input
		FileOutputFormat.setOutputPath(conf, new Path(args[1])); // specifies the Output directory /home/<anyname_or_netid>/output

		JobClient.runJob(conf); // calling the JobClient.runJob to submit the job and monitor progress

	}


}

