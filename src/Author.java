import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Author extends Roles implements Serializable  {
    private List<AuthorAward> authAwards = new ArrayList<>(); //Asocja Author -> Awards (z atrybutem)
    private List<Book> books = new ArrayList<>();//Asocja Author -> Book (1-*)
    private String pubHouse;

    private static List<Author> extent = new ArrayList<>();//Ekstensja

    public Author(String pubHouse,Person person) {
        super(person);
        this.pubHouse = pubHouse;
        addAuthor(this);
    }

    //Atrybut wyliczalny
    public int getAwardsNum(){
        return authAwards.size();
    }

    //Ekstensja
    private void addAuthor(Author author){
        extent.add(author);
    }
    public void removeAuthor(){
        while (!authAwards.isEmpty()){
            authAwards.get(0).removeAuthAward(authAwards.get(0));
        }
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Author.class.getName());

        for (Author author : extent) {
            System.out.println(author);
        }
    }

    //Asocja Author -> Awards (z atrybutem)
    public void addAuthAward(AuthorAward authorAward){
        authAwards.add(authorAward);
    }
    public List<AuthorAward> getAuthAwards(){
        return authAwards;
    }
    public void showAwards(){
        if(!authAwards.isEmpty()){
            String info = "Awards:\n";
            for (AuthorAward award : authAwards) {
                info += award.getAward()+ "\n";
            }
            System.out.println(info);
        }else {
            /*System.out.println(getFirstName()+" "+getLatsName()+" has no awards yet");*/
        }
    }

    //Asocjacje Author -> Book
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

    //getery

    public String getPubHouse() {
        return pubHouse;
    }

    //Ekstensja trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Author>) stream.readObject();
    }

    @Override
    public String toString(){
        String info = "";
        try {
            info += getPerson();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(!books.isEmpty()){
            info+="Books:\n";
            for (Book book:books) {
                info += book.getTitle() + "\n";
            }
        }

        if(!authAwards.isEmpty()){
            info+="Awards:\n";
            for (AuthorAward award : authAwards) {
                info += award.getAward().getName() + "\n";
            }
        }
        return info;
    }


    @Override
    public String getRole() {
        return this.getClass().getName();
    }
}
