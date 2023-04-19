import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee{
    public Float salSupplement;


    public Manager(LocalDate empDate, float salary,Float salSupplement) {
        super(empDate, salary);
        this.salSupplement = salSupplement;
        addManager(this);
    }

    public Manager(Employee prevEmployee,Float salSupplement){
        super(prevEmployee.getEmpDate(),prevEmployee.getSalary());
        this.salSupplement = salSupplement;
        if(prevEmployee.getClass()==Salesman.class){
            ((Salesman)prevEmployee).removeSalesman();
        }
        try {
            prevEmployee.getPerson().addLink(roleNameEmployee,roleNameGeneralization,this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        addManager(this);
    }

    private static List<Manager> extent = new ArrayList<>(); //Ekstensja

    //Ekstensja
    private void addManager(Manager manager){
        extent.add(manager);
    }
    public void removeManager(){
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Manager.class.getName());

        for (Manager manager: extent) {
            System.out.println(manager);
        }
    }

    //geterry
    public Float getSalSupplement() {
        return salSupplement;
    }

    //Ekstensja Trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Manager>) stream.readObject();
    }


    @Override
    public float getIncome() {
        return getSalary()+salSupplement;
    }

    @Override
    public String toString() {
        String info = super.toString();
        info += "salSuplement: " + salSupplement +"\n";

        return info;
    }
}
