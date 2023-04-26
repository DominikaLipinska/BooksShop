import enums.OrderStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Book implements Serializable {
    private String isbn; //Asocjacja Book -> isbn | Lists (kwalifikowana)
    private String title;
    private Author author; //Asocja Author -> Book (1-*)
    private Integer year;
    private Float price;
    private Double discount;
    private static double maxDiscount = 0.3;
    private static Map<String,Book> isbnList = new HashMap<>(); //ISBN -> {Unique}
    private List<Lists> lists = new ArrayList<>(); //Asocjacja Book -> isbn | Lists (kwalifikowana)
    private List<Chapter> chapters = new ArrayList<>();//Asocjacja Book -> Chapter (kompozycja)
    private static Set<Chapter> allChapters = new HashSet<>();//Asocjacja Book -> Chapter (kompozycja)
    private List<Screening> screenings = new ArrayList<>();
    private static  Set<Screening> allScreenings = new HashSet<>();
    private List<Order> bookActiveOrders = new ArrayList<>(); //Asocjacja Order -> Book (*-*)

    private static List<Book> extent = new ArrayList<>();//Ekstensja

    public Book(String isbn,String title,Author author, Integer year, Float price) {
        try {
            this.isbn = addISBN(isbn);
            this.title = title;
            this.author = author;
            this.year = year;
            this.price = price;
            addBook(this);
            author.addBook(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public Book(String isbn,String title,Author author, Integer year, Float price, Double discount) {
        this(isbn,title,author,year,price);
        this.discount = discount;
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

    //ISBN -> {Unique}
    private String addISBN(String isbn) throws Exception {
        if(!isbnList.containsKey(isbn) && this.isbn==null){
            isbnList.put(isbn,this);
            return isbn;
        }else {
            throw new Exception("That book already exist or have another isbn");
        }
    }

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
    public void showChapters(){
        System.out.println(getChapters());
    }
    public void removeChapter(Chapter chapterToRemove){
        if (chapters.contains(chapterToRemove)){
            chapters.remove(chapterToRemove);

            allChapters.remove(chapterToRemove);
        }
    }

    //Asocjacja Order -> Book (*-*)
    public void addOrder(Order order){
        if(!bookActiveOrders.contains(order)){
            bookActiveOrders.add(order);
        }
    }
    public void removeOrder(Order order) throws Exception {
        OrderStatus[] statuses = {OrderStatus.FINISHED, OrderStatus.CANCELLED};
        if(order.checkStatus(statuses)){
            if(bookActiveOrders.contains(order)) {
                bookActiveOrders.remove(order);
            }
        }else {
            throw new Exception("Can't remove active order from book");
        }
    }

    //Metoda klasowa
    public static List<Book> findDiscountBook(){
        List<Book> rabateBook = new ArrayList<>();
        for (Book book : extent) {
            if (book.discount != null) rabateBook.add(book);
        }
        return rabateBook;
    }

    //Metody
    public void giveDiscount(double discount, boolean authorized) throws Exception {
        if(!authorized){
            throw  new Exception("You are not authorized to grant a discount");
        }else {
            if(discount>maxDiscount){
                throw new Exception(String.format("The discount (%s) has to be less than %s", discount, maxDiscount));
            }
            this.discount = discount;
        }
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
                    "\ndiscount: " + discount+"\n" + getChapters() + getScreening();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return info;
    }
}


