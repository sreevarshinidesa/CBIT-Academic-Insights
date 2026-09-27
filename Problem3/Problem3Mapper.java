import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Problem3Mapper extends Mapper<LongWritable, Text, Text, Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        if (line.startsWith("studentId")) {
            return;
        }

        String[] fields = line.split(",");
        if (fields.length < 5) return;

        String studentId = fields[0];
        String subjectCode = fields[2];
        String marksObtained = fields[3];
        String maxMarks = fields[4];

        // key = subjectCode   value = studentId,marksObtained,maxMarks
        context.write(new Text(subjectCode), new Text(studentId + "," + marksObtained + "," + maxMarks));
    }
}
