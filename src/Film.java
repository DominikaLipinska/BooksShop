import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//toedit
public class Film extends Screening  {
    private static Object Film;
    private Integer duration;

    private static List<Film> filmExtent = new ArrayList<>();

    //Konstruktor
    private Film(Book book,String title,Integer duration) {
        super(book,title);
        this.duration = duration;
        addFilm(this);
    }
    public static Film createFilm(Book book, String title,Integer duration) throws Exception{
        if(book == null){
            throw new Exception("Book not exist!");
        }

        Film film = new Film(book,title,duration);
        book.addScreening(film);

        return film;
    }

    //Ekstensja
    private void addFilm(Film film){
        if(!filmExtent.contains(film)){
            filmExtent.add(film);
        }
    }
    public void removeFilm(){
        removeScreening();
        if(filmExtent.contains(this)){
            filmExtent.remove(this);
        }
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Film.class.getName());

        for (Film film : filmExtent) {
            System.out.println(film);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(filmExtent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        filmExtent = (ArrayList<Film>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = super.toString();
        String dur = "";
        if(duration==null){
            dur = "Unknown";
        }else {
            dur = duration.toString();
        }
        info+= "Duration: " + dur + "min" + "\n";
        return info;
    }
}
