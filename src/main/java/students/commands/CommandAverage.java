package students.commands;

import students.*;
import students.services.StudentService;
import students.utilities.isIntegerTest;

public class CommandAverage implements Command {

    @Override
    public void execute(StudentService studentService, String command) {
        GroupCriterion groupCriterion = Student::getGroup;
        DataGroup dataGroup = new DataGroup(studentService, groupCriterion);
        String[] splittedCommand = command.split(" ");
            for (int i = 1; i < splittedCommand.length; i++) {
                if (!isIntegerTest.isInteger(splittedCommand[i])){
                    continue;
                }
                Student[] studentsInGroup = dataGroup.getStudents(Integer.parseInt(splittedCommand[i]));
                if (studentsInGroup.length == 0){
                    continue;
                }
                int sum = 0;
                for (Student student : studentsInGroup) {
                    sum += student.getPhysics() + student.getMathematics() + student.getRus() + student.getLiterature() +
                            student.getGeometry() + student.getInformatics();
                }
                double averageGrade = (double) sum / (double) 6 / studentsInGroup.length;
                String formattedAverage = String.format("%.2f", averageGrade);
                System.out.println("Средняя оценка по всем дисциплинам среди учеников " + splittedCommand[i] + "ого класса = " + formattedAverage);
            }
    }
}
