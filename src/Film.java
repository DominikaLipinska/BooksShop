import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//toedit
public class Film extends Screening  {
    private static Object Film;
    private Integer duration;

    private static List<Film> extent = new ArrayList<>();

    //Konstruktor
    private Film(Book book,String title,Integer duration) {
        super(book,title);
        this.duration = duration;
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
        if(!extent.contains(film)){
            extent.add(film);
        }
    }
    public void removeFilm(){
        removeScreening();
        if(!extent.contains(this)){
            extent.remove(this);
        }
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
