package mx.com.telcel.hadoop.mr.job;

import mx.com.telcel.hadoop.mr.mapper.DnaAvgLengthMapper;
import mx.com.telcel.hadoop.mr.reducer.DnaAvgLengthReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DnaMapperReducerJob {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "DNA Avg Length Job");

        job.setJarByClass(DnaMapperReducerJob.class);
        job.setMapperClass(DnaAvgLengthMapper.class);
        job.setReducerClass(DnaAvgLengthReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
        // Calcula el promedio de longitud de secuencias de ADN por ID (por ejemplo, por paciente, muestra, etc.)
    }
}

