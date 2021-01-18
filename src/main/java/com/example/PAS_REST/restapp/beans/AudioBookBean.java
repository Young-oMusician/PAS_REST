package com.example.PAS_REST.restapp.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

public class AudioBookBean {
    @NotNull
    @NotEmpty
    public String id;
    @NotNull
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public String purchaseDate;
    @NotNull
    @PositiveOrZero
    public double pricePerDay;
    @NotNull
    @NotEmpty
    public String title;
    @NotNull
    @NotEmpty
    public String author;
    @NotNull
    @PositiveOrZero
    public double duration;
    @NotNull
    @NotEmpty
    public String lector;

}
