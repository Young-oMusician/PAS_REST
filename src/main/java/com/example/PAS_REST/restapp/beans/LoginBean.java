package com.example.PAS_REST.restapp.beans;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginBean {
    @NotNull
    @NotEmpty
    @Email
    public String email;
    @NotNull
    @NotEmpty
    public String password;
}
