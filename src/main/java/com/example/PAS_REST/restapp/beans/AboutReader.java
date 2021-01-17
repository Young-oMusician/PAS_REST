package com.example.PAS_REST.restapp.beans;

import com.example.PAS_REST.model.datalayer.obj.Events.Rent;

import java.util.Date;
import java.util.List;

public class AboutReader extends AboutPerson {
    public Date dateOfRegistration;
    public Double balance;
    public List<Rent> rentList;
    public boolean active;
}
