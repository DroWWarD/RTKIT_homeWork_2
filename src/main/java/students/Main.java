package students;

import students.services.StudentService;
import students.dataLoaders.DataLoaderFile;

import java.io.*;

public class Main {
    private static final String studentsFile = "src/main/resources/students.csv";

    public static void main(String[] args) throws IOException {
        CommandBuilder commandBuilder = new CommandBuilder(new StudentService(new DataLoaderFile(new FileReader(studentsFile))));
        commandBuilder.start();
    }

    public static String getFile(){
        return studentsFile;
    }

}
