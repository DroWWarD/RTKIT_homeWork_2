package students.dataLoaders;

import students.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class DataLoaderFile implements DataLoader {
    private Student[] studentList = new Student[1000];
    private final Reader reader;

    public DataLoaderFile(Reader reader) {
        this.reader = reader;
    }

    @Override
    public Student[] loadData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        int position = 0;
        while (bufferedReader.ready()){
            if (position >= studentList.length){
                listResize(studentList.length * 2, studentList.length);
            }
            Student student = readLineAndCreateStudent(bufferedReader);
            if (student != null){
                studentList[position] = student;
                position++;
            }
        }
        bufferedReader.close();
        listResize(position, position);
        return studentList;
    }

    private void listResize(int i, int i1) {
        Student[] newStudentList = new Student[i];
        System.arraycopy(studentList, 0, newStudentList, 0, i1);
        studentList = newStudentList;
    }

    private Student readLineAndCreateStudent(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        String[] splittedLine = line.split(";");
        try {
            return new Student(splittedLine[0], splittedLine[1],
                    Integer.parseInt(splittedLine[2]), Integer.parseInt(splittedLine[3]),
                    Integer.parseInt(splittedLine[4]),Integer.parseInt(splittedLine[5]),
                    Integer.parseInt(splittedLine[6]),Integer.parseInt(splittedLine[7]),
                    Integer.parseInt(splittedLine[8]),Integer.parseInt(splittedLine[9]));
        } catch (Exception e) {
            return null;
        }
    }
}
