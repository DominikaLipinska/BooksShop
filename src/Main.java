import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final String extentFile = "extent.ser";

        Adres adres1 = new Adres("City2","Street1",12);

        Person author1 = new Person("NameA1","SurnameA1","167-346-145","namea1surnamea1@gmail.com",adres1,
                "PubHouse1");
        Person author2 = new Person("NameA2", "SurnameA2","641-427-885","namea2surnamea2@gmail.com",adres1,
                "PubHouse2");

        author1.addClient(true);
        author1.addInstructor(new ArrayList<>(Arrays.asList("Quali2", "Quali3")));

        Award award1 = new Award("Award1", 2023);
        Award award2 = new Award("Award2",2015);
        Award award3 = new Award("Award3",2020);

        try {
            author2.getAuthor().addAward(award1);
            author1.getAuthor().addAward(award1);
            author1.getAuthor().addAward(award2);
            author2.getAuthor().addAward(award3);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        /*Book book1 = new Book("51245","T1",author1,2020,12);
        Book book2 = new Book("12345","T2",author2,2023,30,0.2);*/


        Person client1 = new Person("NameC1","SurnameC1","123-456-148",
                "namec1surnamec1@gmail.com",new Adres("City1","Street1",10),true);

        Person client2 = new Person("NameC2","SurnameC2","153-678-748",
                "namec2surnamec2@gmail.com",new Adres("City1","Street1",20),false);

        /*Lists list1 = client1.addList("List1");
        Lists list2 = new Lists("List2",client1);
        Lists list3 = client1.addList("List3");

        client2.addList("List4");
        client2.addList("List5");*/

        /*//Asocjacja "Zwykła"
        Client.showExtent();
        Lists.showExtent();

        System.out.println("**************************REMOVE_LIST1******************************");
        list1.removeList();
        Client.showExtent();
        Lists.showExtent();

        System.out.println("**************************CLIENT1_REMOVE_LIST2******************************");
        client1.removeList(list2);
        Client.showExtent();
        Lists.showExtent();

        System.out.println("**************************REMOVE_CLIENT2******************************");
        client2.removeClient();
        Client.showExtent();
        Lists.showExtent();*/

        Person manager = new Person("NameM1","SurnameM1","245-548-145","namem1surnamem1@gmail.com",adres1,
                LocalDate.of(2008,12,6),4956.25f,250f);
        Person salesman1 = new Person("NameS1","SurnameS1","543-178-164","names1surnames1@gmail.com",adres1,
                LocalDate.of(2012,6,14),3956.25f,10);
        Person salesman2 = new Person("NameS2","SurnameS2","393-164-147","names2surnames2@gmail.com",adres1,
                LocalDate.of(2012,6,14),3906.85f,6);


        Workshop workshop1 = new Workshop("WorshopName1", LocalDateTime.now(),120);
        Workshop workshop2 = new Workshop("WorkshopName2",LocalDateTime.now(),200);

        Person instructor1 = new Person("NameI1", "SurnameI1","645-497-145","namei1surnamei1@gmail.com",adres1,
                new ArrayList<>(Arrays.asList("Quali1", "Quali2", "Quali3")));
        Person instructor2 = new Person("NameI2", "SurnameI2","784-986-152","namei2surnamei2@gmail.com",adres1,
                new ArrayList<>(Arrays.asList("Quali4")));

        instructor1.addClient(false);

        /*WorkshopInstructor workshopInstructor1 = new WorkshopInstructor(1000,workshop1,instructor1);
        WorkshopInstructor workshopInstructor2a = new WorkshopInstructor(600,workshop2,instructor1);
        WorkshopInstructor workshopInstructor2b = new WorkshopInstructor(600,workshop2,instructor2);*/

        //Asocjacja z atrybutem
        /*
        Instructor.showExtent();
        Workshop.showExtent();

        System.out.println("**************************REMOVE_INSTRUCTOR1******************************");
        instructor1.removeInstructor();

        Instructor.showExtent();
        Workshop.showExtent();

        System.out.println("**************************REMOVE_WORKSHOP2******************************");
        workshop2.removeWorkshop();

        Instructor.showExtent();
        Workshop.showExtent();*/

        /*list1.addBookQualif(book1);
        list1.addBookQualif(book2);
        list2.addBookQualif(book1);
        list3.addBookQualif(book2);
        list3.addBookQualif(book2);*/

        //Asocjacja kwalifikowana
        /*
        Lists.showExtent();
        Book.showExtent();

        System.out.println("**************************REMOVE_BOOK2******************************");
        book2.removeBook();

        Lists.showExtent();
        Book.showExtent();

        System.out.println("**************************REMOVE_LIST1******************************");
        list1.removeList();

        Lists.showExtent();
        Book.showExtent();*/

        //Kompozycja
        /*
        try {
            Chapter chapter1 = book1.createChapter("Chapter1");
            Chapter chapter2 = book1.createChapter("Chapter2");
            Chapter chapter3 = book1.createChapter("Chapter3");
            Chapter chapter =  book2.createChapter("Chapter");
            Book.showExtent();

            System.out.println("**************************REMOVE_CHAPTER******************************");
            book2.removeChapter(chapter);

            Book.showExtent();

            System.out.println("**************************REMOVE_BOOK******************************");
            book1.removeBook();

            Book.showExtent();

            //book1.showChapters();
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/

        /*Author.showExtent();
        Award.showExtent();

        System.out.println("**************************REMOVE_AUTHOR1******************************");
        author1.removeAuthor();

        Author.showExtent();
        Award.showExtent();

        System.out.println("**************************REMOVE_AWARD1******************************");
        award1.removeAward();

        Author.showExtent();
        Award.showExtent();*/


        try{
            var out = new ObjectOutputStream(new FileOutputStream(extentFile));
            Book.writeExtent(out);
            Author.writeExtent(out);
            Workshop.writeExtent(out);
            Salesman.writeExtent(out);
            out.close();

            var in = new ObjectInputStream(new FileInputStream(extentFile));
            Book.readExtent(in);
            Author.readExtent(in);
            Workshop.readExtent(in);
            Salesman.readExtent(in);
            in.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        Author.showExtent();
        Client.showExtent();
        Salesman.showExtent();
        Manager.showExtent();

    }
}
