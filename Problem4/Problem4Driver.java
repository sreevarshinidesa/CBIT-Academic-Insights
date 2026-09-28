import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Problem4Driver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Student Performance Trend (Secondary Sorting)");

        job.setJarByClass(Problem4Driver.class);
        job.setMapperClass(Problem4Mapper.class);
        job.setReducerClass(Problem4Reducer.class);

        // the secondary sorting pieces
        job.setPartitionerClass(StudentPartitioner.class);
        job.setGroupingComparatorClass(StudentGroupingComparator.class);

        job.setMapOutputKeyClass(StudentSemesterKey.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}