import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
//toedit
public class BookWithFilms extends Book{
    private String discType;

    private static List<BookWithFilms> extent = new ArrayList<>();

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
        extent.add(book);
    }
    public void removeBook(){
        if(chapters.isEmpty()){
            allChapters.removeAll(chapters);
            chapters.removeAll(chapters);
        }
        while (!lists.isEmpty()){
            lists.get(0).removeBookQualif(this);
            lists.remove(0);
        }
        author.removeBook(this);
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + KidsBook.class.getName());

        for (BookWithFilms book : extent) {
            System.out.println(book);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<BookWithFilms>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Disc type: " + discType+ "\n";
        return info;
    }
}
