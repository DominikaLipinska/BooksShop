import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorsMeeting extends Event {
    private boolean treats;
    private Author author;//Asocjacja Author -> AuthorMeeting (1-*)

    private static List<AuthorsMeeting> extent = new ArrayList<>(); //Ekstensja

    public AuthorsMeeting(Author author,String name, LocalDateTime date, float price, boolean treats) {
        super(name, date, price);
        this.author = author;
        this.treats = treats;
        author.addMeeting(this);
        addAuthorsMeeting(this);
    }

    //Ekstensja
    private void addAuthorsMeeting(AuthorsMeeting meeting){
        extent.add(meeting);
    }
    public void removeAuthorsMeeting(){
        author.removeMeeting(this);
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + AuthorsMeeting.class.getName());

        for (AuthorsMeeting meeting : extent) {
            System.out.println(meeting);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<AuthorsMeeting>) stream.readObject();
    }

    @Override
    public void getUniqueInformation() {
        System.out.println("Unique information about "+this.getName() + ": \n"
                + "Has Treats: " + treats );

    }

    @Override
    public String toString() {
        String info = super.toString() + "Has treats: " + treats + "\nAuthor: " + author.getPerson().getName()+"\n";
        return info;
    }
}
