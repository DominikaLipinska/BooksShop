import java.time.LocalDate;

public class Manager extends Employee{
    public Float salSupplement;

    public Manager(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate, float salary) {
        super(firstName, latsName, phoneNumber, email, adres, empDate, salary);
    }

    public Manager(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate, float salary,float salSupplement) {
        this(firstName, latsName, phoneNumber, email, adres, empDate, salary);
        this.salSupplement = salSupplement;
    }

    @Override
    public float getIncome() {
        return getSalary()+salSupplement;
    }
}
