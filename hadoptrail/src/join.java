
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.jobcontrol.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;


public class join {
	//**************************8
	public class HostCntryMapper extends MapReduceBase implements Mapper<Text, Text, TextPair, TextPair> 
	 {
		 HostCntryMapper(){
        }	
		 public String tag = "R";
        
		public void map( Text key , Text values ,OutputCollector <TextPair, TextPair> output , Reporter reporter ) throws IOException 
		{
			
		/*	String line = values.toString();
	       String[] tokens = line.split(" "); 
	       String[] arraystrings = {"", ""};
	       if(tokens.length>0)
	    	   arraystrings[0] =tokens[0];
	       if(tokens.length>1)
	    	   arraystrings[1] =tokens[1];
	      
	       
	       key.set( arraystrings[0]);
	       values.set(arraystrings[1]);
				output.collect (new TextPair( key.toString () , tag ) ,new TextPair( values.toString (), tag )); */
			
			 String line = key.toString();
		      /*    String[] tokens = line.split(" "); // StringTokenizer splits the line into tokens separated by whitespaces.
		      
		       String[] arraystrings = {"", ""};
		      if(tokens.length>0)
		    	   arraystrings[0] = tokens[0];
		       if(tokens.length>1)
		    	   arraystrings[1] = tokens[1];
		       if(tokens.length>2)
		    	   arraystrings[2] = tokens[2];
		       if(tokens.length>3)
		    	   arraystrings[3] = tokens[3];
		       if(tokens.length>4)
		    	   arraystrings[4] = tokens[4];
		       key.set(arraystrings[0]);
		       value.set(arraystrings[1]+ " " + arraystrings[2] + " "+ arraystrings[3]+" "+arraystrings[4]);*/
		      if(line.indexOf(' ') !=-1)
		      {
		      String ip = line.substring(0,line.indexOf(' '));
		      output . collect (new TextPair( ip . toString () , tag ) ,new TextPair( values . toString () , tag ));
		      }
		}	
		}
//***********************	
	 public class NasaMapper extends MapReduceBase implements Mapper<Text, Text, TextPair, TextPair> 
	 {
		 NasaMapper(){
        }	
		 public String tag = "S";
        
		public void map( Text key , Text values ,OutputCollector <TextPair, TextPair> output , Reporter reporter ) throws IOException 
		{
			
			/*String line = values.toString();
	       String[] tokens = line.split("\t"); 
	       String[] arraystrings = {"", "", "","",""};
	       if(tokens.length>0)
	    	   arraystrings[0] =tokens[0];
	       if(tokens.length>1)
	    	   arraystrings[1] =tokens[1];
	       if(tokens.length>2)
	    	   arraystrings[2] =tokens[2];
	       if(tokens.length>3)
	    	   arraystrings[3] =tokens[3];
	       if(tokens.length>4)
	    	   arraystrings[4] =tokens[4];
	       
	       key.set( arraystrings[0]);
	       values.set(arraystrings[1] +" "+ arraystrings[2] + " "+ arraystrings[3] + " "+ arraystrings[4]);
				output . collect (new TextPair( key.toString () , tag ) ,new TextPair( values.toString (), tag ));*/
			
			 String line = key.toString();
		      /*    String[] tokens = line.split(" "); // StringTokenizer splits the line into tokens separated by whitespaces.
		      
		       String[] arraystrings = {"", ""};
		      if(tokens.length>0)
		    	   arraystrings[0] = tokens[0];
		       if(tokens.length>1)
		    	   arraystrings[1] = tokens[1];
		       if(tokens.length>2)
		    	   arraystrings[2] = tokens[2];
		       if(tokens.length>3)
		    	   arraystrings[3] = tokens[3];
		       if(tokens.length>4)
		    	   arraystrings[4] = tokens[4];
		       key.set(arraystrings[0]);
		       value.set(arraystrings[1]+ " " + arraystrings[2] + " "+ arraystrings[3]+" "+arraystrings[4]);*/
		      if(line.indexOf('-') !=-1)
		      {
		      String ip = line.substring(0,line.indexOf('-'));
		      output . collect (new TextPair( ip . toString () , tag ) ,new TextPair( values . toString () , tag ));
		      }
				
		}
	 }
//**********************************
	 public class TextPair implements WritableComparable<TextPair>{
			
			private Text first;
			private Text second;
			
			public TextPair()
			{
				set(new Text(),new Text());
				
			}

			public void set(Text first , Text second)
			{
				this.first=first;
				this.second=second;
				
			}
			
			public TextPair(String first, String second)
			{
				set(new Text(first),new Text(second));
				
			}
			
			public TextPair(Text first,Text second)
			{
				set(first,second);
			}
			
			public Text getfirst()
			{
				return first;
			}
			
			public Text getsecond()
			{
				return second;
			}
			
			public void write (DataOutput out) throws IOException
			{
				
				first.write(out);
				second.write(out);
			}
			
			public void readFields(DataInput in) throws IOException
			{
				
				first.readFields(in);
				second.readFields(in);
			}
			
			public int hashcode()
			{
				return first.hashCode()*163 +second.hashCode();
			}
			
			public boolean equals (Object o)
			{
				
				if(o instanceof TextPair)
				{
					
					TextPair tp = (TextPair) o;
					return first.equals(tp.first) && second.equals(tp.second);
				}
				
				return false;
			}
			
			public  String toString()
			{
				return first + "\t" + second;
			}
			
			public int compareTo (TextPair tp)
			{
				
				int cmp = first.compareTo(tp.first);
				if(cmp!=0)
					return cmp;
				return second.compareTo(tp.second);
			}
			
