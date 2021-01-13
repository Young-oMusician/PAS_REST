package com.example.PAS_REST.model.datalayer.obj.People;

import java.util.Date;

public class Administrator extends Employee{
    public Administrator(String id, String name, String surname, Date birthDate, String phoneNumber, String email, String gender, Date dateOfEmployment) {
        super(id, name, surname, birthDate, phoneNumber, email, gender, dateOfEmployment);
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
