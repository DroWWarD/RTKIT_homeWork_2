package students.commands;

import students.DAO.DBHandler;

public class CommandDBLoad implements CommandDB{
    @Override
    public void execute(DBHandler dbHandler, String command) {
        dbHandler.loadDataFromFile();
    }
}
