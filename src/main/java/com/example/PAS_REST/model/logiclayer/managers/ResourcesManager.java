package com.example.PAS_REST.model.logiclayer.managers;

import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.Events.Return;
import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.example.PAS_REST.model.datalayer.repositories.EventsRepository;
import com.example.PAS_REST.model.datalayer.repositories.ResourcesRepository;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class ResourcesManager {

    ResourcesRepository resourcesRepository;
    EventsRepository eventsRepository;

    public ResourcesManager(ResourcesRepository resourcesRepository, EventsRepository eventsRepository) {
        this.resourcesRepository = resourcesRepository;
        this.eventsRepository = eventsRepository;
    }

    public ResourcesManager(){ }

    public void addCopyOfBook(Date purchase, double pricePerDay, String title, String author,int pages) throws ExceptionHandler {
        if(purchase.after(Calendar.getInstance().getTime())){
            throw new ExceptionHandler("Future purchase date !");
        }
        if(pricePerDay < 0){
            throw new ExceptionHandler("Price must be positive!");
        }
        if(pages <= 0){
            throw new ExceptionHandler("Pages number must be poitive!");
        }
        resourcesRepository.addCopyOfBook(new Book(UUID.randomUUID() ,purchase, pricePerDay, title, author, pages));
    }

    public void addAudioBook(Date purchase, double pricePerDay, String title, String author, double duration, String lector) throws ExceptionHandler {
        if(purchase.after(Calendar.getInstance().getTime())){
            throw new ExceptionHandler("Future purchase date !");
        }
        if(pricePerDay < 0){
            throw new ExceptionHandler("Price must be positive!");
        }
        if(duration <= 0){
            throw new ExceptionHandler("Duration must be positive!");
        }
        resourcesRepository.addAudioBook(new AudioBook(UUID.randomUUID(), purchase, pricePerDay, title, author, duration, lector));
    }

    public void deleteResource(UUID id) throws ExceptionHandler {
        Resource resource = resourcesRepository.getResource(id);
        if(isResourceRented(resource)){
            throw new ExceptionHandler("This copy of book is already rented!");
        }
        resourcesRepository.deleteResource(id);
    }

    public Boolean isResourceRented(Resource resource){
        if (resource == null)
        {
            throw new IllegalArgumentException("There is no such copy of book");
        }

        List<Resource> rentedCopiesOfBooks = getRentedResources();
        return rentedCopiesOfBooks.contains(resource);
    }

    public List<Resource> getAllResources(){
        return Stream.concat(resourcesRepository.getAllBook().stream(), resourcesRepository.getAllAudioBooks().stream())
                .collect(Collectors.toList());
    }

    public List<Book> getAllCopiesOfBook() {

        return resourcesRepository.getAllBook().stream().map(Book::new).collect(Collectors.toList());
    }

    public List<AudioBook> getAllAudioBooks(){
        return resourcesRepository.getAllAudioBooks().stream().map(AudioBook::new).collect(Collectors.toList());
    }

    public Resource getResource(UUID id){
        Resource resource = resourcesRepository.getResource(id);
        try {
            return resource.getClass().getConstructor(resource.getClass()).newInstance(resource.getClass().cast(resource));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    public Book getBook(UUID id){
        return new Book(resourcesRepository.getBook(id));
    }

    public AudioBook getAudioBook(UUID id){
        return new AudioBook(resourcesRepository.getAudioBook(id));
    }

    public void updateBook(UUID id, Book book) throws ExceptionHandler {
        if(isResourceRented(getResource(id))){
            throw new ExceptionHandler("Cannot modify rented book");
        }
        resourcesRepository.updateCopyOfBook(id, book);
    }

    public void updateAudioBook(UUID id, AudioBook book) throws ExceptionHandler {
        if(isResourceRented(getResource(id))){
            throw new ExceptionHandler("Cannot modify rented book");
        }
        resourcesRepository.updateAudioBook(id, book);
    }

    public List<Rent> getResourceRents(Resource resource){
        if(resource == null){
            throw new IllegalArgumentException("Reader cannot be null");
        }

        return getAllResourcesRents(resource.getId()).stream().map(Rent::new).collect(Collectors.toList());
    }

    public Rent getResourceCurrentRent(Resource resource){
        if(resource == null){
            throw new IllegalArgumentException("Reader cannot be null");
        }
        for(Rent rent : getResourceRents(resource)){
            if(rent.getDateOfReturn() == null){
                return rent;
            }
        }
        return null;
    }

    public List<Resource> getRentedResources() {
        List<Resource> rentedResources = new ArrayList<>();
        for(Rent rent : eventsRepository.getAllRents()){
            if(rent.getDateOfReturn() == null){
                rentedResources.add(rent.getRentedResource());
            }
        }
        for(Return mReturn : eventsRepository.getAllReturns()){
            if(mReturn.getRent().getDateOfReturn() == null){
                rentedResources.remove(mReturn.getReturnedResource());
            }
        }
        return cloneResourcesList(rentedResources);
    }

    public List<Resource> getFreeResources(){
        List<Resource> resources = getAllResources();
        List<Resource> rentedResources = getRentedResources();
        for(Resource resource : rentedResources){
            resources.remove(resource);
        }
        return resources;
    }

    private List<Resource> cloneResourcesList(List<Resource> resources){
        return resources.stream().map(resource -> {
            Resource newResource = null;
            try{
                newResource = resource.getClass().getConstructor(resource.getClass()).newInstance((resource.getClass().cast(resource)));
                return newResource;
            }catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                e.printStackTrace();
            }
            return newResource;
        }).collect(Collectors.toList());
    }

    private List<Rent> getAllResourcesRents(UUID id){
        return eventsRepository.getAllRents().stream().filter(rent -> rent.getRentedResource().equals(resourcesRepository.getResource(id))).collect(Collectors.toList());
    }
    
}
