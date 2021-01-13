package com.example.PAS_REST.restapp.beans;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmployeeAndAdminBean {
    @NotNull
    @NotEmpty
    public String id;
    @NotNull
    @NotEmpty
    public String name;
    @NotNull
    @NotEmpty
    public String surname;
    @NotNull
    @NotEmpty
    public String birthDate;
    @NotNull
    @NotEmpty
    public String phoneNumber;
    @NotNull
    @NotEmpty
    public String email;
    @NotNull
    @NotEmpty
    public String gender;
    @NotNull
    @NotEmpty
    public String dateOfEmployment;
}
