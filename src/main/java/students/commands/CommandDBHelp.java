package students.commands;

import students.services.StudentService;

public class CommandDBHelp implements Command{
    @Override
    public void execute(StudentService studentService, String command) {
        String help = """
                --- Команда ---||---------------------- Результат выполнения -----------------------
                help           || Выводит в консоль данное сообщение с перечнем доступных команд
                clear          || Удаляет из базы все записи
                load           || Загружает в базу данные из файла students.csv
                average 10 11  || Выводит в консоль среднюю оценку в перечисленных классах (10 и 11)
                excellent 14   || Поиск всех отличников, старше указанного возраста (14)
                search family  || Выводит в консоль список учеников с указанной фамилией (family) и их средние оценки
                exit           || Выход из режима работы с базой данных, возврат к предыдущему меню ввода команд
                """;
        System.out.println(help);
    }
}
