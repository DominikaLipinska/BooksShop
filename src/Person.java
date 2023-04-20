import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Person extends ObjectPlusPlus implements Serializable {
    private String firstName;
    private String latsName;
    private  String phoneNumber;
    private String email;
    private Adres adres;

    private static String roleNameClient = "specializationClient";
    private static String roleNameEmployee = "specializationEmployee";
    private static String roleNameAuthor = "specializationAuthor";
    private static String roleNameInstructor = "specializationInstructor";
    private static String roleNameGeneralization = "generalization";


    private Person(String firstName, String latsName) {
        super();
        this.firstName = firstName;
        this.latsName = latsName;
    }
    private Person(String firstName, String latsName, String phoneNumber, String email, Adres adres) {
        this(firstName,latsName);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.adres = adres;
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
        Client c = new Client(loyaltyCard);
        this.addLink(roleNameClient,roleNameGeneralization,c);
    }
    public void addAuthor(String pubHouse){
        Author a = new Author(pubHouse);
        this.addLink(roleNameAuthor,roleNameGeneralization,a);
    }
    public void addEmployee(LocalDate empDate,float sallary,Float supSuplement){
        Employee e = new Manager(empDate,sallary,supSuplement);
        this.addLink(roleNameEmployee,roleNameGeneralization,e);
    }
    public void addEmployee(LocalDate empDate,float sallary, Integer overtimeHours){
        Employee e = new Salesman(empDate,sallary,overtimeHours);
        this.addLink(roleNameEmployee,roleNameGeneralization,e);
    }
    public void addInstructor(List<String> qualifications){
        Instructor i = new Instructor(qualifications);
        this.addLink(roleNameInstructor,roleNameGeneralization,i);
    }

    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        if(hasLoyaltyCard()!= null){ roles.add("Client"); }
        if (hasPubHouse()!=null){ roles.add("Author"); }
        if (hasOvertimeHours()!= null || hasSupSuplement()!= null){
            try {
                roles.add("Employee(" +this.getEmployee().getClass().getName()+")");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (hasQualifications()!=null){ roles.add("Instructor"); }

        return roles;
    }
    public Author getAuthor() throws Exception{
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameAuthor);
            return (Author)obj[0];
        }catch (Exception e){
            throw new Exception("The object is not author");
        }
    }
    public Client getClient() throws Exception{
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameClient);
            return (Client)obj[0];
        }catch (Exception e){
            throw new Exception("The object is not client");
        }
    }
    public Employee getEmployee() throws Exception{
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameEmployee);
            return (Employee)obj[0];
        }catch (Exception e){
            throw new Exception("The object is not employee");
        }
    }
    public Instructor getInctructor() throws Exception{
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameInstructor);
            return (Instructor)obj[0];
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
