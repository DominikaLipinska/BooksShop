import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//toedit
public class ThemeParty extends Event{
    private String topic;

    private static List<ThemeParty> extent = new ArrayList<>();

    //Konstruktor
    public ThemeParty(String name, LocalDateTime date, float price, String topic) {
        super(name, date, price);
        this.topic=topic;
        addThemeParty(this);
    }

    //Ekstensja
    private void addThemeParty(ThemeParty party){
        extent.add(party);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + ThemeParty.class.getName());

        for (ThemeParty party : extent) {
            System.out.println(party);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<ThemeParty>) stream.readObject();
    }

    @Override
    public void getUniqueInformation() {
        System.out.println("Unique information about "+this.getName() + ": \n"
                + "Topic: " + topic );
    }


    @Override
    public String toString() {
        String info = super.toString() + "Topic: " + topic + "\n";
        return info;
    }
}
