package com.example.PAS_REST.model.datalayer.obj.Resources;

import java.util.Date;
import java.util.UUID;

public class Book extends Resource{
    private String title;
    private String author;
    private int pages;

    public Book() {

    }

    public Book(UUID id, Date purchaseDate, double pricePerDay, String title, String author, int pages)
    {
        super(id ,purchaseDate, pricePerDay);
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public Book(Book obj){
        super(obj);
        this.title = obj.title;
        this.author = obj.author;
        this.pages = obj.pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book that = (Book) o;

        return getId().equals(that.getId());
    }

    @Override
    public String toString() {
        return "Book: " + getTitle();
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
