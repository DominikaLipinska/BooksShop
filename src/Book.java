import enums.OrderStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
//toedit
public class Book implements Serializable {
    private String isbn;// {unique} Asocjacja Book -> isbn | Lists (kwalifikowana)
    private String title;
    protected Author author; //Asocja Author -> Book (1-*)
    private Integer year;
    private Float price;
    private Double discount;
    private static double maxDiscount = 0.3; //Atr. klasowy
    private static Map<String,Book> isbnList = new HashMap<>(); //ISBN -> {Unique}
    private List<Lists> lists = new ArrayList<>(); //Asocjacja Book -> isbn | Lists (kwalifikowana)
    private List<Chapter> chapters = new ArrayList<>();//Asocjacja Book -> Chapter (kompozycja)
    protected static Set<Chapter> allChapters = new HashSet<>();//Asocjacja Book -> Chapter (kompozycja)
    private Screening screening; //Wielodziedziczenie ekranizacja
    private static  Set<Screening> allScreenings = new HashSet<>();//Wielodziedziczenie ekranizacja
    private List<Order> bookActiveOrders = new ArrayList<>(); //Asocjacja Order -> Book (*-*)

    private static List<Book> bookExtent = new ArrayList<>();//Ekstensja

    //Konstruktory
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
    public Book(String isbn,String title,Author author, Integer year, Float price, Double discount) throws Exception {
        this(isbn,title,author,year,price);
        giveDiscount(discount);
    }

    //Wieloapektowe ekranizacja
    public void addScreening(Screening screening) throws Exception{
        if(this.screening!=screening){
            if(allScreenings.contains(screening)){
                throw  new Exception("The Screening is already connected with Book!");
            }
            this.screening=screening;
            allScreenings.add(screening);
        }
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
        if(lists.contains(list)){
            lists.remove(list);
        }

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
        for (Book book : bookExtent) {
            if (book.discount != null) rabateBook.add(book);
        }
        return rabateBook;
    }

    //Metody
    ////Ograniczenie statyczne
    public void giveDiscount(double discount) throws Exception {
        if(discount>maxDiscount){
            throw new Exception(String.format("The discount (%s) has to be less than %s", discount, maxDiscount));
        }
        this.discount = discount;
    }

    //Ekstensja
    private void addBook(Book book){
        bookExtent.add(book);
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
        if(screening!=null){
            try {
                ((Film) screening).removeFilm();
            }catch (Exception e){
                ((Series)screening).removeSeries();
            }
        }
        author.removeBook(this);
        bookExtent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Book.class.getName());

        for (Book book : bookExtent) {
            System.out.println(book);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(bookExtent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        bookExtent = (ArrayList<Book>) stream.readObject();
    }

    //Gettery
    public String getTitle(){
        return title;
    }
    public String getIsbn() {
        return isbn;
    }
    public Author getAurhor() {return author;}
    public String getScreening(){
        String info = "Screening: \n";
        if(screening==null){
            info += title + " has not screening\n";
        }else{
            info+=screening+"\n";
        }
        return info;
    }
    public List<Order> getBookActiveOrders() {
        return bookActiveOrders;
    }
    public List<Lists> getLists() {
        return lists;
    }
    public static Set<Chapter> getAllChapters() {
        return allChapters;
    }

    ////Przeciążenie
    public Float getPrice(){return price;}
    public Float getPrice(double rabate) {
        if (rabate<0 || rabate>1){
            return null;
        }else {
            return price*(1-(float) rabate);
        }
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


