import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Book implements Serializable {
    private String isbn; //Asocjacja Book -> isbn | Lists (kwalifikowana)
    private String title;
    private Author author; //Asocja Author -> Book (1-*)
    private int year;
    private float price;
    private Double rabate;
    private static double maxRabate = 0.3;
    private List<Lists> lists = new ArrayList<>(); //Asocjacja Book -> isbn | Lists (kwalifikowana)
    private List<Chapter> chapters = new ArrayList<>();//Asocjacja Book -> Chapter (kompozycja)
    private static Set<Chapter> allChapters = new HashSet<>();//Asocjacja Book -> Chapter (kompozycja)
    private List<Screening> screenings = new ArrayList<>();
    private static  Set<Screening> allScreenings = new HashSet<>();

    private static List<Book> extent = new ArrayList<>();//Ekstensja

    public Book(String isbn,String title,Author author, int year, float price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        addBook(this);
        author.addBook(this);
    }
    public Book(String isbn,String title,Author author, int year, float price, Double rabate) {
        this(isbn,title,author,year,price);
        this.rabate = rabate;
    }

    public void addScreening(Screening screening) throws Exception{
        if(!screenings.contains(screening)){
            if(allScreenings.contains(screening)){
                throw  new Exception("The Screening is already connected with Book!");
            }
            screenings.add(screening);
            allScreenings.add(screening);
        }
    }

    //Ekstensja
    private void addBook(Book book){
        extent.add(book);
    }
    public void removeBook(){
        if(!chapters.isEmpty()){
            allChapters.removeAll(chapters);
            chapters.removeAll(chapters);
        }
        while (!lists.isEmpty()){
            lists.get(0).removeBookQualif(this);
            lists.remove(0);
        }
        author.removeBook(this);
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Book.class.getName());

        for (Book book : extent) {
            System.out.println(book);
        }
    }

    //Gettery
    public Float getPrice(){return price;}
    public Float getPrice(double rabate) {
        if (rabate<0 || rabate>1){
            return null;
        }else {
            return price*(1-(float) rabate);
        }
    }
    public String getTitle(){
        return title;
    }
    public String getIsbn() {
        return isbn;
    }
    public Author getAurhor() {return author;}

    //Asocjacja Book -> isbn | Lists (kwalifikowana)
    public void addList(Lists list){
        lists.add(list);
    }
    public void removeList(Lists list){
        lists.remove(list);
    }

    //Asocjacja Book -> Chapter (kompozycja)
    public Chapter createChapter(String name) throws Exception{
        Chapter chapter = Chapter.createChapter(this,name);
        return chapter;
    }
    public void addChapter(Chapter chapter) throws Exception{
        if(!chapters.contains(chapter)){
            if(allChapters.contains(chapter)){
                throw new Exception("The chapter is already connected with a book!");
            }
            chapters.add(chapter);
            allChapters.add(chapter);
        }
    }
    public String getChapters(){
        String info = "Chapters: \n";
        if(chapters.isEmpty()){
            info += title + " has not chapters\n";
        }else{
            for (Chapter chapter : chapters ) {
                info+=chapter.getName()+"\n";
            }
        }
        return info;
    }

    public String getScreening(){
        String info = "Screening: \n";
        if(screenings.isEmpty()){
            info += title + " has not screening\n";
        }else{
            for (Screening screening : screenings ) {
                info+=screening+"\n";
            }
        }
        return info;
    }

    public void showChapters(){
        System.out.println(getChapters());
    }
    public void removeChapter(Chapter chapterToRemove){
        if (chapters.contains(chapterToRemove)){
            chapters.remove(chapterToRemove);

            allChapters.remove(chapterToRemove);
        }
    }

    //Metoda klasowa
    public static List<Book> findRabateBook(){
        List<Book> rabateBook = new ArrayList<>();
        for (Book book : extent) {
            if (book.rabate != null) rabateBook.add(book);
        }
        return rabateBook;
    }

    //Ekstensja trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Book>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = this.getClass().getName()+": \""+title+"\" ";
        try {
            info += author.getPerson().getFirstName()+" " +
                    author.getPerson().getLatsName() +" " + year +
                    "\nprice: " + price +
                    "\nrabte: " + rabate +"\n" + getChapters() + getScreening();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return info;
    }
}


