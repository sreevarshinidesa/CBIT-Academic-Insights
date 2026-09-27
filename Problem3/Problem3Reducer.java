import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Problem3Reducer extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // studentId -> [sum of marks, sum of maxMarks]
        Map<String, double[]> studentTotals = new HashMap<>();

        for (Text val : values) {
            String[] parts = val.toString().split(",");
            String studentId = parts[0];
            double marks = Double.parseDouble(parts[1]);
            double maxMarks = Double.parseDouble(parts[2]);

            studentTotals.putIfAbsent(studentId, new double[]{0, 0});
            double[] totals = studentTotals.get(studentId);
            totals[0] += marks;
            totals[1] += maxMarks;
        }

        // compute each student's average percentage for this subject
        List<Map.Entry<String, Double>> averages = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : studentTotals.entrySet()) {
            double[] totals = entry.getValue();
            double avgPercentage = (totals[0] / totals[1]) * 100;
            averages.add(new AbstractMap.SimpleEntry<>(entry.getKey(), avgPercentage));
        }

        // sort descending by average percentage
        averages.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // write out top 3 only
        int rank = 1;
        for (Map.Entry<String, Double> entry : averages) {
            if (rank > 3) break;
            context.write(key, new Text("Rank " + rank + ": " + entry.getKey() + " (" + String.format("%.2f", entry.getValue()) + "%)"));
            rank++;
        }
    }
}
