import enums.OrderStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//toedit
public class Order implements Serializable {
    private Client client;//Asocjacja Client -> Order (1-*)
    private OrderStatus status;
    private Lists orderedBooks; //Asocjacja Order -> Book (*-*)
    private Employee employee; //Asocjacja Employee -> Order (1-*)

    private static List<Order> extent = new ArrayList<>();//Ekstensja {Ordered}

    //Konstruktor
    public Order(Client client, Lists lists) {
        this.client = client;
        this.orderedBooks = lists;
        this.status = OrderStatus.SUBMITED;
        client.addOrder(this);
        lists.addOrder(this);
        Employee.addOrder(this);
    }

    //Asocjacja Employee -> Order (1-*)
    public void addEmployee(Employee employee){
        if(this.employee!=null){
            this.employee = employee;
        }
    }

    //Gettery i Settery
    public OrderStatus getStatus() {
        return status;
    }
    public Client getClient() {
        return client;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    //Metody
    public boolean checkStatus(OrderStatus[] statuses){
        for (int i = 0; i < statuses.length; i++) {
            if(checkStatus(statuses[i])){
                return true;
            }
        }
        return false;
    }
    public boolean checkStatus(OrderStatus status){
        if(this.status==status){
            return true;
        }
        return false;
    }

    //Ekstensja
    private void addOrder(Order order){
        extent.add(this);
    }
    private void removeOrder() throws Exception {
        Employee.removeOrder(this);
        client.removeOrder(this);
        orderedBooks.removeOrder(this);
        employee.removeOrder(this);
        extent.remove(this);
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Order>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = "Order for client: " + client.getPerson().getName()+
                "\nStatus: " + status+"\n";
        for (Book book:orderedBooks.getBooksList()) {
            info+= book.getTitle() + "-"+book.getAurhor().getPerson().getName()+"\n";
        }
        return  info;
    }
}
