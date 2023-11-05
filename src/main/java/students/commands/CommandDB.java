package students.commands;

import students.DAO.DBHandler;

public interface CommandDB {
    void execute(DBHandler dbHandler, String command);
}
