package students.commands;

import students.services.StudentService;

public class CommandHelp implements Command {
    private final String help = """
            --- Команда ---||---------------------- Результат выполнения -----------------------
            help           || Выводит в консоль данное сообщение с перечнем доступных команд
            average 10 11  || Выводит в консоль среднюю оценку в перечисленных классах (10 и 11)
            excellent 14   || Поиск всех отличников, старше указанного возраста (14)
            search family  || Поиск ученика по указанной фамилии (family)
            exit           || Прерывает выполнение программы
            """;
    @Override
    public void execute(StudentService studentService, String command) {
        System.out.println(help);
    }
}
