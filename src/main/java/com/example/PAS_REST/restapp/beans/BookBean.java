package com.example.PAS_REST.restapp.beans;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookBean {
    @NotNull
    @NotEmpty
    public String purchase;
    @NotNull
    public double pricePerDay;
    @NotNull
    @NotEmpty
    public String title;
    @NotNull
    @NotEmpty
    public String author;
    @NotNull
    public int pages;
}
