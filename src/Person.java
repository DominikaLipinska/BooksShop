import java.io.Serializable;

abstract class Person implements Serializable {
    private String firstName;
    private String latsName;
    private  String phoneNumber;
    private String email;
    private Adres adres;

    public Person(String firstName, String latsName) {
        this.firstName = firstName;
        this.latsName = latsName;
    }
    public Person(String firstName, String latsName, String phoneNumber, String email, Adres adres) {
        this(firstName,latsName);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.adres = adres;
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
