import enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//toedit
abstract class Employee extends Roles implements Serializable {
    private LocalDate empDate;
    private float sallary;
    private Shift shift;//Asocjacja Shift -> Employee (1-*)
    private static List<Order> allSubmitOrders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)
    private static List<Order> allProcessedOrders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)
    private static List<Order> historyOrders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)
    private List<Order> orders = new ArrayList<>();//Asocjacja Employee -> Order (1-*)
    private Vacation vacation;//Asocjacja Vacation -> Employee (1-*)
    protected boolean authorized = false;

    static String roleNameEmployee = "specializationEmployee";

    //Konstruktor
    public Employee(LocalDate empDate, float sallary,Person person) {
        super(person);
        this.empDate = empDate;
        this.sallary = sallary;
    }

    //Overlapping
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

    //Asocjacja Shift -> Employee (1-*)
    public void addShift(Shift shift) throws Exception {
        if(vacation!=null){
            throw new Exception("Employee on the vacation cannot working on any shift!");
        }else {
            if(this.shift!=null){
                throw new Exception("This employee already have shift");
            }
            this.shift = shift;
        }
    }
    public Shift changeShift(Shift newShift) throws Exception {
        Shift prev = this.shift;
        if(this.shift==null){
            addShift(newShift);
        }else {
            this.shift = shift;
        }
        return prev;
    }
    public Shift removeShift() {
        Shift prev = this.shift;
        this.shift = null;
        return prev;
    }

    //Asocjacja Employee -> Order (1-*)
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
    public Order getOrder(){
        Order order = orders.get(0);
        return order;
    }
    public void completeOrder(Order order) throws Exception {
        if(!orders.isEmpty()){
            removeOrder(order);
            order.setStatus(OrderStatus.FINISHED);
            addOrder(order);
            orders.remove(order);
            order.getClient().moveOrder(order);
        }else {
            throw new Exception("Not have orders to complete");
        }
    }

    //Ograniczenie własne
    public void cancelOrder(Order order) throws Exception {
        if(authorized) {
            if(orders.contains(order)){
                removeOrder(order);
                order.setStatus(OrderStatus.CANCELLED);
                addOrder(order);
                orders.remove(order);
                order.getClient().moveOrder(order);
            }else {
                throw new Exception("Not have orders to cancel");
            }
        }else {
            throw new Exception("You not have autorisation to cancel order!");
        }
    }

    //Asocjacja Vacation -> Employee (1-*)
    public void addVacation(Vacation vacation) throws Exception {
        if(shift!=null){
            if(this.vacation==null) {
                this.vacation = vacation;
            }else {
                throw new Exception("Employee already have vacation!");
            }
        }else{
            throw new Exception("Employee on the shift cannot go to vacation");
        }
    }
    public Vacation changeVacation(Vacation vacation) throws Exception {
        Vacation prev = this.vacation;
        if(this.vacation!=null){
            this.vacation = vacation;
        }else{
            addVacation(vacation);
        }
        return  prev;
    }
    public Vacation removeVacation() throws Exception {
        Vacation prev = this.vacation;
        if(this.vacation!=null){
            this.vacation = null;
        }else{
            throw new Exception("Employee not have vacation to remove");
        }
        return  prev;
    }

    //Gettery
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

    //Metody
    public abstract float getIncome();


}
