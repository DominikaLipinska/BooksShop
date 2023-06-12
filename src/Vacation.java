import java.util.ArrayList;
import java.util.List;
//toedit
public class Vacation {
    private String name;
    private List<Employee> employees = new ArrayList<>();//Asocjacja Vacation -> Employee (1-*)

    private static List<Vacation> extent = new ArrayList<>();

    //Konstruktor
    public Vacation(String name) {
        this.name = name;
    }

    //Asocjacja Vacation -> Employee (1-*)
    public void addEmployee(Employee employee){
        employees.add(employee);
    }
    public void removeEmployee(Employee employee){
        if(employees.contains(employee)){
            employees.remove(employee);
        }
    }

    //Ekstensja
    private void addVacation(Vacation vacation){
        extent.add(vacation);
    }
    public void removeVacation() throws Exception {
        while (!employees.isEmpty()){
            employees.get(0).removeVacation();
            employees.remove(employees.get(0));
        }
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Workshop.class.getName());

        for (Vacation vacation : extent) {
            System.out.println(vacation);
        }
    }

    @Override
    public String toString() {
        return "Vacation: " + name ;
    }
}
