package students.commands;

import students.DAO.DBHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class CommandDBAverage implements CommandDB {
    @Override
    public void execute(DBHandler dbHandler, String command) {
        String[] splittedCommand = command.split(" ");
        List<String> groups = new ArrayList<>(Arrays.asList(splittedCommand).subList(1, splittedCommand.length));
        List<Integer> gradesList = dbHandler.getGradesByGroups(groups);
        OptionalDouble optionalDouble = gradesList.stream().mapToInt(e -> e).average();
        if (optionalDouble.isPresent()){
            String formattedAverage = String.format("%.2f", optionalDouble.getAsDouble());
            StringBuilder groupsToString = new StringBuilder();
            for (String s:groups) {
                groupsToString.append(s).append(" ");
            }
            System.out.println("Средняя оценка по всем дисциплинам среди учеников групп (если такие найдены)" + groupsToString + " = " + formattedAverage);
        }
    }
}