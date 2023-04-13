import java.time.LocalDate;

public class Salesman extends Employee{
    private int overtimeHour;
    private static float overtimeRate = 40;

    public Salesman(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate, float salary) {
        super(firstName, latsName, phoneNumber, email, adres, empDate, salary);
    }

    public Salesman(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate, float salary, int overtimeHour) {
        this(firstName, latsName, phoneNumber, email, adres, empDate, salary);
        this.overtimeHour = overtimeHour;
    }

    @Override
    public float getIncome() {
        return getSalary() + overtimeHour*overtimeRate;
    }
}
