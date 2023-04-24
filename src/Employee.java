import java.io.Serializable;
import java.time.LocalDate;

abstract class Employee extends Roles implements Serializable {
    private LocalDate empDate;
    private float sallary;

    static String roleNameEmployee = "specializationEmployee";


    public Employee(LocalDate empDate, float sallary,Person person) {
        super(person);
        this.empDate = empDate;
        this.sallary = sallary;
    }


    public LocalDate getEmpDate() {
        return empDate;
    }
    public float getSalary() {
        return sallary;
    }
    public void addPerson(){}

    public void changeRole(Integer overtimeHours) throws Exception {
        Person person = this.getPerson();
        if(person.hasOvertimeHours()!= null){
            throw new Exception("That Employee is already a Salesman!");
        }else {
            Employee e = new Salesman(this.empDate,this.sallary,overtimeHours,person);
            try {
                person.setRole(roleNameEmployee,e);
                ((Manager)this).removeManager();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void changeRole(Float supSuplement) throws Exception {
        Person person = this.getPerson();
        if(person.hasSupSuplement()!= null){
            throw new Exception("That Employee is already a Manager!");
        }else {
            Employee e = new Manager(this.empDate,this.sallary,supSuplement,person);
            try {
                person.setRole(roleNameEmployee,e);
                ((Salesman)this).removeSalesman();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }



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
        info += "sallary: " + sallary + "\n";
        info += "income: " + getIncome() + "\n";

        return info;
    }

    public abstract float getIncome();
}
