import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//toedit
public class Salesman extends Employee {
    private Integer overtimeHours;
    private static float overtimeRate = 40;

    private static List<Salesman> extent = new ArrayList<>(); //Ekstensja

    //Konstruktor
    public Salesman(LocalDate empDate, float salary, Integer overtimeHours,Person person) {
        super(empDate, salary,person);
        this.overtimeHours = overtimeHours;
        addSalesman(this);
    }

    //Ekstensja
    private void addSalesman(Salesman salesman){
        extent.add(salesman);
    }
    public void removeSalesman(){
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Salesman.class.getName());

        for (Salesman salesman : extent) {
            System.out.println(salesman);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Salesman>) stream.readObject();
    }

    //Gettery
    public Integer getOvertimeHours() {
        return overtimeHours;
    }

    @Override
    public String getRole() {
        return super.getRole()+"(Salesman)";
    }

    @Override
    public float getIncome() {
        return getSalary() + overtimeHours*overtimeRate;
    }

    @Override
    public String toString() {
        String info = super.toString();
        info += "overtimeHours: " + overtimeHours +"\n";

        return info;
    }
}
