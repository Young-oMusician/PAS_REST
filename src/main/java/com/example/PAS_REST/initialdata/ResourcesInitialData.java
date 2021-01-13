package com.example.PAS_REST.initialdata;


import com.example.PAS_REST.model.datalayer.interfaces.ResourceDataFiller;
import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class ResourcesInitialData implements ResourceDataFiller {
    @Override
    public void fill(List<Book> books, List<AudioBook> audioBooks) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2017, Calendar.FEBRUARY, 4);
        Book book1 = new Book(UUID.randomUUID(), cal1.getTime(), 1.0, "Nad Niemnem", "Eliza Orzeszkowa", 399);
        cal1.set(2010, Calendar.SEPTEMBER, 20);
        Book book2 = new Book(UUID.randomUUID(), cal1.getTime(), 1.5, "Mistrz i Małgorzata", "Michaił Bułchakow", 552);
        cal1.set(2012, Calendar.OCTOBER, 20);
        Book book3 = new Book(UUID.randomUUID(), cal1.getTime(), 2.0, "Antygona", "Sofokles", 80);
        cal1.set(2003, Calendar.JULY, 23);
        Book book4 = new Book(UUID.randomUUID(), cal1.getTime(), 2.5, "Władca Much", "William Golding", 296);
        cal1.set(2014, Calendar.DECEMBER, 7);
        Book book5 = new Book(UUID.randomUUID(), cal1.getTime(), 3.0, "Folwark Zwierzęcy", "George Orwell", 136);
        cal1.set(2004, Calendar.MARCH, 12);
        Book book6 = new Book(UUID.randomUUID(), cal1.getTime(), 0.5, "Imię Róży", "Umberto Eco", 756);

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);

        cal1.set(2011, Calendar.SEPTEMBER, 2);
        AudioBook audioBook1 = new AudioBook(UUID.randomUUID(), cal1.getTime(), 5.0, "Ecce Homo", "Friedrich Nietzsche", 136, "Jarosław Dudek");
        cal1.set(2017, Calendar.MAY, 14);
        AudioBook audioBook2 = new AudioBook(UUID.randomUUID(), cal1.getTime(), 6.0, "Dzieci z Bullerbyn", "Astrid Lindgren", 420, "Edyta Jungowska");
        cal1.set(2010, Calendar.NOVEMBER, 29);
        AudioBook audioBook3 = new AudioBook(UUID.randomUUID(), cal1.getTime(), 5.0, "Zbrodnia i Kara", "Fiodor Dostojewski", 1258, "Filip Kosior");

        audioBooks.add(audioBook1);
        audioBooks.add(audioBook2);
        audioBooks.add(audioBook3);
    }
}
