
 import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class classifiedoutput{
        
         classifiedoutput(){
        	 
            }
         
public static void main(String[] args) throws Exception {
  
JobConf conf = new JobConf(classifiedoutput.class); // Create a JobConf object
    
    conf.setJobName("classifiedoutput"); // job name

    conf.setOutputKeyClass(Text.class); // see output.collect in map --> this and the following line are reduce input pair so reduce should have Text-IntWritable pair as input
    conf.setOutputValueClass(IntWritable.class); // see output.collect

    conf.setMapperClass(Map.class); // specifies the mapper class
    conf.setCombinerClass(Reduce.class); // specifies the combiner
    conf.setReducerClass(Reduce.class); // specifies the reducer
    conf.setNumReduceTasks(1);

    conf.setInputFormat(TextInputFormat.class); // input type is text, key is line no., value is the line words
    conf.setOutputFormat(TextOutputFormat.class); // output type is text
    
    FileInputFormat.setInputPaths(conf, new Path(args[0])); // specifies the Input directory /home/<anyname_or_netid>/input
    FileOutputFormat.setOutputPath(conf, new Path(args[1])); // specifies the Output directory /home/<anyname_or_netid>/output
    

    JobClient.runJob(conf); // calling the JobClient.runJob to submit the job and monitor progress
   // if (conf.isSuccessful())
   
   

}
       
	 	// Mapper implementation --------- processes one line at a time
	 	// Mapper implementation --------- processes one line at a time
	 	  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
                   Map(){
                    }
	 	     private final static IntWritable one = new IntWritable(1);
	 	     private  Text word = new Text();
		// map method
	 	     public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	 	    	 String line = value.toString();
		 	       StringTokenizer tokens = new StringTokenizer(line, "\t");
                      
		 	    
	 	       String[] arraystrings = {"", ""};
                       String  val1 ="";
	 	      if(tokens.hasMoreTokens()) {
	                          arraystrings[0] = tokens.nextToken();
                       
	             }          

                     if(arraystrings[0].length()>0 && !arraystrings[0].equalsIgnoreCase("null")){
                      
	 	         word.set(arraystrings[0]);
	 	         output.collect(word, one); // emits a key-value pair of < <word>, 1>
                     }
	 	       }
	 	     }


      
	 	   
        
	// Reducer implementation --------- sums up the values
	 	  public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
                     Reduce(){
                       }
	  	     public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	  	       int sum = 0; // reduce method
	  	       while (values.hasNext()) {
	  	         sum += values.next().get();
	  	       }
	  	       output.collect(key, new IntWritable(sum));
	  	     }
	  	   }
	 	  
	 	
	     }
	


      
