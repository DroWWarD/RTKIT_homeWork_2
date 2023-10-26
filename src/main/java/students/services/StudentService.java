package students.services;

import students.dataLoaders.DataLoader;
import students.Student;

import java.io.IOException;

public class StudentService {
    private final Student[] studentList;

    public StudentService(DataLoader dataLoader) throws IOException {
        this.studentList = dataLoader.loadData();
    }
    public Student[] getStudentList() {
        return studentList;
    }
}
