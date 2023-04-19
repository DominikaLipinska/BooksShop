import java.time.LocalDate;

public class Salesman extends Employee{
    private Integer overtimeHours;
    private static float overtimeRate = 40;

    public Salesman(LocalDate empDate, float salary, Integer overtimeHours) {
        super(empDate, salary);
        this.overtimeHours = overtimeHours;
    }

    //Gettery
    public Integer getOvertimeHours() {
        return overtimeHours;
    }

    @Override
    public float getIncome() {
        return getSalary() + overtimeHours*overtimeRate;
    }
}
