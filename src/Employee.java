import java.time.LocalDate;

abstract class Employee extends ObjectPlusPlus {
    private LocalDate empDate;
    private float salary;

    public Employee(LocalDate empDate, float salary) {
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
