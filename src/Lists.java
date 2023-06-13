import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
//toedit
public class Lists implements Serializable {
    private String name;
    private Map<String,Book> booksQualif = new TreeMap<>(); //Asocjacja Book -> isbn | Lists (kwalifikowana)
    private Client client; //Asocja Client -> Lists (1-*)
    private List<Order> orders = new ArrayList<>(); //Asocjaca Lists -> Order (1-1)

    private static List<Lists> listsExtent = new ArrayList<>(); //Ekstensja

    //Konstruktor
    public Lists(String name,Client client) {
        this.name = name;
        this.client = client;
        client.addList(this);
        addList(this);
    }

    //Asocjacja Book -> isbn | Lists (kwalifikowana)
    public void addBookQualif(Book newBook){
        if(!booksQualif.containsKey(newBook.getIsbn())){
            booksQualif.put(newBook.getIsbn(),newBook);

            newBook.addList(this);
        }
    }
    public Book findBookQualif(String isbn) {
        if(!booksQualif.containsKey(isbn)){
            return null;
        }
        return booksQualif.get(isbn);
    }
    public void removeBookQualif(Book book){
        booksQualif.remove(book.getIsbn());
    }

    //Asocjaca Lists -> Order (1-1)
    public Order createOrder() throws Exception {
        return new Order(client,this);
    }
    public void addOrder(Order order){
        if(!orders.contains(order)){
            orders.add(order);
        }
    }
    public void removeOrder(Order order){
        if(orders.contains(order)){
            orders.remove(order);
        }
    }

    //Ekstencja
    private void addList(Lists list){
        if(!listsExtent.contains(list)){
            listsExtent.add(list);
        }
    }
    public void removeList(){
        if(listsExtent.contains(this)){
            listsExtent.remove(this);
            client.removeList(this);
            for(Book b : booksQualif.values()) {
                //booksQualif.remove(b.getIsbn());
                b.removeList(this);
            }
        }
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Lists.class.getName());

        for (Lists list : listsExtent) {
            System.out.print(list);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(listsExtent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        listsExtent = (ArrayList<Lists>) stream.readObject();
    }

    //Gettery i Settery
    public List<Book> getBooksList() {
        List<Book> bookList = new ArrayList<>();
        for (Book book :booksQualif.values()) {
            bookList.add(book);
        }
        return bookList;
    }

    @Override
    public String toString() {
        String info = name + " ("+client.getPerson().getFirstName()+" "+client.getPerson().getLatsName()+")\n";
        if(!booksQualif.isEmpty()){
            for (Book book:booksQualif.values()) {
                info += book.getTitle() +" "
                        +book.getAurhor().getPerson().getFirstName()+" "+
                        book.getAurhor().getPerson().getLatsName() + "\n";
            }
        }
        return info;
    }
}
