import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class KidsBook extends Book{
    private boolean toy;
    private KidsBookWithFilms kidsBookWithFilms;

    private static List<KidsBook> extent = new ArrayList<>();

    public KidsBook(String isbn, String title, Author author, Integer year, Float price, boolean toy) {
        super(isbn, title, author, year, price);
        this.toy = toy;
    }

    public KidsBook(String isbn, String title, Author author, Integer year, Float price, Double discount, boolean toy) throws Exception {
        super(isbn, title, author, year, price, discount);
        this.toy = toy;
    }

    //Ekstensja
    private void addBook(KidsBook book){
        extent.add(book);
    }
    public void addKidsBookWithFilms(KidsBookWithFilms kidsBookWithFilms) throws Exception{
        if(this.kidsBookWithFilms== null){
            this.kidsBookWithFilms = kidsBookWithFilms;
        }else {
            throw new Exception("KidsBook has already KidsBookFilms!");
        }
        this.removeBook();
    }


    /*public void removeBook(){
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
    }*/
    public static void showExtent() {
        System.out.println("Extent of the class: " + KidsBook.class.getName());

        for (KidsBook book : extent) {
            System.out.println(book);
        }
    }

    //Gettery
    public boolean isToy() { return toy; }

    //Ekstensja trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<KidsBook>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Has toy: " + toy+ "\n";
        return info;
    }
}
