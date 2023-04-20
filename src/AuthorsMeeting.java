import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorsMeeting extends Event {
    private boolean treats;

    private static List<AuthorsMeeting> extent = new ArrayList<>();

    public AuthorsMeeting(String name, LocalDateTime date, float price, boolean treats) {
        super(name, date, price);
        this.treats = treats;
        addAuthorsMeeting(this);
    }

    private void addAuthorsMeeting(AuthorsMeeting meeting){
        extent.add(meeting);
    }

    public static void showExtent() {
        System.out.println("Extent of the class: " + ThemeParty.class.getName());

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
        String info = super.toString() + "Has treats: " + treats + "\n";
        return info;
    }
}
