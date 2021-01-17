package com.example.PAS_REST.model.datalayer.obj.People;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties({"password"})
public class Reader extends Person {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfRegistration;

    private double balance;

    public Reader() {
        super();
    }

    public Reader(String id, String name, String surname, Date birthDate, String phoneNumber, String email,
                  String gender, Date dateOfRegistration, Double balance, String password) {
        super(id, name, surname, birthDate, phoneNumber, email, gender, password, "READER");
        this.dateOfRegistration = dateOfRegistration;
        this.balance = balance;
    }

    public Reader(String id, String name, String surname, Date birthDate, String phoneNumber, String email,
                  String gender, Date dateOfRegistration, String password) {
        super(id, name, surname, birthDate, phoneNumber, email, gender, password, "READER");
        this.dateOfRegistration = dateOfRegistration;
        this.balance = 0.0;
    }

    public Reader(Reader obj){
        super(obj);
        this.dateOfRegistration = obj.dateOfRegistration;
        this.balance = obj.balance;
    }

    public String displayDateOfRegistration(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(getDateOfRegistration());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Reader reader = (Reader) o;

        return this.getPesel().equals(reader.getPesel());
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