	/*		public static class Comparator extends WritableComparator
			{
				
				private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
				
				public Comparator()
				{
					super(TextPair.class);
				}
				
				public int compare (byte[] b1 , int s1 , int l1 ,byte[] b2 , int s2 , int l2 ) 
				{
						try
						{
							int firstL1 = WritableUtils . decodeVIntSize ( b1 [s1] ) + readVInt (b1 , s1 );
							int firstL2 = WritableUtils . decodeVIntSize ( b2 [s2]) + readVInt (b2 , s2 );
							int cmp = TEXT_COMPARATOR . compare (b1 , s1 , firstL1, b2 , s2 , firstL2 );
							if ( cmp != 0) 
							{
							  return cmp ;
							}
							return TEXT_COMPARATOR . compare (b1 , s1 + firstL1 ,l1 - firstL1 ,b2 , s2 + firstL2 , l2 - firstL2 );
							} 
							
							catch ( IOException e) 
							{
							throw new IllegalArgumentException (e);
							}
						}
				}
			
			static {
				
				WritableComparator.define(TextPair.class, new Comparator());
			}
			
			public static class FirstComparator extends WritableComparator
			{
				
				private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
				
				public FirstComparator()
				{
					
					super(TextPair.class);
				}
				
				public int compare (byte[] b1 , int s1 , int l1 ,byte[] b2 , int s2 , int l2 )
				{
						try 
						{
							int firstL1 = WritableUtils . decodeVIntSize ( b1 [s1]) + readVInt (b1 , s1 );
							int firstL2 = WritableUtils . decodeVIntSize ( b2 [s2]) + readVInt (b2 , s2 );
							return TEXT_COMPARATOR . compare (b1 , s1 , firstL1 ,b2 , s2 , firstL2 );
						} 
						catch ( IOException e)
							{
							throw new IllegalArgumentException (e);
							}
				}
			
				@Override	
				public int compare (WritableComparable a, WritableComparable b)
				{
					
					if( a instanceof TextPair && b instanceof TextPair )
					{
						return( (TextPair) a).first.compareTo(((TextPair)b).first);
						
						
					}
					return super.compare(a,b);
				 }
		   }  */
		}







	
	
	
	//*********************************
	join(){
		
	}
	public static void main(String[] args) throws Exception {
	Job job =  // Create a JobConf object
    job.setJobName("ReducesideJoin"); // job name

    /*conf.setOutputKeyClass(Text.class); // see output.collect in map --> this and the following line are reduce input pair so reduce should have Text-IntWritable pair as input
    conf.setOutputValueClass(Text.class); // see output.collect

   
   conf.setPartitionerClass(KeyPartitioner.class);
   
   conf.setReducerClass(Reduce.class); // specifies the reducer
    

    conf.setInputFormat(TextInputFormat.class); // input type is text, key is line no., value is the line words
    conf.setOutputFormat(TextOutputFormat.class); // output type is text
    
    FileInputFormat.setInputPaths(conf, new Path(args[0])); // specifies the Input directory /home/<anyname_or_netid>/input
    FileOutputFormat.setOutputPath(conf, new Path(args[1])); // specifies the Output directory /home/<anyname_or_netid>/output
    

    JobClient.runJob(conf); // calling the JobClient.runJob to submit the job and monitor progress
   // if (conf.isSuccessful())*/
		// org.apache.hadoop.conf.Configuration topconf = new org.apache.hadoop.conf.Configuration();
   	  //org.apache.hadoop.mapreduce.Job job = new org.apache.hadoop.mapreduce.Job(topconf,"reducesidejoin");
   	 
   	 // job.setMapperClass(Top10Mapper.class);
   	 // job.setReducerClass(Top10Reducer.class);
    Path edgeListPath = new Path(args[0]);
    
    
   	  job.setOutputKeyClass(NullWritable.class);
   	  job.setOutputValueClass(Text.class);
   	   MultipleInputs.addInputPath(job, edgeListPath,org.apache.hadoop.mapreduce.lib.input.TextInputFormat.class,HostCntryMapper.class);
   	  
   	job.setInputFormat(TextInputFormat.class); // input type is text, key is line no., value is the line words
	job.setOutputFormat(TextOutputFormat.class); // output type is text
   	  //org.apache.hadoop.mapreduce.lib.input.FileInputFormat.setInputPaths(job,new Path(args[1]));
   	  org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job,new Path(args[2]));
   	//  job.waitForCompletion(true);
	}
	
	
	 
	public static class KeyPartitioner implements Partitioner <TextPair, TextPair> {
	@Override
	public int getPartition (TextPair key , TextPair value , int numPartitions ) 
	{
	  return ( key . getfirst () . hashCode () & Integer .MAX_VALUE ) % numPartitions ;
	}
	@Override
	public void configure ( JobConf conf ) {}
	}
	 
	 
	 public static class Reduce extends MapReduceBase implements Reducer <TextPair,TextPair,Text,Text>
	 {
		 
	   Reduce(){
		   
		  }
	   public void reduce (TextPair key , Iterator<TextPair> values, OutputCollector<Text,Text> output ,Reporter reporter) throws IOException{
		   ArrayList<Text> T1 = new ArrayList<Text>();
		   Text tag = key.getsecond();
		   TextPair value = null;	
		   while(values.hasNext())
		   {
			   value = values.next();
			   if(value.getsecond().compareTo(tag)==0)
			   {
				   T1.add(value.getfirst());
			   }
			   
			   else
			   {
				   
				   for (Text val :T1)
				   {
					   output.collect(key.getfirst(),new Text(val.toString() +"\t"+value.getfirst().toString()));
					   
				   }
			   }
			   
		   }
		   
	   }
		 
	 }
}
	 
	 
