import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Problem4Mapper extends Mapper<LongWritable, Text, StudentSemesterKey, Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        if (line.startsWith("studentId")) {
            return;
        }

        String[] fields = line.split(",");
        if (fields.length < 5) return;

        String studentId = fields[0];
        int semester = Integer.parseInt(fields[1]);
        double marksObtained = Double.parseDouble(fields[3]);
        double maxMarks = Double.parseDouble(fields[4]);

        double percentage = (marksObtained / maxMarks) * 100;

        StudentSemesterKey compositeKey = new StudentSemesterKey(studentId, semester);
        // value now carries semester alongside the percentage
        context.write(compositeKey, new Text(semester + "," + percentage));
    }
}