package students.commands;

import students.DataGroup;
import students.GroupCriterion;
import students.Student;
import students.services.StudentService;

public class CommandSearchFamily implements Command {
    @Override
    public void execute(StudentService studentService, String command) {
        GroupCriterion groupCriterion = student -> student.getFamily().charAt(0) - 1025;
        DataGroup dataGroup = new DataGroup(studentService, groupCriterion);
        String[] splittedCommand = command.split(" ");
        String familyForSearch = splittedCommand[1];
        Student[] studentsInGroup = dataGroup.getStudents(familyForSearch.charAt(0) - 1025);
        for (Student student : studentsInGroup) {
            if (student.getFamily().equals(familyForSearch)){
                System.out.println(student);
            }
        }
    }
}
