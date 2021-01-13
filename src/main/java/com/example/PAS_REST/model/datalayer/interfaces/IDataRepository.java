package com.example.PAS_REST.model.datalayer.interfaces;

import com.example.PAS_REST.model.datalayer.obj.Events.Event;
import com.example.PAS_REST.model.datalayer.obj.Events.Payment;
import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.Events.Return;
import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;

import java.util.List;
import java.util.UUID;

public interface IDataRepository {



    // CopyOfBook methods

    void addCopyOfBook(Book book) throws ExceptionHandler;
    void addAudioBook(AudioBook audioBook) throws ExceptionHandler;
    void deleteResource(UUID id);
    Resource getResource(UUID id);
    Book getCopyOfBook(UUID id);
    AudioBook getAudioBook(UUID id);
    List<Book> getAllCopiesOfBook();
    List<AudioBook> getAllAudioBooks();
    List<Rent> getAllResourcesRents(UUID id);
    void updateCopyOfBook(UUID id, Book book);
    void updateAudioBook(UUID id, AudioBook audioBook);
//    CopyOfBook FindCopyOfBook(Predicate<CopyOfBook> parameter);

    // Administrator methods

    void addAdministrator(Administrator administrator) throws ExceptionHandler;
    void deleteAdministrator(Administrator administrator);
    Administrator getAdministrator(String email);
    List<Administrator> getAllAdministrators();
    void updateAdministrator(String email, Administrator administrator) throws ExceptionHandler;

    // Employee methods

    void addEmployee(Employee employee) throws ExceptionHandler;
    void deleteEmployee(Employee employee);
    Employee getEmployee(String email);
    List<Employee> getAllEmployees();
    void updateEmployee(String email, Employee employee) throws ExceptionHandler;
//    Employee FindEmployee(Predicate<Employee> parameter);

    //Readers methods

    void addReader(Reader reader) throws ExceptionHandler;
    void deleteReader(Reader reader);
    Reader getReader(String email);
    List<Reader> getAllReaders();
    List<Rent> getReadersRents(String email);
    void updateReader(String email, Reader reader) throws ExceptionHandler;
//    Reader FindReader(Predicate<Reader> parameter);

    //Event methods

    void addEvent(Event eventObject) throws ExceptionHandler;
    Event getEvent(UUID id);
    List<Event> getAllEvents();
    List<Rent> getAllRents();
    List<Rent> getAllCurrentRents();
    Rent getRent(UUID id);
    List<Return> getAllReturns();
    List<Return> getAllReturnsByRent(UUID id);
    List<Payment> getAllPayments();
//    Event FindEvent(Predicate<Event> parameter);
}
