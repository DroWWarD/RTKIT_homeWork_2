package students.responses;


public class StudentWithAverageGrades {
    private String family;
    private String name;
    private String group;
    private double average;

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public StudentWithAverageGrades(String family, String name, String group, double average) {
        this.family = family;
        this.name = name;
        this.group = group;
        this.average = average;
    }
}
