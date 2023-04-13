import java.time.LocalDate;

public class Salesman extends Employee{
    private Float overtimeSal;

    public Salesman(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate, float salary) {
        super(firstName, latsName, phoneNumber, email, adres, empDate, salary);
    }

    public Salesman(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate, float salary, float overtimeSal) {
        this(firstName, latsName, phoneNumber, email, adres, empDate, salary);
        this.overtimeSal = overtimeSal;
    }

    @Override
    public float getIncome() {
        return getSalary() + overtimeSal;
    }
}
