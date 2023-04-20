import java.time.LocalDate;

abstract class Employee extends ObjectPlusPlus {
    private LocalDate empDate;
    private float salary;

    static String roleNameGeneralization = "generalization";
    static String roleNameEmployee = "specializationEmployee";


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
    public Person getPerson() throws Exception{
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameGeneralization);
            return  (Person)obj[0];
        }catch (Exception e){
            throw new Exception("The object ist not person");
        }
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
