import java.time.LocalDate;

abstract class Employee extends Person {
    private LocalDate empDate;
    private float salary;

    public Employee(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate, float salary) {
        super(firstName, latsName, phoneNumber, email, adres);
        this.empDate = empDate;
        this.salary = salary;
    }

    public LocalDate getEmpDate() {
        return empDate;
    }

    public float getSalary() {
        return salary;
    }



    public abstract float getIncome();
}
