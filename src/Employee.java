import java.io.Serializable;
import java.time.LocalDate;

abstract class Employee extends Roles implements Serializable {
    private LocalDate empDate;
    private float sallary;
    private Shift shift;

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
    public Shift getShift(){ return shift;}

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

    public void addShift(Shift shift) throws Exception {
        if(this.shift!=null){
            throw new Exception("This employee already have shift");
        }
        this.shift = shift;
    }
    public Shift changeShift(Shift newShift) throws Exception {
        Shift prev = this.shift;
        if(this.shift==null){
            throw new Exception("This employee not have shift to change");
        }
        this.shift = shift;
        return prev;
    }
    public Shift removeShift() {
        Shift prev = this.shift;
        this.shift = null;
        return prev;
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
