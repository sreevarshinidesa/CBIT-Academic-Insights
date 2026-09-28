import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Problem2Reducer extends Reducer<Text, Text, Text, Text> {

    private String lowestSubject = "";
    private double lowestPercentage = Double.MAX_VALUE;

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        int total = 0;
        int passed = 0;

        for (Text val : values) {
            total++;

            if (val.toString().equals("PASS")) {
                passed++;
            }
        }

        double passPercentage = ((double) passed / total) * 100;

        // Existing output
        context.write(key, new Text(String.format("%.2f", passPercentage)));

        // Find lowest pass percentage
        if (passPercentage < lowestPercentage) {
            lowestPercentage = passPercentage;
            lowestSubject = key.toString();
        }
    }

    @Override
    protected void cleanup(Context context)
            throws IOException, InterruptedException {

        context.write(
            new Text("Lowest Pass Percentage"),
            new Text(lowestSubject + " : " +
                     String.format("%.2f", lowestPercentage) + "%")
        );
    }
}