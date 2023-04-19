public class Series extends Screening{
    private int seasonsNum;

    private Series(Book book,String title,int seasonsNum) {
        super(book,title);
        this.seasonsNum = seasonsNum;
    }

    public static Series createSeries(Book book, String title,int duration) throws Exception{
        if(book == null){
            throw new Exception("Book not exist!");
        }

        Series series = new Series(book,title,duration);
        book.addScreening(series);

        return series;
    }

    @Override
    public String toString() {
        String info = super.toString();
        info+= "Number of seasons: " + seasonsNum + "seasons" + "\n";
        return info;
    }
}
