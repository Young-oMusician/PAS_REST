package com.example.PAS_REST.restapp.beans;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddRentBean {
    @NotNull
    @NotEmpty
    @Email
    public String readersEmail;
    @NotNull
    @NotEmpty
    public String resourceId;
}
