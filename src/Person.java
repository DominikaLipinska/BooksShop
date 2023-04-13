import java.io.Serializable;
import java.time.LocalDate;

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


    public Person(String firstName, String latsName) {
        super();
        this.firstName = firstName;
        this.latsName = latsName;
    }
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres) {
        this(firstName,latsName);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.adres = adres;
    }
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres,boolean loyaltyCard ) {
        this(firstName,latsName,phoneNumber,email,adres);
        addClient(loyaltyCard);
    }
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
        Client c = new Client(this.firstName, this.latsName, this.phoneNumber,this.email, this.adres );
        this.addLink(roleNameClient,roleNameGeneralization,c);
    }
    public void addAuthor(String pubHouse){
        Author a = new Author(this.firstName,this.latsName);
        this.addLink(roleNameAuthor,roleNameGeneralization,a);
    }
    public void addEmployee(LocalDate empDate,float sallary,Float supSuplement){
        Employee e = new Manager(this.firstName,this.latsName,this.phoneNumber,this.email,this.adres,empDate,sallary,supSuplement);
        this.addLink(roleNameEmployee,roleNameGeneralization,e);
    }
    public void addEmployee(LocalDate empDate,float sallary, int overtimeHour){
        Employee e = new Salesman(this.firstName,this.latsName,this.phoneNumber,this.email,this.adres,empDate,sallary,overtimeHour);
        this.addLink(roleNameEmployee,roleNameGeneralization,e);
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLatsName() {
        return latsName;
    }

    public void setLatsName(String latsName) {
        this.latsName = latsName;
    }

    @Override
    public String toString() {
        String result = this.getClass().getName() +": "+ firstName + " " + latsName ;
        if(phoneNumber!= null){
            result += " " + phoneNumber;
        }
        if(email!= null){
            result += " email: " + email;
        }
        if(adres!= null){
            result += " " + adres;
        }
        result+="\n";

        return result;
    }
}
