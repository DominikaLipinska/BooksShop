import java.time.LocalDate;

public class Manager extends Employee{
    public Float salSupplement;

    public Manager(LocalDate empDate, float salary,Float salSupplement) {
        super(empDate, salary);
        this.salSupplement = salSupplement;
    }

    //geterry
    public Float getSalSupplement() {
        return salSupplement;
    }

    @Override
    public float getIncome() {
        return getSalary()+salSupplement;
    }
}
