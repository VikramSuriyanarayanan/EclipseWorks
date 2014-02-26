package ReduceJoin;
//package databaseJoins;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;





public class ReduceSideJoin {

	public static class CustomPartitioner extends Partitioner<TuplePair, TuplePair> {

		@Override
		public int getPartition(TuplePair key, TuplePair value, int partitionCount) {
			return ( key.getTupleValue(). hashCode () & Integer . MAX_VALUE )
					% partitionCount ; 
		}		

	}
	public static class CustomGroupingComparator extends WritableComparator{

		protected CustomGroupingComparator(){
			super((Class<? extends WritableComparable<TuplePair>>) TuplePair.class, true);
		}
		@SuppressWarnings("rawtypes")
		@Override
		public int compare(WritableComparable w1, WritableComparable w2) {
			TuplePair ip1=(TuplePair)w1;
			TuplePair ip2=(TuplePair)w2;
			String str1=ip1.getTupleValue();
			String str2=ip2.getTupleValue();
			return str1.compareTo(str2);			
		}
				
	}
	/*public static class CustomGrouping implements RawComparator<TuplePair> {

		@Override
		public int compare(TuplePair arg0, TuplePair arg1) {			
			String str0=arg0.getTupleValue();
			String str1=arg1.getTupleValue();
			return str0.compareTo(str1);			
		}

		@Override
		public int compare(byte[] arg0, int arg1, int arg2, byte[] arg3,
				int arg4, int arg5) {
			return WritableComparator.compareBytes(arg0, arg1, arg2, arg3, arg4, arg5);
		}

	}*/
	public static class CompositeKeyComparator extends WritableComparator {

		/*protected CompositeKeyComparator(
				Class<? extends WritableComparable<TuplePair>> keyClass,
						boolean createInstances) {
			super(keyClass,true);
		}*/ 
		protected CompositeKeyComparator() {
			super((Class<? extends WritableComparable<TuplePair>>) TuplePair.class, true);
		}
		@SuppressWarnings("rawtypes")
		@Override
		public int compare(WritableComparable w1, WritableComparable w2) {
			TuplePair ip1=(TuplePair)w1;
			TuplePair ip2=(TuplePair)w2;
			
			int cmp = ip1.getTupleValue().compareTo(ip2.getTupleValue());
			if (cmp != 0) {
				return cmp;
			}
			return (ip1.getTag()-ip2.getTag()); 
		}

	}

	public static class JoinMapper extends  Mapper<Object, Text , TuplePair, TuplePair >{


		public void map(Object key,Text value,Context context) throws IOException,InterruptedException {

			String[] temp=value.toString().split("[ \t]",2);
			String joinKey=temp[0].trim();
			int tag=0;
			if(temp[1].trim().length()==2){	
				tag=1; 
			}

			TuplePair keyPair=	new TuplePair();
			keyPair.set(joinKey, tag);
			TuplePair valuePair=new TuplePair();
			valuePair.set(temp[1], tag);
			//if(tag==1)
			context.write(keyPair,valuePair);

		}


	}
	public static class JoinReducer extends Reducer<TuplePair, TuplePair, Text, Text>{



		public void reduce(TuplePair key,Iterable<TuplePair> values,Context context)throws IOException,InterruptedException{

			int tag = key.getTag();
			ArrayList <String> T1 = new ArrayList <String>();
			int check=0;
			for( TuplePair value:values)
			{
				//context.write(new Text(key.toString()), new Text(value.toString()));
				if(value.getTag()== tag)
				{	
					T1.add (value.getTupleValue()+"\t"+key.getTag());
				}
				else
				{	check=1;
					for( String val : T1 )
					{
						context.write(new Text(key.toString()), new Text(value.getTupleValue()+"\t"+val));
					}
				}
			}
			if(check==0){
				context.write(new Text(key.toString()), new Text("No Matching for this Join Key in Table HOST_COUNTRY"));
			}
			context.write(new Text("--------------------"), new Text(" -------------------------"));

		} 

	}


	public static void main(String[] args) {

		Configuration conf =new Configuration();
		Job job=null;
		try {
			FileSystem fs=FileSystem.get(conf);
			fs.delete(new Path(args[1]), true);
			job =new Job(conf, "Reduce Side Join");	

		} catch (IOException e) {
			e.printStackTrace();
		}

		job.setJarByClass(ReduceSideJoin.class);

		job.setMapperClass(JoinMapper.class);
		job.setReducerClass(JoinReducer.class);

		job.setMapOutputKeyClass(TuplePair.class);
		job.setMapOutputValueClass(TuplePair.class);

		job.setPartitionerClass(CustomPartitioner.class);

		job.setGroupingComparatorClass(CustomGroupingComparator.class);
		job.setSortComparatorClass(CompositeKeyComparator.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(1);


		try {
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
		} catch (IOException e) {
			e.printStackTrace();
		}


		try {
			System.exit(job.waitForCompletion(true) ? 0 : 1);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

}
