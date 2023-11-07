package students.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import students.DAO.DBHandler;
import students.responses.Error;
import students.responses.StudentWithAverageGrades;
import students.model.Student;
import students.utilities.StringAnalyst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@WebServlet(urlPatterns = {"/*"})
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer output = null;
        try {
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8");
            output = resp.getWriter();
            String group = req.getParameter("group");
            int limit = StringAnalyst.isInteger(req.getParameter("limit")) ? Integer.parseInt(req.getParameter("limit")) : 0;
            int offset = StringAnalyst.isInteger(req.getParameter("offset")) ? Integer.parseInt(req.getParameter("offset")) : 0;
            List<Student> studentsByGroup = new DBHandler().getStudentsByGroup(group, limit, offset);
            List<StudentWithAverageGrades> resultList = new ArrayList<>();
            for (Student stud : studentsByGroup) {
                resultList.add(createObjectForResponse(stud));
            }
            ObjectMapper mapper = new ObjectMapper();
            resp.setContentType("application/string");
            output.write(mapper.writeValueAsString(resultList));
            output.flush();
        } catch (Exception e) {
            assert output != null;
            output.write(new ObjectMapper().writeValueAsString(new Error(404, e.getMessage())));
        } finally {
            assert output != null;
            output.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer output = null;
        try {
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8");
            output = resp.getWriter();
            if (StringAnalyst.isInteger(req.getParameter("grade"))){
                new DBHandler().setNewGrade(req.getParameter("family"), req.getParameter("name"),
                        req.getParameter("group"), req.getParameter("subject"), Integer.parseInt(req.getParameter("grade")));
            }
        } catch (Exception e) {
            assert output != null;
            output.write(new ObjectMapper().writeValueAsString(new Error(404, e.getMessage())));
        }finally {
            assert output != null;
            output.close();
        }
    }

    private StudentWithAverageGrades createObjectForResponse(Student stud) {
        OptionalDouble optionalDouble = Arrays.stream(stud.getProgress().getGrades()).mapToInt(e -> e).average();
        double average = optionalDouble.orElse(0);
        double scale = Math.pow(10, 2);
        double formattedAverage = Math.ceil(average * scale) / scale;
        return new StudentWithAverageGrades(stud.getFamily(), stud.getName(),
                stud.getGroup().getName(), formattedAverage);
    }
}
