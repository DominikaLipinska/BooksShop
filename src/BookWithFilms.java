import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookWithFilms extends Book{
    private String discType;

    private static List<BookWithFilms> extent = new ArrayList<>();

    public BookWithFilms(String isbn, String title, Author author, int year, float price,String discType) {
        super(isbn, title, author, year, price);
        this.discType = discType;
        addFilm();
    }

    public BookWithFilms(String isbn, String title, Author author, int year, float price, Double rabate,String discType) {
        super(isbn, title, author, year, price, rabate);
        this.discType = discType;
        addFilm();
    }

    //Ekstensja
    private void addBook(BookWithFilms book){
        extent.add(book);
    }
    private void addFilm(){
        try {
            Film film1 = Film.createFilm(this,"Unknown",null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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

        for (BookWithFilms book : extent) {
            System.out.println(book);
        }
    }


    //Ekstensja trwałość
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
