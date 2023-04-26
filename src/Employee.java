import enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

abstract class Employee extends Roles implements Serializable {
    private LocalDate empDate;
    private float sallary;
    private Shift shift;
    private  static List<Order> allSubmitOrders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)
    private  static List<Order> allProcessedOrders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)
    private  static List<Order> historyOrders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)
    private List<Order> orders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)

    static String roleNameEmployee = "specializationEmployee";


    public Employee(LocalDate empDate, float sallary,Person person) {
        super(person);
        this.empDate = empDate;
        this.sallary = sallary;
    }

    public static void showAllOrdersLists() {
        System.out.println("Submit Orders: ");
        if(allSubmitOrders.isEmpty()){
            System.out.println("Not have orders");
        }else {
            for (Order order : allSubmitOrders) {
                System.out.println(order);
            }
        }
        System.out.println("Progress Orders: ");
        if(allProcessedOrders.isEmpty()){
            System.out.println("Not have orders");
        }else {
            for (Order order : allProcessedOrders) {
                System.out.println(order);
            }
        }
        System.out.println("History Orders: ");
        if(historyOrders.isEmpty()){
            System.out.println("Not have orders");
        }else {
            for (Order order : historyOrders) {
                System.out.println(order);
            }
        }
    }



    public LocalDate getEmpDate() {
        return empDate;
    }
    public float getSalary() {
        return sallary;
    }
    public Shift getShift(){ return shift;}

    public void getOrders() {
        System.out.println("Orders: ");
        if(orders.isEmpty()){
            System.out.println("Not have orders");
        }else {
            for (Order order:orders) {
                System.out.println(order);
            }
        }
    }

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

    //Asocjacja Employee -> Order (1-*)
    public static void addOrder(Order order){
        if(order.checkStatus(OrderStatus.SUBMITED)){
            if(!allSubmitOrders.contains(order)){
                allSubmitOrders.add(order);
            }
        }else if(order.checkStatus(OrderStatus.PROGRESSED)){
            if(!allProcessedOrders.contains(order)){
                allProcessedOrders.add(order);
            }
        }else {
            if(order.getStatus()!=null){
                if(!historyOrders.contains(order)){
                    historyOrders.add(order);
                }
            }
        }
    }
    public static void removeOrder(Order order){
        if(allSubmitOrders.contains(order)){
            allSubmitOrders.remove(order);
        }else if(allProcessedOrders.contains(order)){
            allProcessedOrders.remove(order);
        }else if(historyOrders.contains(order)){
            historyOrders.remove(order);
        }
    }
    public Order takeOrder() throws Exception {
        if(!allSubmitOrders.isEmpty()){
            Order order = allSubmitOrders.get(0);
            orders.add(order);
            removeOrder(order);
            order.setStatus(OrderStatus.PROGRESSED);
            addOrder(order);
            return order;
        }else {
            throw new Exception("Not have orders to take");
        }
    }
    public void completeOrder() throws Exception {
        if(!orders.isEmpty()){
            Order order = orders.get(0);
            removeOrder(order);
            order.setStatus(OrderStatus.FINISHED);
            addOrder(order);
            orders.remove(order);
            order.getClient().moveOrder(order);
        }else {
            throw new Exception("Not have orders to complete");
        }
    }
    public void cancelOrder(Order order) throws Exception {
        if(orders.contains(order)){
            removeOrder(order);
            order.setStatus(OrderStatus.CANCELLED);
            addOrder(order);
            orders.remove(order);
            order.getClient().moveOrder(order);
        }else {
            throw new Exception("Not have orders to cancel");
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
