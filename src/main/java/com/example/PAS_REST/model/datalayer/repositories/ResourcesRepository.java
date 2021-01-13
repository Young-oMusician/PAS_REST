package com.example.PAS_REST.model.datalayer.repositories;

import com.example.PAS_REST.initialdata.ResourcesInitialData;
import com.example.PAS_REST.model.datalayer.interfaces.ResourceDataFiller;
import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourcesRepository {

    private List<Book> books;
    private List<AudioBook> audioBooks;
    private ResourceDataFiller filler;

    public ResourcesRepository(){

        books = new ArrayList<>();
        audioBooks = new ArrayList<>();
    }

    public ResourcesRepository(List<Book> books, List<AudioBook> audioBooks){
        this.books = books;
        this.audioBooks = audioBooks;
    }

    public synchronized void addCopyOfBook(Book book) throws ExceptionHandler {
        if(book.getId() == null){
            throw new ExceptionHandler("Book ID cannot be null");
        }
        if(getResource(book.getId()) != null){
            throw new ExceptionHandler("Book with ID already exists in database!");
        }
        books.add(book);
    }

    public synchronized void addAudioBook(AudioBook audioBook) throws ExceptionHandler {
        if(audioBook.getId() == null){
            throw new ExceptionHandler("AudioBook ID cannot be null");
        }
        if(getResource(audioBook.getId()) != null){
            throw new ExceptionHandler("AudioBook with ID already exists in database!");
        }
        audioBooks.add(audioBook);
    }

    public synchronized void deleteResource(UUID id) {
        Book bookResource = books.stream().filter(copyOfBook -> copyOfBook.getId().equals(id)).findFirst().orElse(null);
        if(bookResource == null){
            AudioBook audioBookResource = audioBooks.stream().filter(audioBook -> audioBook.getId().equals(id)).findFirst().orElse(null);
            audioBooks.remove(audioBookResource);
            return;
        }
        books.remove(bookResource);
    }

    public synchronized Resource getResource(UUID id){
        Resource resource =books.stream().filter(copyOfBook -> copyOfBook.getId().equals(id)).findFirst().orElse(null);
        if(resource == null){
            resource = audioBooks.stream().filter(audioBook -> audioBook.getId().equals(id)).findFirst().orElse(null);
            return resource;
        }
        return resource;
    }

    public synchronized Book getBook(UUID id) {
        return books.stream()
                .filter(CopyOfBook -> id.equals(CopyOfBook.getId()))
                .findAny()
                .orElse(null);
    }

    public synchronized AudioBook getAudioBook(UUID id) {
        return audioBooks.stream()
                .filter(CopyOfBook -> id.equals(CopyOfBook.getId()))
                .findAny()
                .orElse(null);
    }

    public synchronized List<Book> getAllBook() {
        return books;
    }

    public synchronized List<AudioBook> getAllAudioBooks(){
        return audioBooks;
    }

    public synchronized void updateCopyOfBook(UUID id, Book book) {
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getId().equals(id)){
                books.set(i, book);
            }
        }
    }

    public synchronized void updateAudioBook(UUID id, AudioBook audioBook){
        for(int i = 0; i < audioBooks.size(); i++){
            if(audioBooks.get(i).getId().equals(id)){
                audioBooks.set(i, audioBook);
            }
        }
    }

    public synchronized ResourceDataFiller getFiller() {
        return filler;
    }

    public synchronized void setFiller(ResourceDataFiller filler) {
        this.filler = filler;
    }

    public synchronized void fill(){
        if(filler != null){
            filler.fill(books, audioBooks);
        }
    }
}
