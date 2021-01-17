package com.example.PAS_REST.model.datalayer.obj.People;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties({"password"})
public class Administrator extends Employee{
    public Administrator(String id, String name, String surname, Date birthDate, String phoneNumber, String email, String gender, Date dateOfEmployment, String password) {
        super(id, name, surname, birthDate, phoneNumber, email, gender, dateOfEmployment, password);
        super.setRole("ADMIN");
    }

    public Administrator(Administrator obj) {
        super(obj);
    }

    public Administrator(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return true;
    }
}
