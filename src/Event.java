
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract public class Event implements Serializable {
    private String name;
    private LocalDateTime date;
    private float price;

    private static List<Event> extent = new ArrayList<>();

    public Event(String name, LocalDateTime date, float price) {
        this.name = name;
        this.date = date;
        this.price = price;
        addEvent(this);
    }

    private void addEvent(Event event){
        extent.add(event);
    }

    public String getName() {
        return name;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public float getPrice() {
        return price;
    }


    public static void showExtent() {
        System.out.println("Extent of the class: " + Event.class.getName());

        for (Event event : extent) {
            System.out.println(event);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Event>) stream.readObject();
    }

    public abstract void getUniqueInformation();

    @Override
    public String toString() {
        String info = this.getClass().getName() + ": " + name+" " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm"))+"\n" +
                "Price: " + price + "\n";
        return info;
    }
}
