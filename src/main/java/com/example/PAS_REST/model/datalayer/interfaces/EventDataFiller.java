package com.example.PAS_REST.model.datalayer.interfaces;



import com.example.PAS_REST.model.datalayer.obj.Events.Event;

import java.util.List;

public interface EventDataFiller {
    void fill(List<Event> events);
}