import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthorAward implements Serializable {
    private float moneyAward;
    private Author author;//Asocja Author -> Awards (z atrybutem)
    private Award award;//Asocja Author -> Awards (z atrybutem)

    private static List<AuthorAward> extent = new ArrayList<>();//Ekstencja

    public AuthorAward(float moneyAward, Author author, Award award) {

        this.moneyAward = moneyAward;
        this.author = author;
        this.award = award;
        this.author.addAuthAward(this);
        this.award.addAuthAward(this);
        addAuthAward(this);
    }


    //Ekstensja
    private void addAuthAward(AuthorAward authorAward){
        extent.add(authorAward);
    }
    public void removeAuthAward(AuthorAward authAward){
        if(author.getAuthAwards().contains(authAward)){
            author.getAuthAwards().remove(authAward);
        }
        if(award.getAuthAwards().contains(authAward)){
            award.getAuthAwards().remove(authAward);
        }
        extent.remove(this);
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

    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<AuthorAward>) stream.readObject();
    }
}
