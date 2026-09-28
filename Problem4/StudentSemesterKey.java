import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;

public class StudentSemesterKey implements WritableComparable<StudentSemesterKey> {
    private String studentId;
    private int semester;

    public StudentSemesterKey() {}

    public StudentSemesterKey(String studentId, int semester) {
        this.studentId = studentId;
        this.semester = semester;
    }

    public String getStudentId() { return studentId; }
    public int getSemester() { return semester; }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(studentId);
        out.writeInt(semester);
    }

    public void readFields(DataInput in) throws IOException {
        studentId = in.readUTF();
        semester = in.readInt();
    }

    public int compareTo(StudentSemesterKey other) {
        int cmp = this.studentId.compareTo(other.studentId);
        if (cmp != 0) return cmp;
        return Integer.compare(this.semester, other.semester);
    }

    public String toString() {
        return studentId + "," + semester;
    }
}