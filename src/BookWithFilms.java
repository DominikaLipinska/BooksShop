import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
//toedit
public class BookWithFilms extends Book{
    private String discType;

    private static List<BookWithFilms> bookWithFilmsExtent = new ArrayList<>();

    //Kontruktory
    public BookWithFilms(String isbn, String title, Author author, int year, float price,String discType) {
        super(isbn, title, author, year, price);
        this.discType = discType;
        addBook(this);
    }
    public BookWithFilms(String isbn, String title, Author author, int year, float price, Double rabate,String discType) throws Exception {
        super(isbn, title, author, year, price, rabate);
        this.discType = discType;
        addBook(this);
    }

    //Ekstensja
    private void addBook(BookWithFilms book){
        bookWithFilmsExtent.add(book);
    }
    public void removeBook(){
        super.removeBook();
        bookWithFilmsExtent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + BookWithFilms.class.getName());

        for (BookWithFilms book : bookWithFilmsExtent) {
            System.out.println(book);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(bookWithFilmsExtent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        bookWithFilmsExtent = (ArrayList<BookWithFilms>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Disc type: " + discType+ "\n";
        return info;
    }
}
