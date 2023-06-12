import enums.Qualifications;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//toedit
public class Instructor extends Roles implements Serializable {
    private List<Qualifications> qualifications;
    private List<WorkshopInstructor> workIns = new ArrayList<>();//Asocjacja Workshop -> Instructor (z atrybutem)

    private static List<Instructor> extent = new ArrayList<>();//Ekstensja
    private static String roleNameGeneralization = "generalization";

    public Instructor(List<Qualifications> qualifications, Person person ) {
        super(person);
        this.qualifications = qualifications;
        addInstructor(this);
    }


    //Elstensja
    private void addInstructor(Instructor instructor){
        extent.add(instructor);
    }
    public void removeInstructor(){
        while (!workIns.isEmpty()){
            workIns.get(0).removeWorkIn(workIns.get(0));
        }
        extent.remove(this);
    }
    public static void showExtent(){
        System.out.println("Extent of the class: " + Instructor.class.getName());

        for (Instructor instructor: extent) {
            System.out.println(instructor);
        }
    }

    //Asocjacja Workshop -> Instructor (z atrybutem)
    public void addWorkIn(WorkshopInstructor workshopInstructor) {
        workIns.add(workshopInstructor);
    }
    public List<WorkshopInstructor> getWorkIns() {
        return workIns;
    }

    //Gettery
    public List<Qualifications> getQualifications() {
        return qualifications;
    }

    //Ekstencja trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Instructor>) stream.readObject();
    }

    @Override
    public String toString() {
        String info = "";
        try {
            info+= getPerson();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(!qualifications.isEmpty()){
            info += "Qualifications:\n";
            for (Qualifications quali: qualifications) {
                info += quali + "\n";
            }
        }
        if(!workIns.isEmpty()){
            info+= "Workshops:\n";
            for (WorkshopInstructor workIn:workIns) {
                info+= workIn.getWorkshop().getName()+ "\n";
            }
        }
        return info;
    }

    @Override
    public String getRole() {
        return this.getClass().getName();
    }
}
