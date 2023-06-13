import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//toedit
public class Series extends Screening{
    private int seasonsNum;

    private static List<Series> seriesExtent = new ArrayList<>();

    //Konstruktor
    private Series(Book book,String title,int seasonsNum) {
        super(book,title);
        this.seasonsNum = seasonsNum;
        addSeries(this);
    }
    public static Series createSeries(Book book, String title,int duration) throws Exception{
        if(book == null){
            throw new Exception("Book not exist!");
        }

        Series series = new Series(book,title,duration);
        book.addScreening(series);

        return series;
    }

    //Ekstensja
    private void addSeries(Series series){
        if(!seriesExtent.contains(series)){
            seriesExtent.add(series);
        }
    }
    public void removeSeries(){
        removeScreening();
        if(seriesExtent.contains(this)){
            seriesExtent.remove(this);
        }
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Series.class.getName());

        for (Series series : seriesExtent) {
            System.out.println(series);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(seriesExtent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        seriesExtent = (ArrayList<Series>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Number of seasons: " + seasonsNum + "seasons" + "\n";
        return info;
    }
}
