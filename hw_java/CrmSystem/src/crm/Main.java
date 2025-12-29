package crm;

import crm.model.Contact;
import crm.model.Lead;
import crm.model.Status;

public class Main {
    public static void main(String[] args) {
        Contact contact1 = new Contact(1, "Иван", "asd@mail.ru", "+798182391");
        Contact contact2 = new Contact(2, "Максим", "asasdd@mail.ru", "+5123455");
        
        Lead lead1 = new Lead(1, 74213, Status.NEW, "Google", "25.07.2024");
        Lead lead2 = new Lead(2, 51234, Status.IN_PROGRESS, "WebSite", "25.01.2042");
        Lead lead3 = new Lead(3, 12311, Status.CLOSED, "Phone", "24.03.2052");
    System.out.println(contact1);
    System.out.println(contact2);

    System.out.println(lead1);
    System.out.println(lead2);
    System.out.println(lead3);

        contact1.setName("Гей");
        System.out.println("Новое имя первого контакта:" + contact1.getName());

        lead1.setStatus(Status.IN_PROGRESS);
        System.out.println("Новый статус лида 1: " + lead1.getStatus());


    }
}