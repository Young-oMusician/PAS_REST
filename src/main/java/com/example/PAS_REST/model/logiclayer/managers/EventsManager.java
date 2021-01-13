package com.example.PAS_REST.model.logiclayer.managers;

import com.example.PAS_REST.model.datalayer.obj.Events.Event;
import com.example.PAS_REST.model.datalayer.obj.Events.Payment;
import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.Events.Return;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.example.PAS_REST.model.datalayer.repositories.EventsRepository;
import com.example.PAS_REST.model.datalayer.repositories.HRRepository;
import com.example.PAS_REST.model.datalayer.repositories.ResourcesRepository;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventsManager {

    EventsRepository eventsRepository;
    HRRepository hrRepository;
    ResourcesRepository resourcesRepository;

    public EventsManager(EventsRepository eventsRepository, HRRepository hrRepository, ResourcesRepository resourcesRepository) {
        this.eventsRepository = eventsRepository;
        this.hrRepository = hrRepository;
        this.resourcesRepository = resourcesRepository;
    }

    public List<Event> getAllEvents(){
        return eventsRepository.getAllEvents().stream().map(event -> {
            Event newEvent = null;
            try {
                newEvent = event.getClass().getConstructor(event.getClass()).newInstance((event.getClass().cast(event)));
                return newEvent;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return newEvent;
        }).collect(Collectors.toList());
    }

    public Event getEvent(UUID id){
        Event event = eventsRepository.getEvent(id);
        try {
            return event.getClass().getConstructor(event.getClass()).newInstance(event.getClass().cast(event));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    public void addRent(String readersEmail, UUID resourceId) throws ExceptionHandler {
        if(readersEmail == null){
            throw new IllegalArgumentException("Reader cannot be null");
        }
        if(resourceId == null){
            throw new IllegalArgumentException("Books list cannot be null");
        }
        Reader reader = hrRepository.getReader(readersEmail);
        if(reader == null){
            throw new ExceptionHandler("Reader with this id doesn't exist!");
        }
        Resource resource = resourcesRepository.getResource(resourceId);
        if(resource == null){
            throw new ExceptionHandler("Resource with this ID doesn't exist!");
        }
        if(!hrRepository.getReader(reader.getEmail()).isActive()){
            throw new ExceptionHandler("This reader isn't active!");
        }
        if(isResourceRented(resource.getId())){
            throw new ExceptionHandler("Resource is already rented!");
        }
        if(!isReaderClear(reader)){
            throw new ExceptionHandler("This reader has already rented resource!");
        }
        if(reader.getBalance() < 0){
            throw new ExceptionHandler("Readers with negative balance aren't allowed to rent books");
        }
        eventsRepository.addEvent(new Rent(UUID.randomUUID(),reader, resource, new Date(),null));
    }

    public List<Rent> getAllRents() {
        List<Rent> rents = new ArrayList<Rent>();
        for(int i = 0; i< eventsRepository.getAllEvents().size(); i++){
            if(Rent.class.equals(eventsRepository.getAllEvents().get(i).getClass())){
                rents.add((Rent) eventsRepository.getAllEvents().get(i));
            }
        }
        return rents.stream().map(Rent::new).collect(Collectors.toList());
    }

    public List<Rent> getAllCurrentRents() {

        return eventsRepository.getAllCurrentRents().stream().map(Rent::new).collect(Collectors.toList());
    }

    public Rent getRent(UUID id){
        return new Rent(eventsRepository.getRent(id));
    }

    public void addReturn(UUID rentID, UUID resourceID) throws ExceptionHandler {
        if(rentID == null){
            throw new IllegalArgumentException("Rent cannot be null");
        }
        if(resourceID == null){
            throw new IllegalArgumentException("Books list cannot be null");
        }
        Rent rent = eventsRepository.getRent(rentID);
        if(rent == null){
            throw new ExceptionHandler("Rent doesn't exists in repository!");
        }
        Resource resource = resourcesRepository.getResource(resourceID);
        if(resource == null){
            throw new ExceptionHandler("Resource doesn't exists in repository!");
        }
        if(rent.getDateOfReturn() != null){
            throw new ExceptionHandler("This rent is finalized!");
        }
        if(!resource.equals(rent.getRentedResource())){
            throw new ExceptionHandler("Resource don't concern given rent");
        }
        eventsRepository.addEvent(new Return(UUID.randomUUID(), new Date(),resource, rent));
        charge(resource, rent);
        finalzeRent(rent);
    }

    public List<Return> getAllReturns() {
        List<Return> returns = new ArrayList<Return>();
        for(int i = 0; i< eventsRepository.getAllEvents().size(); i++){
            if(Return.class.equals(eventsRepository.getAllEvents().get(i).getClass())){
                returns.add((Return) eventsRepository.getAllEvents().get(i));
            }
        }
        return returns.stream().map(Return::new).collect(Collectors.toList());
    }

    public List<Return> getAllReturnsByRent(Rent rent) {

        return eventsRepository.getAllReturnsByRent(rent.getId()).stream().map(Return::new).collect(Collectors.toList());
    }

    public Return getReturn(UUID id){
        return new Return(eventsRepository.getReturn(id));
    }

    public void addPayment(String readersEmail, double cash) throws ExceptionHandler {
        if(readersEmail == null){
            throw new IllegalArgumentException("Reader cannot be null");
        }
        Reader reader = hrRepository.getReader(readersEmail);
        if(reader == null){
            throw new ExceptionHandler("Reader with this email doesn't exist!");
        }
        if(cash <= 0){
            throw new ExceptionHandler("Cash must be positive number");
        }
        Reader originalReader = hrRepository.getReader(reader.getEmail());
        eventsRepository.addEvent(new Payment(UUID.randomUUID(),new Date(), originalReader,cash));
        originalReader.setBalance(originalReader.getBalance() + cash);
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<Payment>();
        for(int i = 0; i< eventsRepository.getAllEvents().size(); i++){
            if(Payment.class.equals(eventsRepository.getAllEvents().get(i).getClass())){
                payments.add((Payment) eventsRepository.getAllEvents().get(i));
            }
        }
        return payments.stream().map(Payment::new).collect(Collectors.toList());
    }

    public Payment getPayment(UUID id){
        return new Payment(eventsRepository.getPayment(id));
    }

    private boolean isResourceRented(UUID id){
        Resource resource = resourcesRepository.getResource(id);
        List<Resource> rentedCopies = new ArrayList<>();
        for(Rent rent : eventsRepository.getAllRents()){
            rentedCopies = Stream.concat(rentedCopies.stream(), getRentedResources(rent).stream())
                    .collect(Collectors.toList());
        }

        return rentedCopies.contains(resource);
    }


    private void charge(Resource resource, Rent rent){
        Duration period = Duration.between(rent.getDate().toInstant(), new Date().toInstant());
        double cost = rent.getCachedPricePerDay() * period.toDays();
        rent.getReader().setBalance(rent.getReader().getBalance() - cost);
    }

    private void finalzeRent(Rent rent){
        if(getRentedResources(rent).size() == 0){
            rent.setDateOfReturn(new Date());
        }
    };

    private List<Resource> getRentedResources(Rent rent) {
        List<Resource> rentedCopiesOfBooks = new ArrayList<>();
        rentedCopiesOfBooks.add(rent.getRentedResource());
        List<Return> returns = eventsRepository.getAllReturnsByRent(rent.getId());
        for(Return m_return : returns){
            rentedCopiesOfBooks.remove(m_return.getReturnedResource());
        }
        return rentedCopiesOfBooks;
    }

    private boolean isReaderClear(Reader reader){
        return getAllRents().stream().noneMatch(rent -> rent.getReader().equals(reader) && rent.getDateOfReturn() == null);
    }
}
