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
public class MyWord2{
	public MyWord2(){};

	// Mapper implementation --------- processes one line at a time
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		public Map(){}; 
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		// map method
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			
			String line = value.toString();
			String val1 = "";
			
			String[] splitvalue = line.split(",");
			if (splitvalue.length >18){
				val1 = splitvalue[19] + splitvalue[20];
			}
			
			word.set(val1);
			output.collect(word, one);
			
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
		public Reduce(){}; 		 
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			int sum = 0; // reduce method
			while (values.hasNext()) {
				sum += values.next().get();
			}
			output.collect(key, new IntWritable(sum));
		}
	}
public static class Top10Mapper extends  org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, NullWritable, Text> {
		
		public Top10Mapper() {
			// default constructor 
		}
	
        private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line, "\t");
            String visitorName = "NullName";
            int visitorCount = 0;
            if (tokenizer.hasMoreTokens()) {
                visitorName = tokenizer.nextToken();
            }
            String secondVal = "";
            if (tokenizer.hasMoreTokens()) {
            	secondVal = tokenizer.nextToken();
            	try{
            		visitorCount = Integer.parseInt(secondVal);
            	}catch(NumberFormatException exp){
            		visitorName = visitorName + " " + secondVal;
            		if(tokenizer.hasMoreTokens()){
            		visitorCount = Integer.parseInt(tokenizer.nextToken());
            		}
            	}
            }
            
            // Add this record to our map with the reputation as the key
            repToRecordMap.put(visitorCount, new Text(visitorCount + "\t" + visitorName));
            // If we have more than ten records, remove the one with the lowest rep
            // As this tree map is sorted in descending order, the user with
            // the lowest reputation is the last key.
            if (repToRecordMap.size() > 10) {
                repToRecordMap.remove(repToRecordMap.firstKey());
            }
        }
 @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            // Output our ten records to the reducers with a null key
            for (Text t : repToRecordMap.values()) {
                context.write(NullWritable.get(), t);
            }
        }
    }
	

	public  static class Top10Reducer extends org.apache.hadoop.mapreduce.Reducer<NullWritable, Text, NullWritable, Text> {

		public Top10Reducer() {
			// default constructor
		}
		
		private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
        private Text outputValue = new Text();

        public void reduce(NullWritable key, Iterator<Text> values,
                Context context) throws IOException, InterruptedException {
            while (values.hasNext()) {
                String[] valueStr = values.next().toString().split("\t");
                String visitorName = valueStr[1];
                int visitorCount = Integer.parseInt(valueStr[0]);
                outputValue.set("" + visitorCount + "\t" + visitorName);
                repToRecordMap.put(visitorCount, outputValue);
                
             // If we have more than ten records, remove the one with the lowest rep
                // As this tree map is sorted in descending order, the user with
                // the lowest reputation is the last key.
                if (repToRecordMap.size() > 10) {
                    repToRecordMap.remove(repToRecordMap.firstKey());
                }
            }
            for (Text t : repToRecordMap.values()) {
                // Output our ten records to the file system with a null key
                context.write(NullWritable.get(), t);
            }
        }
    }

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(MyWord2.class); // Create a JobConf object
		conf.setJobName("wordcount"); // job name

		conf.setOutputKeyClass(Text.class); // see output.collect in map --> this and the following line are reduce input pair so reduce should have Text-IntWritable pair as input
		conf.setOutputValueClass(IntWritable.class); // see output.collect

		conf.setMapperClass(Map.class); // specifies the mapper class
		conf.setCombinerClass(Reduce.class); // specifies the combiner
		conf.setReducerClass(Reduce.class); // specifies the reducer

		conf.setInputFormat(TextInputFormat.class); // input type is text, key is line no., value is the line words
		conf.setOutputFormat(TextOutputFormat.class); // output type is text

		FileInputFormat.setInputPaths(conf, new Path(args[0])); // specifies the Input directory /home/<anyname_or_netid>/input
		FileOutputFormat.setOutputPath(conf, new Path(args[1])); // specifies the Output directory /home/<anyname_or_netid>/output

		JobClient.runJob(conf); // calling the JobClient.runJob to submit the job and monitor progress

		//  if(conf.isSuccessful()) {

		org.apache.hadoop.conf.Configuration topconf = new org.apache.hadoop.conf.Configuration();
		org.apache.hadoop.mapreduce.Job job = new org.apache.hadoop.mapreduce.Job(topconf,"topk");
		job.setJarByClass(MyWord2.class);
		job.setMapperClass(Top10Mapper.class);
		job.setReducerClass(Top10Reducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(1);
		job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.TextInputFormat.class);
		job.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class);
		org.apache.hadoop.mapreduce.lib.input.FileInputFormat.setInputPaths(job,new Path(args[1]));
		org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job,new Path(args[2]));
		job.waitForCompletion(true);


	}
	
	
}
	