import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkshopInstructor implements Serializable {
    private float salary;
    private Workshop workshop;//Asocjacja Workshop -> Instructor (z atrybutem)
    private Instructor instructor;//Asocjacja Workshop -> Instructor (z atrybutem)

    private static List<WorkshopInstructor> extent = new ArrayList<>();//Ekstencja

    public WorkshopInstructor(float salary, Workshop workshop, Instructor instructor) {
        this.salary = salary;
        this.workshop = workshop;
        this.instructor = instructor;
        this.workshop.addWorkIn(this);
        this.instructor.addWorkIn(this);
        addWorkIn(this);
    }

    //Ekstensja
    private void addWorkIn(WorkshopInstructor workshopInstructor){
        extent.add(workshopInstructor);
    }
    public void removeWorkIn(WorkshopInstructor workIn){
        if(instructor.getWorkIns().contains(workIn)){
            instructor.getWorkIns().remove(workIn);
        }
        if(workshop.getWorkIns().contains(workIn)){
            workshop.getWorkIns().remove(workIn);
        }
        extent.remove(this);
    }

    //Gettery
    public float getSalary() {
        return salary;
    }
    public Workshop getWorkshop() {
        return workshop;
    }
    public Instructor getInstructor() {
        return instructor;
    }

    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<WorkshopInstructor>) stream.readObject();
    }
}
