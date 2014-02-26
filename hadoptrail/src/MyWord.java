


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
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.mapreduce.Mapper.Context;
public class MyWord{
	public MyWord(){};

	// Mapper implementation --------- processes one line at a time
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		public Map(){}; 
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		// map method
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line, ","); // StringTokenizer splits the line into tokens separated by whitespaces.
			int count =0;
			String[] arraystrings = {"", ""};

			while (tokenizer.hasMoreTokens() && count<2) {
				arraystrings[count]=tokenizer.nextToken();
				count=count+1;
			}
			String val1=arraystrings[0]+ "\t" +arraystrings[1];

			word.set(val1);
			output.collect(word, one); // emits a key-value pair of < <word>, 1>
		}
	}


	// Reducer implementation --------- sums up the values
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

	public static class Top10Mapper extends org.apache.hadoop.mapreduce.Mapper<Object, Text, NullWritable, Text> {
		// setup
		public Top10Mapper(){};
		private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
		private Text outputValue = new Text();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String[] valueStr = value.toString().split("\t");
			String visitorName = valueStr[0];
			int visitorCount;
			
			try{
				visitorCount = Integer.parseInt(valueStr[1]);
			}catch(NumberFormatException e){
				visitorName = visitorName + "" + valueStr[1];
				visitorCount = Integer.parseInt(valueStr[2]);
				
			}
				outputValue.set(visitorName + "\t" + visitorCount);

			// Add this record to our map with the reputation as the key
			repToRecordMap.put(visitorCount, outputValue);

			// If we have more than ten records, remove the one with the lowest rep
			// As this tree map is sorted in descending order, the user with
			// the lowest reputation is the last key.
			if (repToRecordMap.size() > 10) {
				repToRecordMap.remove(repToRecordMap.firstKey());
			}
		}

		protected void cleanup(Context context) throws IOException,
		InterruptedException {

			// Output our ten records to the reducers with a null key
			for (Text t : repToRecordMap.values()) {
				String[] valueStr = t.toString().split("\t");
				String visitorName = valueStr[0];
				int visitorCount = Integer.parseInt(valueStr[1]);
				outputValue.set(visitorName + "\t" + visitorCount);
				context.write(NullWritable.get(), outputValue);
			}
		}
	}

	public static class Top10Reducer extends org.apache.hadoop.mapreduce.Reducer<NullWritable, Text, NullWritable, Text> {
		// setup
		public Top10Reducer(){};
		private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
		private Text outputValue = new Text();

		public void reduce(NullWritable key, Iterable<Text> values,
				Context context) throws IOException, InterruptedException {

			for (Text value : values) {
				String[] valueStr = value.toString().split("\t");

				String visitorName = valueStr[0];
				int visitorCount = Integer.parseInt(valueStr[1]);
				outputValue.set(visitorName + "\t" + visitorCount);
				repToRecordMap.put(visitorCount, outputValue);
				// If we have more than ten records, remove the one with the lowest rep
				// As this tree map is sorted in descending order, the user with
				// the lowest reputation is the last key.

				if (repToRecordMap.size() > 10) {
					repToRecordMap.remove(repToRecordMap.firstKey());
				}
			}

			for (Text t : repToRecordMap.descendingMap().values()) {
				// Output our ten records to the file system with a null key
				context.write(NullWritable.get(), t);
			}
		}
	}




	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(MyWord.class); // Create a JobConf object
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
		MultipleInputs.addInputPath(conf, path, inputFormatClass)
		job.setJarByClass(MyWord.class);
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



		/* if(((RunningJob) conf).isSuccessful())
{



 JobConf conf1 = new JobConf(WordCount.class);
    conf1.setJobName("top10");

    conf1.setOutputKeyClass(Text.class);
    conf1.setOutputValueClass(IntWritable.class);

    conf1.setMapperClass(Top10Mapper.class);
    conf1.setReducerClass(Top10Reducer.class);
    conf1.setReducerClass(Top10Reducer.class);
    conf1.setNumReduceTasks(1);

    conf1.setInputFormat(TextInputFormat.class);
    conf1.setOutputFormat(TextOutputFormat.class);

    /* Deprecated ?
		 * conf.setInputPath(new Path(args[0]));
		 * conf.setOutputPath(new Path(args[1]));     

    FileInputFormat.setInputPaths(conf1, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf1, new Path(args[1]));

    JobClient.runJob(conf1); 


  Configuration confb = new Configuration();
    org.apache.hadoop.mapreduce.Job job = new org.apache.hadoop.mapreduce.Job(confb,"part1");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(Top10Mapper.class);
    job.setReducerClass(Top10Reducer.class);
    job.setOutputKeyClass(NullWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setNumReduceTasks(1);

    job.setInputFormat(TextInputFormat.class);
    job.setOutputFormat(TextOutputFormat.class);
 org.apache.hadoop.mapreduce.lib.input.FileInputFormat.setInputPaths(job,new Path(args[1]));
 //args2
job.waitForCompletion(true); */
	}
}

