import java.io.Serializable;

public class Chapter implements Serializable {
    private String name;
    private Book book;

    private Chapter(Book book, String name){
        this.name = name;
        this.book = book;
    }

    public static Chapter createChapter(Book book, String name) throws Exception{
        if(book==null){
            throw new Exception("The given book does not exist!");
        }

        Chapter chapter = new Chapter(book,name);
        book.addChapter(chapter);

        return chapter;
    }

    public String getName() {
        return name;
    }

}
