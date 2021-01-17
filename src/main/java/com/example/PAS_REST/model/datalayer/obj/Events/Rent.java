package com.example.PAS_REST.model.datalayer.obj.Events;

import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Rent extends Event{


    private Reader reader;
    private Resource rentedResource;
    private double cachedPricePerDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date dateOfReturn;

    public Rent() {
    }
    public Rent(UUID id, Reader reader, Resource resource, Date dateOfRental,
                Date dateOfReturn) {
        super(id, dateOfRental);
        this.reader = reader;
        this.dateOfReturn = dateOfReturn;
        this.rentedResource = resource;
        this.cachedPricePerDay = resource.getPricePerDay();
    }

    public Rent(UUID id, Date date, Reader reader, Resource resource) {
        super(id, date);
        this.reader = reader;
        this.rentedResource = resource;
        this.cachedPricePerDay = resource.getPricePerDay();
    }

    public Rent(Rent obj){
        super(obj);
        this.reader = new Reader(obj.reader);
        try {
            this.rentedResource = obj.rentedResource.getClass().getConstructor(obj.rentedResource.getClass()).newInstance((obj.rentedResource.getClass().cast(obj.rentedResource)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.dateOfReturn = obj.dateOfReturn;
        this.cachedPricePerDay = obj.cachedPricePerDay;
    }

    public String displayDateOfReturn(){
        if(dateOfReturn != null) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(dateOfReturn);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Rent rent = (Rent) o;

        return this.getId().equals(rent.getId());
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Resource getRentedResource() {
        return rentedResource;
    }

    public void setRentedResource(Resource rentedResource) {
        this.rentedResource = rentedResource;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public Double getCachedPricePerDay() {
        return cachedPricePerDay;
    }

}
