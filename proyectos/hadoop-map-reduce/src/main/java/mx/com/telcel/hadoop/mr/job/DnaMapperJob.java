package mx.com.telcel.hadoop.mr.job;

import mx.com.telcel.hadoop.mr.mapper.DnaMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Map-only Hadoop job that processes a CSV file containing DNA sequences.
 */
public class DnaMapperJob {

    public static void main(String[] args) throws Exception {
        // Configuración base de Hadoop
        Configuration conf = new Configuration();

        // Crear una instancia de trabajo con nombre
        Job job = Job.getInstance(conf, "DNA Sequence Mapper");

        // Establecer la clase principal del job
        job.setJarByClass(DnaMapperJob.class);

        // Establecer la clase Mapper
        job.setMapperClass(DnaMapper.class);

        // Establecer el tipo de clave y valor de salida
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // Establecer rutas de entrada y salida
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Este es un Map-only job (sin Reducers)
        job.setNumReduceTasks(0);

        // Ejecutar el job
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}

/*
BUENAS PRACTICAS:
✅ Validación de columnas del CSV
✅ Ignora la cabecera del archivo
✅ Usa NullWritable cuando no hay necesidad de valor
✅ setNumReduceTasks(0) para Map-only
✅ Código limpio y modular
*/

