package com.example.PAS_REST.model.datalayer.repositories;

import com.example.PAS_REST.model.datalayer.interfaces.EventDataFiller;
import com.example.PAS_REST.model.datalayer.obj.Events.Event;
import com.example.PAS_REST.model.datalayer.obj.Events.Payment;
import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.Events.Return;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventsRepository {

    private List<Event> events;
    private EventDataFiller filler;

    public EventsRepository(){
        events = new ArrayList<>();
    }

    public EventsRepository(List<Event> events) {
        this.events = events;
    }

    public synchronized void addEvent(Event eventObject) throws ExceptionHandler {
        if(events.stream().anyMatch(event -> event.equals(eventObject))){
            throw new ExceptionHandler("Event with this ID already exists in repository!");
        }
        events.add(eventObject);
    }

    public synchronized Event getEvent(UUID id) {
        return events.stream()
                .filter(Event -> id.equals(Event.getId()))
                .findAny()
                .orElse(null);
    }

    public synchronized Rent getRent(UUID id){
        for(Event event : events){
            if(Rent.class.equals(event.getClass()) && event.getId().equals(id)){
                return (Rent) event;
            }
        }
        return null;
    }

    public synchronized Return getReturn(UUID id){
        for(Event event : events){
            if(Return.class.equals(event.getClass()) && event.getId().equals(id)){
                return (Return) event;
            }
        }
        return null;
    }

    public synchronized Payment getPayment(UUID id){
        for(Event event : events){
            if(Payment.class.equals(event.getClass()) && event.getId().equals(id)){
                return (Payment) event;
            }
        }
        return null;
    }

    public synchronized List<Event> getAllEvents() {
        return events;
    }

    public synchronized List<Rent> getAllRents(){
        List<Rent> rents = new ArrayList<>();
        for(Event event : events){
            if(event.getClass().equals(Rent.class)){
                rents.add((Rent) event);
            }
        }
        return rents;
    }

    public synchronized List<Rent> getAllCurrentRents() {
        List<Rent> rents = getAllRents();
        for (int i = 0; i < getAllRents().size(); i++) {
            if (getAllRents().get(i).getDateOfReturn() != null) {
                rents.remove(getAllRents().get(i));
            }
        }
        return rents;
    }

    public synchronized List<Return> getAllReturns(){
        List<Return> returns = new ArrayList<>();
        for(Event event : events){
            if(event.getClass().equals(Return.class)){
                returns.add((Return) event);
            }
        }
        return returns;
    }

    public synchronized List<Payment> getAllPayments(){
        List<Payment> payments = new ArrayList<>();
        for(Event event : events){
            if(event.getClass().equals(Payment.class)){
                payments.add((Payment) event);
            }
        }
        return payments;
    }

    public synchronized List<Return> getAllReturnsByRent(UUID id){
        Rent rent = getRent(id);
        List<Return> returns = new ArrayList<>();
        for (Return m_return : getAllReturns()){
            if(m_return.getRent().equals(rent)){
                returns.add(m_return);
            }
        }
        return returns;
    }

    public synchronized void fill(){
        if(filler != null){
            filler.fill(events);
        }
    }

    public synchronized EventDataFiller getFiller() {
        return filler;
    }

    public synchronized void setFiller(EventDataFiller filler) {
        this.filler = filler;
    }
}
