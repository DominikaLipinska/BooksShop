import enums.Qualifications;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final String extentFile = "extent.ser";
        try {
            Address address1 = new Address("City1","Street1",12);
            Address address2 = new Address("City1","Street2",23);
            Address address3 = new Address("City2","Street3",4,8);

            Person author1 = new Person("NameA1","SurnameA1","167-346-145","namea1surnamea1@gmail.com", address1, "PubHouse1");
            Person author2 = new Person("NameA2", "SurnameA2",null,null,null, "PubHouse2");

            author1.addClient(true);
            author1.addInstructor(new ArrayList<>(Arrays.asList(Qualifications.WRITING,Qualifications.RECITATION)));

            Award award1 = new Award("Award1");
            Award award2 = new Award("Award2");
            Award award3 = new Award("Award3");


            //addAuthAward
            AuthorAward authAward1 = new AuthorAward(5000, author1.getAuthor(), award1);
            AuthorAward authAward2 = new AuthorAward(4500, author1.getAuthor(), award2);
            AuthorAward authAward3 = new AuthorAward(5000, author2.getAuthor(), award1);
            AuthorAward authAward4 = new AuthorAward(4500, author2.getAuthor(), award2);
            AuthorAward authAward5 = new AuthorAward(4500, author1.getAuthor(), award2);

            authAward1.removeAuthAward();

            Book book1 = new Book("51245","Title1",author1.getAuthor(),2020,12f);
            Book book2 = new Book("12345","Title2",author2.getAuthor(),2023,30f,0.2);
            Book book3 = new Book("12367","Title3",author2.getAuthor(),2022,30f);

            BookWithFilms bookWF1 = new BookWithFilms("12536","TitleWF1",author2.getAuthor(),2016,100,"DVD");
            KidsBook kidsBook1 = new KidsBook("12467","TitleKB1", author1.getAuthor(),2020,30f,false );
            KidsBookWithFilms kidsBookWF1 = new KidsBookWithFilms("30142","TitleKBWF1",author1.getAuthor(),2018,40f,"DVD",true);

            Film film1 = Film.createFilm(bookWF1,"TitleF1",120);
            Series series1 = Series.createSeries(book1,"TitleS1",4);

            Person client1 = new Person("NameC1","SurnameC1","123-456-148", "namec1surnamec1@gmail.com",new Address("City1","Street1",10),true);
            Person client2 = new Person("NameC2","SurnameC2","153-678-748", "namec2surnamec2@gmail.com",new Address("City1","Street1",20),false);

            Lists list1 = client1.getClient().addList("List1");
            Lists list2 = new Lists("List2",client1.getClient());
            Lists list3 = client1.getClient().addList("List3");


            list1.addBookQualif(book1);
            list1.addBookQualif(book2);
            list2.addBookQualif(book1);
            list3.addBookQualif(book2);
            list3.addBookQualif(book2);

            Lists list4 = client2.getClient().addList("List4");
            Lists list5 = client2.getClient().addList("List5");


            list4.addBookQualif(book3);
            Lists.showExtent();

            //System.out.println(client1.getClient());
            //System.out.println(client1.getAddress());


            Person manager = new Person("NameM1","SurnameM1","245-548-145","namem1surnamem1@gmail.com", address1, LocalDate.of(2008,12,6),4956.25f,250f);
            Person salesman1 = new Person("NameS1","SurnameS1","543-178-164","names1surnames1@gmail.com", address1, LocalDate.of(2012,6,14),3956.25f,10);
            Person salesman2 = new Person("NameS2","SurnameS2","393-164-147","names2surnames2@gmail.com", address1, LocalDate.of(2012,6,14),3906.85f,6);



            manager.getEmployee().changeRole(10);
            salesman1.getEmployee().changeRole(250.0f);
            //salesman2.getEmployee().changeRole(5);

            Shift shift1 = new Shift("Pierwsza",Arrays.asList(manager.getEmployee(),salesman1.getEmployee(),salesman2.getEmployee()),(Manager)salesman1.getEmployee());

            /*Shift.showExtent();
            System.out.println("*************************");
            shift1.removeEmp(salesman2.getEmployee());
            Shift.showExtent();
            System.out.println("*************************");
            shift1.addEmp(salesman2.getEmployee());
            Shift.showExtent();
            //shift1.removeEmp(salesman1.getEmployee());*/

            Workshop workshop1 = new Workshop("WorshopName1", LocalDateTime.now(),120);
            Workshop workshop2 = new Workshop("WorkshopName2",LocalDateTime.now(),200);

            Person instructor1 = new Person("NameI1", "SurnameI1","645-497-145","namei1surnamei1@gmail.com", address1,
                    new ArrayList<>(Arrays.asList(Qualifications.BOOKBINDING,Qualifications.WRITING,Qualifications.RECITATION)));
            Person instructor2 = new Person("NameI2", "SurnameI2","784-986-152","namei2surnamei2@gmail.com", address1,
                    new ArrayList<>(Arrays.asList(Qualifications.WRITING)));

            instructor1.addClient(false);

            AuthorsMeeting meeting1 = new AuthorsMeeting(author1.getAuthor(), "NameAM1",LocalDateTime.now().minusDays(3).minusMinutes(150),150,true);
            ThemeParty party1 = new ThemeParty("NameTP1", LocalDateTime.now().minusHours(150).minusMinutes(48),30,"TopicP1");


            Order order1 = list1.createOrder();
            Order order2 = list2.createOrder();
            //Order order4 = list4.createOrder();

            //System.out.println(order1);
            //Order order2 = new Order(client1.getClient(), Arrays.asList(bookWF1,book2,bookWF1));
            //Order order3 = new Order(client2.getClient(), Arrays.asList(kidsBookWF1,kidsBook1));
            //Order order4 = new Order(author1.getClient(), Arrays.asList(book2,kidsBookWF1));

            client1.getClient().cancelOrder(order1);

            //Manager.showAllOrdersLists();
            salesman1.getEmployee().takeOrder();
            //salesman1.getEmployee().takeOrder();

            //Manager.showAllOrdersLists();
            //client1.getClient().cancelOrder(order1);
            salesman1.getEmployee().completeOrder();
            //salesman1.getEmployee().cancelOrder(order1);

            //Manager.showAllOrdersLists();

            /*salesman1.getEmployee().getOrders();
            salesman2.getEmployee().getOrders();
            System.out.println("************************");
            salesman1.getEmployee().takeOrder();

            salesman1.getEmployee().getOrders();
            salesman2.getEmployee().getOrders();
            System.out.println("************************");
            salesman2.getEmployee().takeOrder();

            salesman1.getEmployee().getOrders();
            salesman2.getEmployee().getOrders();
            System.out.println("************************");
            salesman1.getEmployee().completeOrder();

            salesman1.getEmployee().getOrders();
            salesman2.getEmployee().getOrders();
            System.out.println("************************");
            salesman2.getEmployee().takeOrder();

            salesman1.getEmployee().getOrders();
            salesman2.getEmployee().getOrders();
            System.out.println("************************");
            salesman2.getEmployee().completeOrder();

            salesman1.getEmployee().getOrders();
            salesman2.getEmployee().getOrders();
            System.out.println("************************");
            salesman2.getEmployee().completeOrder();

            salesman1.getEmployee().getOrders();
            salesman2.getEmployee().getOrders();
            System.out.println("************************");
            Employee.showAllOrdersLists();
            System.out.println("************************");

            Order order = salesman1.getEmployee().takeOrder();
            salesman1.getEmployee().getOrders();
            System.out.println("************************");
            salesman1.getEmployee().cancelOrder(order);
            Employee.showAllOrdersLists();
            System.out.println("************************");*/

        } catch (Exception exception) {
            exception.printStackTrace();
        }


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

            /*var in = new ObjectInputStream(new FileInputStream(extentFile));
            Book.readExtent(in);
            Author.readExtent(in);
            Workshop.readExtent(in);
            Salesman.readExtent(in);
            in.close();*/
        }catch (IOException /*| ClassNotFoundException*/ e){
            e.printStackTrace();
        }


    }
}
