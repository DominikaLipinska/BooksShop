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

    public WorkshopInstructor(float salary, Workshop workshop, Instructor instructor) throws Exception {
        if(isWorkInExist(workshop, instructor)){
            throw new Exception("This workshop has already this instructor");
        }else {
            this.salary = salary;
            this.workshop = workshop;
            this.instructor = instructor;
            this.workshop.addWorkIn(this);
            this.instructor.addWorkIn(this);
            addWorkIn(this);
        }
    }

    public boolean isWorkInExist(Workshop workshop, Instructor instructor){
        List<Workshop> inWorksops = new ArrayList<>();
        for (WorkshopInstructor worIn:extent) {
            if(worIn.instructor==instructor){
                inWorksops.add(worIn.workshop);
            }
        }
        return inWorksops.contains(workshop);
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
