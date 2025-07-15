package mx.com.telcel.hadoop.mr.mapper;

import java.io.IOException;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Mapper que emite el ID y la secuencia de ADN.
 */
public class DnaMapper extends Mapper<Object, Text, Text, NullWritable> {

    // Saltar cabecera
    private boolean isFirstLine = true;

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        // Ignora la cabecera
        if (isFirstLine && line.startsWith("Date")) {
            isFirstLine = false;
            return;
        }

        String[] fields = line.split(",");

        // Validar que tenga al menos 3 columnas
        if (fields.length >= 3) {
            String sampleId = fields[1].trim();
            String sequence = fields[2].trim();
            context.write(new Text(sampleId + "\t" + sequence), NullWritable.get());
        }
    }
}

