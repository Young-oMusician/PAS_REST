package com.example.PAS_REST.restapp.beans;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddPaymentBean {
    @NotNull
    @NotEmpty
    public String readersEmail;
    @NotNull
    public double cash;
}
