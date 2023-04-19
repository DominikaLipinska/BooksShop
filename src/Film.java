import java.io.Serializable;

public class Film extends Screening  {
    private static Object Film;
    private int duration;

    private Film(Book book,String title,int duration) {
        super(book,title);
        this.duration = duration;
    }

    public static Film createFilm(Book book, String title,int duration) throws Exception{
        if(book == null){
            throw new Exception("Book not exist!");
        }

        Film film = new Film(book,title,duration);
        book.addScreening(film);

        return film;
    }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Duration: " + duration + "min" + "\n";
        return info;
    }
}
