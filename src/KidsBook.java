import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
//toedit
public class KidsBook extends Book{
    private boolean toy;
    private KidsBookWithFilms kidsBookWithFilms;//Wielodziedziczenie

    private static List<Book> kidsBookExtent = new ArrayList<>();

    public KidsBook(String isbn, String title, Author author, Integer year, Float price, boolean toy) {
        super(isbn, title, author, year, price);
        this.toy = toy;
        addBook(this);
    }
    public KidsBook(String isbn, String title, Author author, Integer year, Float price, Double discount, boolean toy) throws Exception {
        super(isbn, title, author, year, price, discount);
        this.toy = toy;
        addBook(this);
    }

    //Ekstensja
    private void addBook(KidsBook book){
        kidsBookExtent.add(book);
    }
    public void addKidsBookWithFilms(KidsBookWithFilms kidsBookWithFilms) throws Exception{
        if(this.kidsBookWithFilms== null){
            this.kidsBookWithFilms = kidsBookWithFilms;
            kidsBookExtent.add(kidsBookWithFilms);
        }else {
            throw new Exception("KidsBook has already KidsBookFilms!");
        }
        this.removeBook();
    }
    public void removeBook(){
        super.removeBook();
        kidsBookExtent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + KidsBook.class.getName());

        for (Book book : kidsBookExtent) {
            System.out.println(book);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(kidsBookExtent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        kidsBookExtent = (ArrayList<Book>) stream.readObject();
    }

    //Gettery
    public boolean isToy() { return toy; }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Has toy: " + toy+ "\n";
        return info;
    }
}
