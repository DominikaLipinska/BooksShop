import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        addScreening(this);
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
    public static void showExtent() {
        System.out.println("Extent of the class: " + Screening.class.getName());

        for (Screening screening: screeningsExtent) {
            System.out.println(screening);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(screeningsExtent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        screeningsExtent = (ArrayList<Screening>) stream.readObject();
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
