package students.commands;

import students.services.StudentService;

public interface Command {
    public void execute(StudentService studentService, String command);
}
