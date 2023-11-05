package students.commands;

import students.services.StudentService;

public class CommandHelp implements Command {
    @Override
    public void execute(StudentService studentService, String command) {
        String help = """
                --- Команда ---||---------------------- Результат выполнения -----------------------
                help           || Выводит в консоль данное сообщение с перечнем доступных команд
                average 10 11  || Выводит в консоль среднюю оценку в перечисленных классах (10 и 11)
                excellent 14   || Поиск всех отличников, старше указанного возраста (14)
                search family  || Поиск ученика по указанной фамилии (family)
                db             || Переход в меню ввода команд для работы с базой данных
                exit           || Прерывает выполнение программы
                """;
        System.out.println(help);
    }
}
