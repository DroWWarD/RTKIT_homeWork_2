package students.commands;

import students.*;
import students.services.StudentService;
import students.utilities.StringAnalyst;

public class CommandExcellentElderAge implements Command {
    @Override
    public void execute(StudentService studentService, String command) {
        GroupCriterion groupCriterion = Student::getAge;
        DataGroup dataGroup = new DataGroup(studentService, groupCriterion);
        String[] splittedCommand = command.split(" ");
        if (!StringAnalyst.isInteger(splittedCommand[1])){
            System.out.println("Возраст введен некорректно");
            return;
        }
        int minAge = Integer.parseInt(splittedCommand[1]) +1;
        for (int i = minAge; i < 100; i++) {
            Student[] studentsInGroup = dataGroup.getStudents(i);
            for (Student student : studentsInGroup) {
                if (student.getPhysics() == 5 && student.getMathematics() == 5 &&
                student.getRus() == 5 && student.getLiterature() == 5 &&
                student.getGeometry() == 5 && student.getInformatics() == 5){
                    System.out.println(student);
                }
            }
        }
    }
}
