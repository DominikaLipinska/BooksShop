import java.util.ArrayList;
import java.util.List;

public class Vacation {
    private String name;
    private List<Employee> employees = new ArrayList<>();//Asocjacja Vacation -> Employee (1-*)

    public Vacation(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    @Override
    public String toString() {
        return "Vacation: " + name ;
    }
}
