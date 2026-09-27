import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Problem2Mapper extends Mapper<LongWritable, Text, Text, Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        if (line.startsWith("studentId")) {
            return;
        }

        String[] fields = line.split(",");
        if (fields.length < 5) return;

        String subjectCode = fields[2];
        double marksObtained = Double.parseDouble(fields[3]);
        double maxMarks = Double.parseDouble(fields[4]);

        double percentage = (marksObtained / maxMarks) * 100;
        String status = (percentage >= 40.0) ? "PASS" : "FAIL";

        // key = subjectCode   value = PASS or FAIL
        context.write(new Text(subjectCode), new Text(status));
    }
}
