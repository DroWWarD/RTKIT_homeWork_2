package students;

import students.DAO.DBHandler;
import students.commands.*;
import students.services.StudentService;

import java.util.Scanner;

public class CommandBuilder {
    private final StudentService studentService;
    private final String welcome = "Главное меню ввода команд (что бы посмотреть все доступные - воспользуйтесь командой \"help\")";

    public CommandBuilder(StudentService studentService){
        this.studentService = studentService;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcome);
        String command;
        while (!(command = scanner.nextLine()).equals("exit")){
            String[] commandLine = command.split(" ");
            switch (commandLine[0]) {
                case "help" -> new CommandHelp().execute(studentService, "");
                case "average" -> new CommandAverage().execute(studentService, command);
                case "excellent" -> new CommandExcellentElderAge().execute(studentService, command);
                case "search" -> new CommandSearchFamily().execute(studentService, command);
                case "db" -> switchToDBCommands();
            }
        }
    }

    private void switchToDBCommands() {
        Scanner scanner = new Scanner(System.in);
        String welcomeDB = "Меню ввода команд для работы с БД (что бы посмотреть все доступные - воспользуйтесь командой \"help\")";
        System.out.println(welcomeDB);
        String command;
        while (!(command = scanner.nextLine()).equals("exit")){
            String[] commandLine = command.split(" ");
            switch (commandLine[0]) {
                case "help" -> new CommandDBHelp().execute(studentService, "");
                case "clear" -> new CommandDBClear().execute(new DBHandler(), "");
                case "load" -> new CommandDBLoad().execute(new DBHandler(), "");
                case "average" -> new CommandDBAverage().execute(new DBHandler(), command);
                case "excellent" -> new CommandDBExcellent().execute(new DBHandler(), command);
                case "search" -> new CommandDBSearch().execute(new DBHandler(), command);
            }
        }
        System.out.println(welcome);
    }

}
