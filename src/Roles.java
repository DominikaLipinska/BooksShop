import java.util.List;

public abstract class Roles {
    Person person;

    public Roles(Person person) {
        this.person = person;
    }

    public String getRole(){
        return this.getClass().getName();
    }

    public Person getPerson() {
        return person;
    }
}
