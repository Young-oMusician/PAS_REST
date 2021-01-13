package com.example.PAS_REST.model.datalayer.obj.Events;



import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

public class Return extends Event {

    private Resource returnedResource;
    private Rent rent;

    public Return() {
    }

    public Return(UUID id, Date returnDate, Resource resource, Rent rent)
    {
        super(id, returnDate);
        this.returnedResource = resource;
        this.rent = rent;
    }

    public Return(Return obj){
        super(obj);
        try {
            this.returnedResource = obj.returnedResource.getClass().getConstructor(obj.returnedResource.getClass()).newInstance((obj.returnedResource.getClass().cast(obj.returnedResource)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.rent = new Rent(obj.rent);
    }

    public Rent getRent() {
        return rent;
    }

    public Resource getReturnedResource() {
        return returnedResource;
    }

    public void setReturnedResource(Resource returnedResource) {
        this.returnedResource = returnedResource;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
}
