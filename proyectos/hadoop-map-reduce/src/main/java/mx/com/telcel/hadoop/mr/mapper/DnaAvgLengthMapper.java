package mx.com.telcel.hadoop.mr.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DnaAvgLengthMapper extends Mapper<Object, Text, Text, IntWritable> {
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split(",");
        if (tokens.length >= 3 && !tokens[2].equalsIgnoreCase("Sequence")) {
            String id = tokens[1].trim();
            String sequence = tokens[2].trim();
            context.write(new Text(id), new IntWritable(sequence.length()));
        }
    }
}
