package com.example.PAS_REST.model.datalayer.interfaces;


import com.example.PAS_REST.model.datalayer.repositories.EventsRepository;
import com.example.PAS_REST.model.datalayer.repositories.HRRepository;
import com.example.PAS_REST.model.datalayer.repositories.ResourcesRepository;

public interface IDataFiller {
    void fill(HRRepository hrRepository, ResourcesRepository resourcesRepository, EventsRepository eventsRepository);
}
