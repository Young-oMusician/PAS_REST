package com.example.PAS_REST.model.datalayer;

import com.example.PAS_REST.model.datalayer.obj.Events.Event;
import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;

import java.util.ArrayList;
import java.util.List;

public class LibraryContext {


    private List<Book> copiesOfBooks;
    private List<AudioBook> audiobooks;
    private List<Administrator> administrators;
    private List<Employee> employees;
    private List<Reader> readers;
    private List<Event> events;

    public LibraryContext()
    {

        copiesOfBooks = new ArrayList<>();
        audiobooks = new ArrayList<>();
        administrators = new ArrayList<>();
        employees = new ArrayList<>();
        readers = new ArrayList<>();
        events = new ArrayList<>();
    }

//    @PostConstruct
//    public  void init(){
//        books = new ArrayList<Book>();
//        Book hobbit = new Book(UUID.randomUUID() ,"Hobbit, czyli tam i z powrotem",new Author(UUID.randomUUID(), "Fiodor", "Dostojewski"), "Powiesc fantasy dla dzieci autorstwa J.R.R. Tolkiena.", Book.BookType.Fantasy);
//        Book zik = new Book( UUID.randomUUID(),"Zbrodnia i Kara",new Author(UUID.randomUUID(), "John Ronald Reuel", "Tolkien"), "Tematem powiesci sa losy bylego studenta, Rodiona Raskolnikowa, ktory postanawia zamordowac i obrabowac stara lichwiarke.", Book.BookType.Classics);
//        books.add(hobbit);
//        books.add(zik);
//        authors = new ArrayList<Author>();
//        Author tolkien = new Author(UUID.randomUUID(), "John Ronald Reuel", "Tolkien");
//        authors.add(tolkien);
//        Author fDostojewski = new Author(UUID.randomUUID(), "Fiodor", "Dostojewski");
//        authors.add(fDostojewski);

//    }


    public List<Book> getCopiesOfBooks() {
        return copiesOfBooks;
    }

    public List<AudioBook> getAudiobooks() {
        return audiobooks;
    }

    public List<Administrator> getAdministrators() {
        return administrators;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public List<Event> getEvents() {
        return events;

    }
}
