import java.io.Serializable;

abstract public class Screening implements Serializable {
    private String title;
    private Book book;

    public Screening(Book book,String title) {
        this.title = title;
        this.book = book;
    }

    public String getTitle() {
        return title;
    }

    public String getBookAuthor(){
        try {
            return book.getAurhor().getPerson().getFirstName() + " " + book.getAurhor().getPerson().getLatsName();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        String info = this.getClass().getName() + ": " + title + "\n";
        info += "Book: " + book.getTitle() + " " + getBookAuthor() + "\n";
        return info;
    }
}
