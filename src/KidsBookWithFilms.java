import java.util.ArrayList;
import java.util.List;

public class KidsBookWithFilms extends BookWithFilms {
    KidsBook kidsBook;//Wielodziedziczenie

    private static List<KidsBookWithFilms> kidsBookWithFilmsExtent = new ArrayList<>();

    public KidsBookWithFilms(String isbn, String title, Author author, int year, float price, String discType, boolean toy) {
        super(isbn, title, author, year, price, discType);
        kidsBook = new KidsBook(null,null,author,null,null,toy);
        try {
            kidsBook.addKidsBookWithFilms(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        addKidsBookWithFilms(this);
    }

    public KidsBookWithFilms(String isbn, String title, Author author, int year, float price, Double rabate, String discType, boolean toy) throws Exception {
        super(isbn, title, author, year, price, rabate, discType);
        kidsBook = new KidsBook(null,null,author,null,null,toy);
        kidsBook.removeBook();
        try {
            kidsBook.addKidsBookWithFilms(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //Ekstensja
    private void addKidsBookWithFilms(KidsBookWithFilms kidsBookWithFilms){
        if(!kidsBookWithFilmsExtent.contains(kidsBookWithFilms)){
            kidsBookWithFilmsExtent.add(kidsBookWithFilms);
        }
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + KidsBookWithFilms.class.getName());

        for (KidsBookWithFilms book : kidsBookWithFilmsExtent) {
            System.out.println(book);
        }
    }

    @Override
    public void removeBook() {
        super.removeBook();
        kidsBookWithFilmsExtent.remove(this);
    }

    //Gettery
    public boolean isToy() {
        return kidsBook.isToy();
    }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Has toy: " + isToy();
        return info;
    }
}
