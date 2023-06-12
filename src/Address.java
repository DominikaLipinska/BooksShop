import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Address implements Serializable {
    private String city;
    private String street;
    private int houseNumber;
    private Integer apartmentNumber;
    private List<Person> personList = new ArrayList<>(); //Asocjacja Address -> Person (1-*)

    private static List<Address> extent = new ArrayList<>(); //Ekstensja

    //Konstruktory
    public Address(String city, String street, int houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        addAddress(this);
    }
    public Address(String city, String street, int houseNumber, Integer apartmentNumber) {
        this(city, street, houseNumber);
        this.apartmentNumber = apartmentNumber;
    }

    //Asocjacja Address -> Person (1-*)
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
    public String getAddress(){
        String info = city + " " + street + " " + houseNumber;
        if(apartmentNumber!=null){
            info+="/"+apartmentNumber;
        }
        return info;
    }

    public void setAddress(String city, String street, int houseNumber){
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
    public void setAddress(String city, String street, int houseNumber, Integer apartmentNumber) {
        setAddress(city, street, houseNumber);
        this.apartmentNumber = apartmentNumber;
    }

    //Ekstensja
    private void addAddress(Address address){
        extent.add(address);
    }
    public void removeAddress(){
        while (!personList.isEmpty()){
            personList.get(0).removeAddress();
            removePerson(personList.get(0));
        }
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Address.class.getName());

        for (Address address : extent) {
            System.out.println(address);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Address>) stream.readObject();
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

