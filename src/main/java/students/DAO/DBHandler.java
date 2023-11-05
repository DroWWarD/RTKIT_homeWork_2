package students.DAO;

import students.Exceptions.DataFormatException;
import students.Main;
import students.model.Group;
import students.model.Progress;
import students.model.Student;
import students.utilities.StringAnalyst;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;

public class DBHandler {
    private Connection connection;

    public DBHandler() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            String URL = "jdbc:postgresql://localhost:5432/StudProgress";
            String USER = "postgres";
            String PASSWORD = "26499462";
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            if (connection == null) {
                System.err.println("Could not connect to database");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void clearDB() {
        System.out.println("Запущен процесс удаления всех данных из базы. Подождите, процесс может занять несколько минут");
        try {
            try {
                Statement statement = connection.createStatement();
                statement.execute("DELETE FROM student");
                statement.execute("DELETE FROM progress");
                statement.execute("DELETE FROM stud_group");
                statement.execute("DELETE FROM stud_plan");
                connection.commit();
                System.out.println("Из базы данных удалены все записи");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Не удалось удалить данные из базы");
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromFile() {
        System.out.println("Запущен процесс добавления данных в базу данных. Подождите, процесс может занять несколько минут");
        try {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Main.getFile()))) {
                int studPlanPK = addStudPlan(bufferedReader.readLine());
                while (bufferedReader.ready()) {
                    addDataToDB(studPlanPK, bufferedReader.readLine());
                }
                connection.commit();
                System.out.println("Данные из файла добавлены в базу данных");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException | DataFormatException e) {
                System.out.println(e.getMessage());
                connection.rollback();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToDB(int studPlanPK, String rowFromFile) throws SQLException, DataFormatException {
        String[] dataToInsert = rowFromFile.split(";");
        String groupName = dataToInsert[3];
        int groupPK = getGroupId(groupName);
        if (groupPK == -1) {
            groupPK = addGroup(groupName, studPlanPK);
        }
        int progressPK = addProgress(dataToInsert);
        addStudent(dataToInsert, groupPK, progressPK);
    }

    private void addStudent(String[] dataToInsert, int groupPK, int progressPK) throws SQLException, DataFormatException {
        PreparedStatement addStudent;
        if (!StringAnalyst.isInteger(dataToInsert[2])) {
            throw new DataFormatException("Некорректное заполнение поля age");
        }
        addStudent = connection.prepareStatement("INSERT INTO student (family,name,age,stud_group_id,progress_id) VALUES (?,?,?,?,?)");
        addStudent.setString(1, dataToInsert[0]);
        addStudent.setString(2, dataToInsert[1]);
        addStudent.setInt(3, Integer.parseInt(dataToInsert[2]));
        addStudent.setInt(4, groupPK);
        addStudent.setInt(5, progressPK);
        addStudent.execute();
    }

    private int addProgress(String[] dataToInsert) throws SQLException, DataFormatException {
        PreparedStatement addProgress;
        if (StringAnalyst.isInteger(dataToInsert[4]) && StringAnalyst.isInteger(dataToInsert[5]) && StringAnalyst.isInteger(dataToInsert[6]) &&
                StringAnalyst.isInteger(dataToInsert[7]) && StringAnalyst.isInteger(dataToInsert[8]) && StringAnalyst.isInteger(dataToInsert[9])) {
            Integer[] grades = new Integer[]{Integer.parseInt(dataToInsert[4]), Integer.parseInt(dataToInsert[5]), Integer.parseInt(dataToInsert[6]),
                    Integer.parseInt(dataToInsert[7]), Integer.parseInt(dataToInsert[8]), Integer.parseInt(dataToInsert[9])};
            Array gradesArray = connection.createArrayOf("integer", grades);
            addProgress = connection.prepareStatement("INSERT INTO progress (grades) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            addProgress.setArray(1, gradesArray);
            addProgress.execute();
            ResultSet auto_pk = addProgress.getGeneratedKeys();
            int groupPK = -1;
            if (auto_pk.next()) {
                return auto_pk.getInt("id");
            }
            return groupPK;
        } else {
            throw new DataFormatException("Ошибка заполнения полей оценок");
        }
    }

    private int addGroup(String groupName, int studPlanPK) throws SQLException {
        PreparedStatement addGroup;
        addGroup = connection.prepareStatement("INSERT INTO stud_group (name,stud_plan_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        addGroup.setString(1, groupName);
        addGroup.setInt(2, studPlanPK);
        addGroup.execute();
        ResultSet auto_pk_group = addGroup.getGeneratedKeys();
        int groupPK = -1;
        if (auto_pk_group.next()) {
            return auto_pk_group.getInt("id");
        }
        return groupPK;
    }

    private int getGroupId(String groupName) throws SQLException {
        PreparedStatement selectGroup;
        selectGroup = connection.prepareStatement("SELECT id FROM stud_group WHERE name like ?");
        selectGroup.setString(1, groupName);
        ResultSet resultSet = selectGroup.executeQuery();
        int groupId = -1;
        while (resultSet.next()) {
            groupId = resultSet.getInt("id");
        }
        return groupId;
    }

    private int addStudPlan(String firstLine) throws SQLException {
        String[] firstRow = firstLine.split(";");
        String[] studPlanObjects = new String[]{firstRow[4], firstRow[5], firstRow[6], firstRow[7], firstRow[8], firstRow[9]};
        PreparedStatement addStudPlan;
        Array arrayStudObjects = connection.createArrayOf("text", studPlanObjects);
        addStudPlan = connection.prepareStatement("INSERT INTO stud_plan (name,stud_objects) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        addStudPlan.setString(1, "Some_Stud_Plan");
        addStudPlan.setArray(2, arrayStudObjects);
        addStudPlan.execute();
        ResultSet auto_pk_studPlan = addStudPlan.getGeneratedKeys();
        int studPlanId = -1;
        while (auto_pk_studPlan.next()) {
            studPlanId = auto_pk_studPlan.getInt("id");
        }
        return studPlanId;
    }


    public List<Integer> getGradesByGroups(List<String> groups) {
        List<Integer> gradesList = new ArrayList<>();
        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT grades FROM progress
                        JOIN student ON progress.id=student.progress_id
                        JOIN stud_group ON stud_group.id=student.stud_group_id
                        WHERE stud_group.name LIKE ?
                        """);
                for (String group : groups) {
                    preparedStatement.setString(1, group);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Array array = resultSet.getArray("grades");
                        Integer[] arr = (Integer[]) array.getArray();
                        gradesList.addAll(Arrays.asList(arr));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Запрос не выполнен");
                connection.rollback();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gradesList;
    }

    public List<String> getExcellentElder(int age) {
        List<String> resultList = new ArrayList<>();
        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT family, name FROM student
                        JOIN progress ON progress.id=student.progress_id
                        WHERE 5 = ALL (progress.grades) AND student.age > ?
                        """);
                preparedStatement.setInt(1, age);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(resultSet.getString("family") + " " + resultSet.getString("name"));
                }
            } catch (SQLException e) {
                System.out.println("Запрос не выполнен");
                connection.rollback();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public List<Student> getStudentsByFamily(String family) {
        List<Student> resultList = new ArrayList<>();
        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("""
                        SELECT student.family, student.name, stud_group.name AS group_name, progress.grades
                        FROM student
                        JOIN progress ON progress.id=student.progress_id
                        JOIN stud_group ON stud_group.id=student.stud_group_id
                        WHERE student.family LIKE ?
                        """);
                preparedStatement.setString(1, family);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Progress progress = new Progress();
                    Array array = resultSet.getArray("grades");
                    progress.setGrades((Integer[]) array.getArray());
                    Group group = new Group();
                    group.setName(resultSet.getString("group_name"));
                    Student student = new Student();
                    student.setFamily(resultSet.getString("family"));
                    student.setName(resultSet.getString("name"));
                    student.setGroup(group);
                    student.setProgress(progress);
                    resultList.add(student);
                }
            } catch (SQLException e) {
                System.out.println("Запрос не выполнен");
                connection.rollback();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
