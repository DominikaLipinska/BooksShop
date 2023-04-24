import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client extends Roles implements Serializable {
    private List<Order> historyOrders = new ArrayList<>();
    private List<Lists> lists = new ArrayList<>(); //Asocja Client -> Lists (1-*)
    private boolean loyaltyCard;

    private static List<Client> extent = new ArrayList<>(); //Ekstensja
    private static String roleNameGeneralization = "generalization";

    public Client(boolean loyaltyCard,Person person) {
        super(person);
        this.loyaltyCard = loyaltyCard;
        addClient(this);
    }


    //Ekstensja
    private void addClient(Client client){
        extent.add(client);
    }
    public void removeClient(){
        while(!lists.isEmpty()){
            lists.get(0).removeList();
        }
        extent.remove(this);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Client.class.getName());

        for (Client client : extent) {
            System.out.println(client);
        }
    }

    //Gettery

    //"zwykła"
    public Lists addList(String name){
        Lists newlist = new Lists(name,this);
        return addList(newlist);
    }
    public Lists addList(Lists newList){
        if(!lists.contains(newList)){
            lists.add(newList);
            return newList;
        }
        return lists.get(lists.indexOf(newList));
    }
    public void removeList(Lists list){
        if (lists.contains(list)){
            lists.remove(list);
            list.removeList();
        }

    }

    public boolean isLoyaltyCard(){
        return loyaltyCard;
    }

    //Ekstensja Trwałość
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Client>) stream.readObject();
    }

    @Override
    public String toString() {
        String info ="";
        try {
            info+=getPerson();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        info+="Your saved lists: \n";
        if(lists.isEmpty()){
            info+="You don't have saved lists yet\n";
        }else {
            for (Lists list:lists) {
                info+=list;
            }
        }
        info+="Order History:\n";
        if(historyOrders.isEmpty()){
            info+="You don't have old orders\n";
        }else {
            for (Order order : historyOrders) {
                info+=order+"\n";
            }
        }
        return info;
    }


}
