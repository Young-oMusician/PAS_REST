package com.example.PAS_REST.restapp;

import com.example.PAS_REST.initialdata.DataFiller;
import com.example.PAS_REST.initialdata.HRInitialData;
import com.example.PAS_REST.initialdata.ResourcesInitialData;
import com.example.PAS_REST.model.datalayer.repositories.EventsRepository;
import com.example.PAS_REST.model.datalayer.repositories.HRRepository;
import com.example.PAS_REST.model.datalayer.repositories.ResourcesRepository;
import com.example.PAS_REST.model.logiclayer.managers.EventsManager;
import com.example.PAS_REST.model.logiclayer.managers.HRManager;
import com.example.PAS_REST.model.logiclayer.managers.ResourcesManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataCenter {

    private HRRepository hrRepository;
    private ResourcesRepository resourcesRepository;
    private EventsRepository eventsRepository;
    private HRManager _hr;
    private ResourcesManager resourcesManager;
    private EventsManager eventsManager;

    @PostConstruct
    public  void init(){
//        this._dr = new DataRepository();
        this.resourcesRepository = new ResourcesRepository();
        this.hrRepository = new HRRepository();
        this.eventsRepository = new EventsRepository();

        resourcesRepository.setFiller(new ResourcesInitialData());
        resourcesRepository.fill();
        hrRepository.setFiller(new HRInitialData());
        hrRepository.fill();
        DataFiller filler = new DataFiller();
        filler.fill(hrRepository, resourcesRepository, eventsRepository);

        this._hr = new HRManager(hrRepository, eventsRepository);
        this.resourcesManager = new ResourcesManager(resourcesRepository, eventsRepository);
        this.eventsManager = new EventsManager(eventsRepository, hrRepository, resourcesRepository);
    }

    public HRRepository getHrRepository() {
        return hrRepository;
    }

    public void setHrRepository(HRRepository hrRepository) {
        this.hrRepository = hrRepository;
    }

    public ResourcesRepository getResourcesRepository() {
        return resourcesRepository;
    }

    public void setResourcesRepository(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    public EventsRepository getEventsRepository() {
        return eventsRepository;
    }

    public void setEventsRepository(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public HRManager get_hr() {
        return _hr;
    }

    public void set_hr(HRManager _hr) {
        this._hr = _hr;
    }

    public ResourcesManager getResourcesManager() {
        return resourcesManager;
    }

    public void setResourcesManager(ResourcesManager resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public EventsManager getEventsManager() {
        return eventsManager;
    }

    public void setEventsManager(EventsManager eventsManager) {
        this.eventsManager = eventsManager;
    }
}
