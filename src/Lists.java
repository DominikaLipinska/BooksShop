import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Lists implements Serializable {
    private String name;

    private Map<String,Book> booksQualif = new TreeMap<>(); //Asocjacja Book -> isbn | Lists (kwalifikowana)

    private Client client; //Asocja Client -> Lists (1-*)

    private static List<Lists> extent = new ArrayList<>(); //Ekstensja

    public Lists(String name,Client client) {
        this.name = name;
        this.client = client;
        addList(this);
    }

    //Ekstencja i Asocja Client -> Lists (1-*)
    private void addList(Lists list){
        if(!extent.contains(list)){
            extent.add(list);
            client.addList(list);
        }
    }
    public void removeList(){
        if(extent.contains(this)){
            extent.remove(this);
            client.removeList(this);
            for(Book b : booksQualif.values()) {
                booksQualif.remove(b.getIsbn());
                b.removeList(this);
            }
        }
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Lists.class.getName());

        for (Lists list : extent) {
            System.out.print(list);
        }
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

    //Ekstensja Trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Lists>) stream.readObject();
    }


    @Override
    public String toString() {
        String info = name + " ("+/*client.getFirstName()+" "+client.getLatsName()+*/")\n";
        if(!booksQualif.isEmpty()){
            for (Book book:booksQualif.values()) {
                /*info += book.getTitle() +" "+book.getAurhor().getFirstName()+" "+book.getAurhor().getLatsName() + "\n";*/
            }
        }
        return info;
    }
}
