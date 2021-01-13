package com.example.PAS_REST.model.datalayer.obj.Events;

import com.example.PAS_REST.model.datalayer.obj.People.Reader;

import java.util.Date;
import java.util.UUID;

public class Payment extends Event {

    private double cash;
    private Reader reader;

    public Payment(UUID id, Date paymentDate, Reader reader, double cash)
    {
        super(id, paymentDate);
        this.cash = cash;
        this.reader = reader;
    }

    public Payment() {
    }

    public Payment(Payment obj){
        super(obj);
        this.cash = obj.cash;
        this.reader = new Reader(obj.reader);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Payment payment = (Payment) o;

        return this.getId().equals(payment.getId());
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
