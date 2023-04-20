public class KidsBookWithFilms extends BookWithFilms implements IKidsBook {
    KidsBook kidsBook;

    public KidsBookWithFilms(String isbn, String title, Author author, int year, float price, String discType, boolean toy) {
        super(isbn, title, author, year, price, discType);
        kidsBook = new KidsBook(null,null,author,null,null,toy);
        kidsBook.removeBook();
        try {
            kidsBook.addKidsBookWithFilms(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public KidsBookWithFilms(String isbn, String title, Author author, int year, float price, Double rabate, String discType, boolean toy) {
        super(isbn, title, author, year, price, rabate, discType);
        kidsBook = new KidsBook(null,null,author,null,null,null,toy);
        kidsBook.removeBook();
        try {
            kidsBook.addKidsBookWithFilms(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
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