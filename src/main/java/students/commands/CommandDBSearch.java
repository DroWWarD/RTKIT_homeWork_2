package students.commands;

import students.DAO.DBHandler;
import students.model.Student;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class CommandDBSearch implements CommandDB {
    @Override
    public void execute(DBHandler dbHandler, String command) {
        String family = command.substring(command.indexOf(" ") + 1);
        List<Student> resultList = dbHandler.getStudentsByFamily(family);
        for (Student student : resultList) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(student.getFamily()).append(" ").append(student.getName()).append(" ").append(student.getGroup().getName()).append(" ");
            Integer[] gradesList = student.getProgress().getGrades();
            OptionalDouble optionalDouble = Arrays.stream(gradesList).mapToInt(e -> e).average();
            if (optionalDouble.isPresent()){
                String formattedAverage = String.format("%.2f", optionalDouble.getAsDouble());
                stringBuilder.append(formattedAverage);
            }
            System.out.println(stringBuilder);
        }

    }
}
