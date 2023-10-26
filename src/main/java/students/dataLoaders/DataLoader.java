package students.dataLoaders;

import students.Student;

import java.io.IOException;

public interface DataLoader {
    public Student[] loadData() throws IOException;
}
