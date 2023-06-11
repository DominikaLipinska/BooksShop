import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthorAward implements Serializable {
    private float moneyAward;
    private Author author;//Asocja Author -> Awards (z atrybutem) {Bag}
    private Award award;//Asocja Author -> Awards (z atrybutem) {Bag}

    private static List<AuthorAward> extent = new ArrayList<>();//Ekstencja

    //Konstruktor
    public AuthorAward(float moneyAward, Author author, Award award) {

        this.moneyAward = moneyAward;
        this.author = author;
        this.award = award;
        this.author.addAuthAward(this);
        this.award.addAuthAward(this);
        addAuthAward(this);
    }

    //Gettery
    public float getMoneyAward() {
        return moneyAward;
    }
    public Author getAuthor() {
        return author;
    }
    public Award getAward() {
        return award;
    }

    //Ekstensja
    private void addAuthAward(AuthorAward authorAward){
        extent.add(authorAward);
    }
    public void removeAuthAward(){
        if(author.getAuthAwards().contains(this)){
            author.getAuthAwards().remove(this);
        }
        if(award.getAuthAwards().contains(this)){
            award.getAuthAwards().remove(this);
        }
        extent.remove(this);
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<AuthorAward>) stream.readObject();
    }
}
