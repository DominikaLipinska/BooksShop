import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//toedit
abstract public class Screening implements Serializable {
    private String title;
    private Book book;

    private static List<Screening> screeningsExtent = new ArrayList<>();

    //Konstruktor
    public Screening(Book book,String title) {
        this.title = title;
        this.book = book;
    }

    //Ekstensja
    private void addScreening(Screening screening){
        if(!screeningsExtent.contains(screening)){
            screeningsExtent.add(screening);
        }
    }
    public void removeScreening(){
        if(screeningsExtent.contains(this)){
            screeningsExtent.remove(this);
        }
    }

    //Gettery i Settery
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
