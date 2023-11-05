package students.model;

public class Group {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudPlan getStudPlan() {
        return studPlan;
    }

    public void setStudPlan(StudPlan studPlan) {
        this.studPlan = studPlan;
    }

    private String name;
    private StudPlan studPlan;
}
