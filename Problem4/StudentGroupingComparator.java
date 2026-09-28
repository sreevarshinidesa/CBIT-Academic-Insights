import org.apache.hadoop.io.WritableComparator;

public class StudentGroupingComparator extends WritableComparator {
    protected StudentGroupingComparator() {
        super(StudentSemesterKey.class, true);
    }

    public int compare(org.apache.hadoop.io.WritableComparable a, org.apache.hadoop.io.WritableComparable b) {
        StudentSemesterKey key1 = (StudentSemesterKey) a;
        StudentSemesterKey key2 = (StudentSemesterKey) b;
        // group ONLY by studentId — ignore semester
        return key1.getStudentId().compareTo(key2.getStudentId());
    }
}