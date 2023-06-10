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
    private List<Person> personList = new ArrayList<>(); //Asocjacja Adres -> Person (1-*)

    private static List<Adres> extent = new ArrayList<>(); //Ekstensja

    //Konstruktory
    public Adres(String city, String street, int houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        addAdres(this);
    }
    public Adres(String city, String street, int houseNumber, Integer apartmentNumber) {
        this(city, street, houseNumber);
        this.apartmentNumber = apartmentNumber;
    }

    //Asocjacja Adres -> Person (1-*)
    public void addPerson(Person person){
        if(!personList.contains(person)) {
            personList.add(person);
        }
    }
    public void removePerson(Person person){
        if(personList.contains(person)) {
            personList.remove(person);
        }
    }

    //Ekstensja
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

    //Getery i Settery

    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public int getHouseNumber() {
        return houseNumber;
    }
    public Integer getApartmentNumber() {
        return apartmentNumber;
    }
    public List<Person> getPersonList() {
        return personList;
    }

    public void setAdres(String city, String street, int houseNumber){
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
    public void setAdres(String city, String street, int houseNumber, Integer apartmentNumber) {
        setAdres(city, street, houseNumber);
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public String toString() {
        String info = city + " " + street + " " + houseNumber;
        if(apartmentNumber!=null){
            info+="/"+apartmentNumber;
        }
        if (!personList.isEmpty()){
            info+="\nPerons:\n";
            for (Person person:personList) {
                info+= person.getName() + "\n";
            }
        }
        return info;
    }
}

