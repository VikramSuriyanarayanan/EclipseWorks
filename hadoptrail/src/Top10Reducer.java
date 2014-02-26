import java.io.IOException;
import java.util.*;	

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.util.*; 

public abstract class Top10Reducer implements Reducer<NullWritable, Text, NullWritable, Text> {

        // setup
        private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
        private Text outputValue = new Text();
        private int ctr = 0;

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
