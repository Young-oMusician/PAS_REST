package com.example.PAS_REST.model.datalayer.obj.Resources;

import java.util.Date;
import java.util.UUID;

public class AudioBook extends Resource{
    private String title;
    private String author;
    private double duration;
    private String lector;

    public AudioBook(){};

    public AudioBook(UUID id, Date purchaseDate, double pricePerDay, String title, String author, double duration, String lector) {
        super(id, purchaseDate, pricePerDay);
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.lector = lector;
    }

    public AudioBook(AudioBook obj) {
        super(obj);
        this.title = obj.title;
        this.author = obj.author;
        this.duration = obj.duration;
        this.lector = obj.lector;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    @Override
    public String toString() {
        return "Audiobook: " + getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AudioBook audioBook = (AudioBook) o;

        return this.getId().equals(audioBook.getId());
    }
}
