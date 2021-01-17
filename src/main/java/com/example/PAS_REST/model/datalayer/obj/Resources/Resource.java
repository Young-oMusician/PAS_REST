package com.example.PAS_REST.model.datalayer.obj.Resources;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public abstract class Resource {
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date purchaseDate;
    private double pricePerDay;

    public Resource() {
    }

    public Resource(UUID id, Date purchaseDate, double pricePerDay){
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.pricePerDay = pricePerDay;
    }

    public Resource(Resource obj){
        this.id = obj.id;
        this.purchaseDate = obj.purchaseDate;
        this.pricePerDay = obj.pricePerDay;
    }

    public String displayPurchaseDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(purchaseDate);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date value){
        this.purchaseDate = value;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double value){
        this.pricePerDay = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return id.equals(resource.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}
