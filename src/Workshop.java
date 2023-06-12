import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//toedit
public class Workshop extends Event {
    private int minMembers = 8;
    private int membersNum = 0;
    private List<WorkshopInstructor> workIns = new ArrayList<>();//Asocjacja Workshop -> Instructor (z atrybutem)

    private static List<Workshop> extent = new ArrayList<>();

    //Konstruktor
    public Workshop(String name, LocalDateTime date, float price ) {
        super(name, date, price);
        addWorkshop(this);
    }

    //Asocjacja Workshop -> Instructor (z atrybutem)
    public void addWorkIn(WorkshopInstructor workshopInstructor) {
        workIns.add(workshopInstructor);
    }
    public List<WorkshopInstructor> getWorkIns() {
        return workIns;
    }

    //Ekstensja
    private void addWorkshop(Workshop workshop){
        extent.add(workshop);
    }
    public void removeWorkshop(){
        while (!workIns.isEmpty()){
            workIns.get(0).removeWorkIn(workIns.get(0));
        }
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Workshop.class.getName());

        for (Workshop workshop : extent) {
            System.out.println(workshop);
        }
    }

    @Override
    public void getUniqueInformation() {
        System.out.println("Unique information about "+this.getName() + ": \n"
                + "Min Members: " + minMembers );
    }



    @Override
    public String toString() {
        String info = super.toString();
        if(!workIns.isEmpty()){
            info+= "Instructors:\n";
            for (WorkshopInstructor workIn:workIns) {
                try {
                    info+= workIn.getInstructor().getPerson().getFirstName() +
                            " "+workIn.getInstructor().getPerson().getLatsName()+"\n";
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return info;
    }
}
