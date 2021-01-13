package com.example.PAS_REST.initialdata;

import com.example.PAS_REST.model.datalayer.interfaces.IDataFiller;
import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.Events.Return;
import com.example.PAS_REST.model.datalayer.repositories.EventsRepository;
import com.example.PAS_REST.model.datalayer.repositories.HRRepository;
import com.example.PAS_REST.model.datalayer.repositories.ResourcesRepository;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;

import java.util.Calendar;
import java.util.UUID;

public class DataFiller implements IDataFiller {
    @Override
    public void fill(HRRepository hrRepository, ResourcesRepository resourcesRepository, EventsRepository eventsRepository) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.set(2020, Calendar.OCTOBER, 4);
        Rent rent1 = new Rent(UUID.randomUUID(), cal1.getTime(), hrRepository.getAllReaders().get(0), resourcesRepository.getAllBook().get(0));
        cal1.set(2020, Calendar.DECEMBER, 1);
        Rent rent2 = new Rent(UUID.randomUUID(), cal1.getTime(), hrRepository.getAllReaders().get(1), resourcesRepository.getAllBook().get(1));
        cal1.set(2020, Calendar.JUNE, 1);
        cal2.set(2020, Calendar.JUNE, 1);
        Rent rent3 = new Rent(UUID.randomUUID(), hrRepository.getAllReaders().get(2), resourcesRepository.getAllAudioBooks().get(0),cal1.getTime(), cal2.getTime());
        Return returnRent3 = new Return(UUID.randomUUID(), cal2.getTime(), resourcesRepository.getAllAudioBooks().get(0), rent3);
        try {
            eventsRepository.addEvent(rent1);
            eventsRepository.addEvent(rent2);
            eventsRepository.addEvent(rent3);
            eventsRepository.addEvent(returnRent3);
        }catch (ExceptionHandler exceptionHandler) {
            exceptionHandler.printStackTrace();
        }
    }
}
