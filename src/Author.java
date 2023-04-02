import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Author extends Person implements Serializable  {
    private List<Award> awards = new ArrayList<>();//Asocja Author -> Awards (*-*)
    private List<Book> books = new ArrayList<>();//Asocja Author -> Book (1-*)

    private static List<Author> extent = new ArrayList<>();//Ekstensja

    public Author(String firstName, String latsName) {
        super(firstName, latsName);
        addAuthor(this);
    }
    public Author(String firstName, String latsName, String phoneNumber, String email, Adres adres) {
        super(firstName, latsName, phoneNumber, email, adres);
        addAuthor(this);
    }

    //Atrybut wyliczalny
    public int getAwardsNum(){
        return awards.size();
    }

    //Ekstensja
    private void addAuthor(Author author){
        extent.add(author);
    }
    public void removeAuthor(){
        if(!awards.isEmpty()){
            for (Award award:awards) {
                removeAward(award);
            }
        }
        while (!books.isEmpty()){
            removeBook(books.get(0));
        }
        extent.remove(this);

    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Author.class.getName());

        for (Author author : extent) {
            System.out.println(author);
        }
    }

    //Asocja Author -> Awards (*-*)
    public void addAward(Award award){
        awards.add(award);
        award.addAuthor(this);
    }
    public void removeAward(Award award){
        if(awards.contains(award)){
            award.removeAuthor(this);
            awards.remove(award);
        }
    }
    public void showAwards(){
        if(!awards.isEmpty()){
            String info = "Awards:\n";
            for (Award award : awards) {
                info += award + "\n";
            }
            System.out.println(info);
        }else {
            System.out.println(getFirstName()+" "+getLatsName()+" has no awards yet");
        }
    }

    //
    public void addBook(Book book){
        if(!books.isEmpty()){
            if(!books.contains(book)){
                books.add(book);
            }
        }else{
            books.add(book);
        }
    }
    public void removeBook(Book book){
        if(books.contains(book)){
            books.remove(book);
            book.removeBook();
        }
    }


    //Ekstensja trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Author>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = super.toString();
        if(!books.isEmpty()){
            info+="Books:\n";
            for (Book book:books) {
                info += book.getTitle() + "\n";
            }
        }

        if(!awards.isEmpty()){
            info+="Awards:\n";
            for (Award award : awards) {
                info += award + "\n";
            }
        }
        return info;
    }
}
