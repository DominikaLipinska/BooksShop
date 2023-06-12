import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//toedit
public class Shift implements Serializable {
    private String name;
    private List<Employee> employees = new ArrayList<>(); //Asocjacja Shift -> Employee (1-*)
    private static int minEmpNum = 2;
    private Manager manager; //Asocjacja Manager -> Shift(1-1)

    private static List<Shift> extent = new ArrayList<>(); //Ekstensja

    public Shift(String name, List<Employee> employees, Manager manager) throws Exception {
        if(!enoughEmp(employees)){
            throw new Exception("Not enough workers");
        }else {
            this.employees.addAll(employees);
            for (Employee emp:employees) {
                emp.addShift(this);
            }
            if (!canManage(manager)) {
            throw new Exception("This manager cannot manage a shift he is not working on");
            } else {
            this.name = name;
            this.manager = manager;
            this.manager.addManageShift(this);
            addShift(this);
        }}
    }

    //Asocjacja Shift -> Employee (1-*)
    private boolean enoughEmp(List<Employee> employees){
        return employees.size() >= minEmpNum;
    }
    public void addEmp(Employee employee) throws Exception {
        if(!employees.contains(employee)){
            employees.add(employee);
            employee.addShift(this);
        }
    }
    public void removeEmp(Employee employee) throws Exception {
        if(!employees.contains(employee)){
            throw new Exception("This employee not working on ths shift");
        }else if(employees.size()-1 < minEmpNum){
            throw new Exception("Cannot remove employe! On shift have to be at least " + minEmpNum);
        }else if(employee.equals(manager)){
            throw new Exception("This employee manage this shift! Change manager before removing this employee");
        }else{
            employees.remove(employee);
            employee.removeShift();
        }
    }

    //Asocjacja Manager -> Shift(1-1)
    public void changeManager(Manager newManager) throws Exception {
        if(!manager.equals(newManager)){
            if(!canManage(newManager)){
                throw new Exception("This manager cannot manage a shift he is not working on");
            }
            manager.removeManageShift();
            this.manager = newManager;
            newManager.addManageShift(this);
        }
    }
    private boolean canManage(Manager manager){
        return employees.contains(manager);
    }

    //Ekstensja
    private void addShift(Shift shift){
        extent.add(shift);
    }
    public void removeShift(){
        manager.removeShift();
        while (!employees.isEmpty()){
            employees.get(0).removeShift();
            employees.remove(employees.get(0));
        }
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Shift.class.getName());

        for (Shift shift : extent) {
            System.out.println(shift);
        }
    }

    //Gettery
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String info = "shift: "+name + "\nmanager: \n"+manager+"\nemployees:\n";
        for (Employee employe:employees) {
            info += employe +"\n";
        }
        return info;
    }
}
