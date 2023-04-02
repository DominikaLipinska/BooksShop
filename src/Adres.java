import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Adres implements Serializable {
    private String city;
    private String street;
    private int houseNumber;
    private Integer apartmentNumber;

    private static List<Adres> extent = new ArrayList<>();

    public Adres(String city, String street, int houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        addAdres(this);
    }

    public Adres(String city, String street, Integer houseNumber, Integer apartmentNumber) {
        this(city, street, houseNumber);
        this.apartmentNumber = apartmentNumber;
    }

    private void addAdres(Adres adres){
        extent.add(adres);
    }

    public static void showExtent() {
        System.out.println("Extent of the class: " + Adres.class.getName());

        for (Adres adres : extent) {
            System.out.println(adres);
        }
    }

    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }

    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Adres>) stream.readObject();
    }
}

