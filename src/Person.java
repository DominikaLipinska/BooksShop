import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Person implements Serializable {
    private String firstName;
    private String latsName;
    private  String phoneNumber;
    private String email;
    private Adres adres; //Asocjacja Adres -> Person (1-*)
    private Map<String,Roles> roles = new HashMap<>();

    private static List<Person> extent = new ArrayList<>(); //Ekstensja

    private static String roleNameClient = "specializationClient";
    private static String roleNameEmployee = "specializationEmployee";
    private static String roleNameAuthor = "specializationAuthor";
    private static String roleNameInstructor = "specializationInstructor";

    //Ekstensja
    private void addPerson(Person person){
        if(!extent.contains(person)){
            extent.add(person);
        }
    }
    public void removePerson(){
        //removeRoles
        if(adres!=null){
            adres.removePerson(this);
        }
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Person.class.getName());

        for (Person person : extent) {
            System.out.println(person);
        }
    }

    //Ekstensja Trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Person>) stream.readObject();
    }

    private Person(String firstName, String latsName) {
        super();
        this.firstName = firstName;
        this.latsName = latsName;
        addPerson(this);
    }
    private Person(String firstName, String latsName, String phoneNumber, String email, Adres adres) {
        this(firstName,latsName);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.adres = adres;
        adres.addPerson(this); //Asocjacja Adres -> Person (1-*)
        addPerson(this);
    }

    //Client
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres,boolean loyaltyCard ) {
        this(firstName,latsName,phoneNumber,email,adres);
        addClient(loyaltyCard);
    }
    //Author
    public Person(String firstName, String latsName,String phoneNumber,String email, Adres adres, String pubHouse) {
        this(firstName,latsName,phoneNumber,email,adres);
        addAuthor(pubHouse);
    }
    //Manager
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate,float sallary,Float sapSuplement) {
        this(firstName,latsName,phoneNumber,email,adres);
        addEmployee(empDate,sallary,sapSuplement);
    }
    //Salesman
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate,float sallary,int overtimeHour) {
        this(firstName,latsName,phoneNumber,email,adres);
        addEmployee(empDate,sallary,overtimeHour);
    }
    //Instructor
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres, List<String> qualifications) {
        this(firstName,latsName,phoneNumber,email,adres);
        addInstructor(qualifications);
    }


    public void addClient(boolean loyaltyCard) {
        Client c = new Client(loyaltyCard,this);
        try {
            addRole(roleNameClient,c);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void addAuthor(String pubHouse){
        Author a = new Author(pubHouse,this);
        try {
            addRole(roleNameAuthor,a);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void addEmployee(LocalDate empDate,float sallary,Float supSuplement){
        Employee e = new Manager(empDate,sallary,supSuplement,this);
        try {
            addRole(roleNameEmployee,e);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void addEmployee(LocalDate empDate,float sallary, Integer overtimeHours){
        Employee e = new Salesman(empDate,sallary,overtimeHours,this);
        try {
            addRole(roleNameEmployee,e);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void addInstructor(List<String> qualifications){
        Instructor i = new Instructor(qualifications,this);
        try {
            addRole(roleNameInstructor,i);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private void addRole(String roleName, Roles roleObject) throws Exception {
        if(roles.get(roleName)==null){
            roles.put(roleName,roleObject);
        }else{
            throw new Exception("This person is already " + roleObject.getRole());
        }
    }
    public void setRole(String roleName,Roles roleObject) throws Exception {
        Roles previos = roles.replace(roleName,roleObject);
        if(previos==null){
            throw new Exception("This person not have this role!");
        }
    }

    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        for (Roles role:this.roles.values()) {
            roles.add(role.getRole());
        }
        return roles;
    }
    public Author getAuthor() throws Exception{
        try {
            Roles author = roles.get(roleNameAuthor);
            return (Author) author;
        }catch (Exception e){
            throw new Exception("The object is not author");
        }
    }
    public Client getClient() throws Exception{
        try {
            Roles client = roles.get(roleNameClient);
            return (Client)client ;
        }catch (Exception e){
            throw new Exception("The object is not client");
        }
    }
    public Employee getEmployee() throws Exception{
        try {
            Roles employee = roles.get(roleNameEmployee);
            return (Employee) employee ;
        }catch (Exception e){
            throw new Exception("The object is not employee");
        }
    }
    public Instructor getInctructor() throws Exception{
        try {
            Roles instructor= roles.get(roleNameClient);
            return (Instructor) instructor;
        }catch (Exception e){
            throw new Exception("The object is not instructor");
        }
    }

    public Boolean hasLoyaltyCard(){
        try {
            return getClient().isLoyaltyCard();
        }catch (Exception e){
            return null;
        }
    }
    public String hasPubHouse(){
        try {
            return getAuthor().getPubHouse();
        }catch (Exception e){
            return null;
        }
    }
    public Float hasSupSuplement(){
        try {
            return ((Manager)getEmployee()).getSalSupplement();
        }catch (Exception e){
            return null;
        }
    }
    public Integer hasOvertimeHours(){
        try {
            return ((Salesman)getEmployee()).getOvertimeHours();
        }catch (Exception e){
            return null;
        }
    }
    public List<String> hasQualifications(){
        try {

            return getInctructor().getQualifications();
        }catch (Exception e){
            return null;
        }
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLatsName() {
        return latsName;
    }
    public String getName(){
        return firstName +" "+latsName;
    }
    public Adres getAdres() {
        return adres;
    }

    @Override
    public String toString() {
        String result = this.getClass().getName() + "( ";

        for (String role:getRoles()) {
            result += role + " ";
        }

        result +="): "+ firstName + " " + latsName +"\n";

        if(phoneNumber!= null){ result += "phone number: " + phoneNumber +"\n";}
        if(email!= null){ result += "email: " + email +"\n"; }
        if(adres!= null){ result += "adres: " + adres +"\n"; }
        if(hasLoyaltyCard()!=null){ result += "has loyalty card: " + hasLoyaltyCard() +"\n"; }
        if(hasPubHouse()!=null){ result += "publishing house: " + hasPubHouse() +"\n"; }
        if (hasSupSuplement()!=null){ result += "supSuplement: " + hasSupSuplement() + "\n"; }
        if (hasOvertimeHours()!=null){ result += "overtime hours: " + hasOvertimeHours() + "\n"; }
        if (hasQualifications()!=null) { result += "qualificatins: " + hasQualifications() + "\n"; }

        return result;
    }
}
