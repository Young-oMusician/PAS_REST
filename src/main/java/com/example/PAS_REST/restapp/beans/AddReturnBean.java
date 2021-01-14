package com.example.PAS_REST.restapp.beans;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddReturnBean {
    @NotNull
    @NotEmpty
    public String rentID;
    @NotNull
    @NotEmpty
    public String resourceID;

}
