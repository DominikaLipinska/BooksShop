import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Award implements Serializable {
    private String name;
    private int year;
    private List<Author> authors = new ArrayList<>();//Asocja Author -> Awards (*-*)

    private static List<Award> extent = new ArrayList<>();//Ekstensja

    public Award(String name, int year) {
        this.name = name;
        this.year = year;
        addAward(this);
    }

    //Ekstensja
    private void addAward(Award award){
        extent.add(award);
    }
    public void removeAward(){
        while (!authors.isEmpty()){
            Author author = authors.get(0);
            author.removeAward(this);
            removeAuthor(author);
        }
        extent.remove(this);
    }
    public static void showExtent(){
        System.out.println("Extent of the class: " + Award.class.getName());

        for (Award award : extent) {
            System.out.println(award);
        }
    }

    //Asocja Author -> Awards (*-*)
    public void addAuthor(Author author){
        if(!authors.contains(author)){
            authors.add(author);
        }
    }
    public void removeAuthor(Author author){
        if (authors.contains(author)){
            authors.remove(author);
        }
    }

    //Ekstensja trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Award>) stream.readObject();
    }

    @Override
    public String toString() {
        return  name + " " + year;
    }
}
