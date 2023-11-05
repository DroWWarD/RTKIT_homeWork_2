package students.commands;

import students.DAO.DBHandler;

public class CommandDBClear implements CommandDB{
    @Override
    public void execute(DBHandler dbHandler, String command) {
        dbHandler.clearDB();
    }
}
