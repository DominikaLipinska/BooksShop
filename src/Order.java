import enums.OrderStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private Client client;//Asocjacja Client -> Order (1-*)
    private OrderStatus status;
    private List<Book> orderedBooks = new ArrayList<>(); //Asocjacja Order -> Book (*-*)

    private static List<Order> extent = new ArrayList<>();//Ekstensja {Ordered}

    public Order(Client client, List<Book> orderedBooks) {
        this.client = client;
        for (Book book:orderedBooks) {
            addBook(book);
            book.addOrder(this);
        }
        this.status = OrderStatus.SUBMITED;
        client.addOrder(this);
        Employee.addOrder(this);
    }

    //Gettery
    public OrderStatus getStatus() {
        return status;
    }
    public Client getClient() {
        return client;
    }

    //Settery
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

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
        while (!orderedBooks.isEmpty()){
            orderedBooks.remove(0);
        }
        Employee.removeOrder(this);
        client.removeOrder(this);
        extent.remove(this);
    }


    //Asocjacja Order -> Book (*-*)
    private void addBook(Book book){
        orderedBooks.add(book);
    }
    private void removeBook(Book book){
        try {
            book.removeOrder(this);
            orderedBooks.remove(book);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    //Ekstensja trwałość
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
        for (Book book:orderedBooks) {
            info+= book.getTitle() + "-"+book.getAurhor().getPerson().getName()+"\n";
        }
        return  info;
    }
}
