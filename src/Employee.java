import java.io.Serializable;
import java.time.LocalDate;

abstract class Employee extends Roles implements Serializable {
    private LocalDate empDate;
    private float salary;

    static String roleNameGeneralization = "generalization";
    static String roleNameEmployee = "specializationEmployee";


    public Employee(LocalDate empDate, float salary,Person person) {
        super(person);
        this.empDate = empDate;
        this.salary = salary;
    }


    public LocalDate getEmpDate() {
        return empDate;
    }
    public float getSalary() {
        return salary;
    }
    public void addPerson(){}


    @Override
    public String getRole() {
        return Employee.class.getName();
    }

    @Override
    public String toString() {
        String info ="";
        try {
            info+= getPerson();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        info += "empDate: " + empDate + "\n";
        info += "salary: " + salary + "\n";
        info += "income: " + getIncome() + "\n";

        return info;
    }

    public abstract float getIncome();
}
