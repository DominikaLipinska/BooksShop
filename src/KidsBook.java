public class KidsBook extends Book{
    private boolean toy;

    public KidsBook(String isbn, String title, Author author, int year, float price) {
        super(isbn, title, author, year, price);
    }

    public KidsBook(String isbn, String title, Author author, int year, float price, Double rabate) {
        super(isbn, title, author, year, price, rabate);
    }
}
