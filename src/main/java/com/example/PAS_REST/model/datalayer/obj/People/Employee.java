package com.example.PAS_REST.model.datalayer.obj.People;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee extends Person {

    private Date dateOfEmployment;

    public Employee(String id, String name, String surname, Date birthDate,
                    String phoneNumber, String email, String gender, Date dateOfEmployment)
    {
        super(id,name,surname,birthDate,phoneNumber,email,gender);
        this.dateOfEmployment = dateOfEmployment;
        setActive(true);
    }

    public Employee(Employee obj){
        super(obj);
        this.dateOfEmployment = obj.dateOfEmployment;
    }

    public Employee(){}

    public String displayDateOfEmployment(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(getDateOfEmployment());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Employee employee = (Employee) o;

        return this.getPesel() == employee.getPesel();
    }

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }
}
