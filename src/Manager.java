import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//toedit
public class Manager extends Employee{
    public Float salSupplement;
    private Shift managedShift; //Asocjacja Manager -> Shift(1-1)
    private List<Event> events = new ArrayList<>(); //Asocjacja Manager -> Event (1-*)

    public Manager(LocalDate empDate, float salary,Float salSupplement,Person person) {
        super(empDate, salary,person);
        this.authorized = true;
        this.salSupplement = salSupplement;
        addManager(this);
    }

    private static List<Manager> extent = new ArrayList<>(); //Ekstensja

    //Asocjacja Manager -> Shift(1-1)
    public void  addManageShift(Shift shift){
        if(managedShift==null){
            managedShift=shift;
        }
    }
    public void removeManageShift(){
        if(managedShift!=null){
            managedShift=null;
        }
    }

    //Asocjacja Manager -> Event (1-*)
    ////Workshop
    public void organizeEvent(String name, LocalDateTime date, float price){
        Event event = new Workshop(name,date,price);
        if(!events.contains(event)){
            events.add(event);
        }
        event.addManager(this);
    }
    ////ThemeParty
    public void organizeEvent(String name, LocalDateTime date, float price, String topic){
        Event event = new ThemeParty(name, date, price, topic);
        if(!events.contains(event)){
            events.add(event);
        }
        event.addManager(this);
    }
    ////AuthorsMeeting
    public void organizeEvent(Author author,String name, LocalDateTime date, float price, boolean treats){
        Event event = new AuthorsMeeting(author, name, date, price, treats);
        if(!events.contains(event)){
            events.add(event);
        }
        event.addManager(this);
    }

    //Ekstensja
    private void addManager(Manager manager){
        extent.add(manager);
    }
    public void removeManager(){
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Manager.class.getName());

        for (Manager manager: extent) {
            System.out.println(manager);
        }
    }
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Manager>) stream.readObject();
    }

    //Metody
    public void giveAuthorization(Salesman salesman){
        salesman.authorized = true;
    }
    public void removeAuthorization(Salesman salesman){
        salesman.authorized = false;
    }
    public void giveDiscount(Book book,double discount) throws Exception {
        book.giveDiscount(discount);
    }
    public WorkshopInstructor hireInstructor(Instructor instructor, Workshop workshop, float salary) throws Exception {
        return new WorkshopInstructor(salary, workshop, instructor);
    }

    //Geterry
    public Float getSalSupplement() {
        return salSupplement;
    }

    //Przesłonięcie
    @Override
    public float getIncome() {
        return getSalary()+salSupplement;
    }

    @Override
    public String toString() {
        String info = super.toString();
        info += "salSuplement: " + salSupplement +"\n";

        return info;
    }

    @Override
    public String getRole() {
        return super.getRole() + "("+ this.getClass().getName()+")";
    }
}
