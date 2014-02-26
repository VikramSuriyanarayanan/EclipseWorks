import java.io.IOException;
import java.util.*;	

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.util.*; 

public abstract class Top10Mapper implements Mapper<LongWritable, Text, NullWritable, Text> {
        // setup
        private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line, "\t");
            String visitorName = "NullName";
            int visitorCount = 0;
            if (tokenizer.hasMoreTokens()) {
                visitorName = tokenizer.nextToken();
            }
            if (tokenizer.hasMoreTokens()) {
                visitorCount = Integer.parseInt(tokenizer.nextToken());
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
 protected void cleanup(Context context) throws IOException, InterruptedException {
            // Output our ten records to the reducers with a null key
            for (Text t : repToRecordMap.values()) {
                context.write(NullWritable.get(), t);
            }
        }
    }
