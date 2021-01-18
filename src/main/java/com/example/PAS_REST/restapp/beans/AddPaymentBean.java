package com.example.PAS_REST.restapp.beans;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class AddPaymentBean {
    @NotNull
    @NotEmpty
    @Email
    public String readersEmail;
    @NotNull
    @PositiveOrZero
    public double cash;
}
