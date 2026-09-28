import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class StudentPartitioner extends Partitioner<StudentSemesterKey, Text> {
    public int getPartition(StudentSemesterKey key, Text value, int numPartitions) {
        // partition ONLY on studentId, ignoring semester
        // this guarantees all of one student's records go to the SAME reducer
        return (key.getStudentId().hashCode() & Integer.MAX_VALUE) % numPartitions;
    }
}