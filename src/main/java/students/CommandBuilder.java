package students;

import students.commands.CommandAverage;
import students.commands.CommandExcellentElderAge;
import students.commands.CommandHelp;
import students.commands.CommandSearchFamily;
import students.services.StudentService;

import java.util.Scanner;

public class CommandBuilder {
    private final StudentService studentService;
    private final String welcome = "Введите команду! (что бы посмотреть все доступные - воспользуйтесь командой \"help\")";

    public CommandBuilder(StudentService studentService) {
        this.studentService = studentService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcome);
        String command = "";
        while (!(command = scanner.nextLine()).equals("exit")){
            String[] commandLine = command.split(" ");
            switch (commandLine[0]) {
                case "help" -> new CommandHelp().execute(studentService, "");
                case "average" -> new CommandAverage().execute(studentService, command);
                case "excellent" -> new CommandExcellentElderAge().execute(studentService, command);
                case "search" -> new CommandSearchFamily().execute(studentService, command);
            }
        }
    }
}
