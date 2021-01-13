package com.example.PAS_REST.model.datalayer.obj.People;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {

    private String pesel;
    private String name;
    private String surname;
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private String gender;
    private boolean active;

    public Person() {
    }

    public Person(String id, String name, String surname, Date birthDate, String phoneNumber, String email, String gender) {
        this.pesel = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
    }

    public Person(Person obj){
        this.pesel = obj.pesel;
        this.name = obj.name;
        this.surname = obj.surname;
        this.birthDate = obj.birthDate;
        this.phoneNumber = obj.phoneNumber;
        this.email = obj.email;
        this.gender = obj.gender;
        this.active = obj.active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return pesel.hashCode();
    }

    @Override
    public String toString() {
        return getEmail();
    }

    public String displayBirthDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(getBirthDate());
    }

    public String getPesel() {
        return pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
