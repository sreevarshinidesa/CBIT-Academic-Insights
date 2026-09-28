import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Problem4Reducer extends Reducer<StudentSemesterKey, Text, Text, Text> {
    public void reduce(StudentSemesterKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // preserves insertion order -> since secondary sorting guarantees semester
        // order, semesters will naturally appear in increasing order here
        LinkedHashMap<Integer, double[]> semesterTotals = new LinkedHashMap<>();

        for (Text val : values) {
            String[] parts = val.toString().split(",");
            int semester = Integer.parseInt(parts[0]);
            double percentage = Double.parseDouble(parts[1]);

            semesterTotals.putIfAbsent(semester, new double[]{0, 0});
            double[] totals = semesterTotals.get(semester);
            totals[0] += percentage; // sum
            totals[1] += 1;          // count
        }

        List<Double> semesterAverages = new ArrayList<>();
        StringBuilder trail = new StringBuilder();
        for (Map.Entry<Integer, double[]> entry : semesterTotals.entrySet()) {
            double avg = entry.getValue()[0] / entry.getValue()[1];
            semesterAverages.add(avg);
            trail.append("Sem").append(entry.getKey()).append("=").append(String.format("%.2f", avg)).append(" ");
        }

        double first = semesterAverages.get(0);
        double last = semesterAverages.get(semesterAverages.size() - 1);

        String trend = (last > first) ? "UPWARD" : (last < first) ? "DOWNWARD" : "STABLE";

        context.write(new Text(key.getStudentId()), new Text(trend + " | " + trail.toString().trim()));
    }
}