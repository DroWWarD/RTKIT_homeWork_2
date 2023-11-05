package students.commands;

import students.services.StudentService;

public interface Command {
    void execute(StudentService studentService, String command);

}
