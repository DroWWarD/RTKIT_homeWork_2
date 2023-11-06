package students.commands;

import students.DAO.DBHandler;
import students.model.Student;
import students.utilities.StringAnalyst;

import java.util.List;

public class CommandDBExcellent implements CommandDB{
    @Override
    public void execute(DBHandler dbHandler, String command) {
        String age = command.substring(command.indexOf(" ") + 1);
        if (StringAnalyst.isInteger(age)){
            List<Student> resultList = dbHandler.getExcellentElder(Integer.parseInt(age));
            System.out.println("Список отличников старше " + age + " лет:");
            resultList.forEach(System.out::println);
        }
    }
}
