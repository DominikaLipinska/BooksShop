import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Award implements Serializable {
    private String name;
    private List<AuthorAward> authAwards = new ArrayList<>();//Asocja Author -> Awards (z atrybutem) {Bag}

    private static List<Award> extent = new ArrayList<>();//Ekstensja

    //Konstruktor
    public Award(String name) {
        this.name = name;
        addAward(this);
    }

    //Asocja Author -> Awards (z atrybutem)
    public void addAuthAward(AuthorAward authorAward){
        authAwards.add(authorAward);
    }
    public List<AuthorAward> getAuthAwards(){
        return authAwards;
    }

    //Gettery i Settery
    public String getName() {
        return name;
    }

    //Ekstensja
    private void addAward(Award award){
        extent.add(award);
    }
    public void removeAward(){
        while (!authAwards.isEmpty()){
            authAwards.get(0).removeAuthAward();
        }
        extent.remove(this);
    }
    public static void showExtent(){
        System.out.println("Extent of the class: " + Award.class.getName());

        for (Award award : extent) {
            System.out.println(award);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Award>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = name + "\n";
        if(!authAwards.isEmpty()){
            info+= "Authors:\n";
            for (AuthorAward authAward:authAwards) {
                try {
                    info+= authAward.getAuthor().getPerson().getName() +"\nMoney award: "+
                            authAward.getMoneyAward()+"\n";
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return info;
    }
}
