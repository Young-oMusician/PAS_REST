package com.example.PAS_REST.restapp.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.*;
import java.util.Date;

public class ReaderBean {
    @NotNull
    @NotEmpty
    @Size(min=11, max=11)
    public String pesel;
    @NotNull
    @NotEmpty
    public String name;
    @NotNull
    @NotEmpty
    public String surname;
    @NotNull
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public String birthDate;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[0-9]{9}$")
    public String phoneNumber;
    @NotNull
    @NotEmpty
    @Email
    public String email;
    @NotNull
    @NotEmpty
    public String gender;
    @NotNull
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public String dateOfRegistration;
    @NotNull
    public Double balance;
    @NotNull
    @NotEmpty
    public String password;
    public boolean active;
}
