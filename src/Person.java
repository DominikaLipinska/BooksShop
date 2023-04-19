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
    public Person(String firstName, String latsName, String pubHouse) {
        this(firstName,latsName);
        addAuthor(pubHouse);
    }


    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate,float sallary,Float sapSuplement) {
        this(firstName,latsName,phoneNumber,email,adres);
        addEmployee(empDate,sallary,sapSuplement);
    }
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres, LocalDate empDate,float sallary,int overtimeHour) {
        this(firstName,latsName,phoneNumber,email,adres);
        addEmployee(empDate,sallary,overtimeHour);
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

    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        if(hasLoyaltyCard()!= null){
            roles.add("Client");
        }
        if (hasPubHouse()!=null){
            roles.add("Author");
        }
        if (hasOvertimeHours()!= null || hasSupSuplement()!= null){
            roles.add("Employee");
        }
        return roles;
    }

    public Boolean hasLoyaltyCard(){
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameClient);
            return ((Client)obj[0]).isLoyaltyCard();
        }catch (Exception e){
            return null;
        }
    }
    public String hasPubHouse(){
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameAuthor);
            return ((Author)obj[0]).getPubHouse();
        }catch (Exception e){
            return null;
        }
    }
    public Float hasSupSuplement(){
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameEmployee);
            return ((Manager)obj[0]).getSalSupplement();
        }catch (Exception e){
            return null;
        }
    }
    public Integer hasOvertimeHours(){
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameEmployee);
            return ((Salesman)obj[0]).getOvertimeHours();
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

        return result;
    }
}
