import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Problem2Reducer extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int total = 0;
        int passed = 0;

        for (Text val : values) {
            total++;
            if (val.toString().equals("PASS")) {
                passed++;
            }
        }

        double passPercentage = ((double) passed / total) * 100;

        context.write(key, new Text(String.format("%.2f", passPercentage)));
    }
}