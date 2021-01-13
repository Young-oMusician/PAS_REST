package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.AudioBookBean;
import com.example.PAS_REST.restapp.beans.BookBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Path("resources")
public class Resources {

    @Inject
    private DataCenter dataCenter;


    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks(){
        return dataCenter.getResourcesManager().getAllCopiesOfBook();
    }
    @GET
    @Path("/audiobooks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AudioBook> getAllAudioBooks(){
        return dataCenter.getResourcesManager().getAllAudioBooks();
    }

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getbook(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        return dataCenter.getResourcesManager().getAudioBook(uuid);
    }
    @GET
    @Path("/audiobooks/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getResource(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        return dataCenter.getResourcesManager().getBook(uuid);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteResuorce(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        try {
            dataCenter.getResourcesManager().deleteResource(uuid);
            return Response.ok().build();
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/books/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookBean book){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(book.purchase);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }
//        if(!title.matches("[A-Z ][AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż ]+")){
//            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable title format").build();
//        }
//        if(!author.matches("[A-Z ][AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż ]+")){
//            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable author name format").build();
//        }
        try {
            dataCenter.getResourcesManager().addCopyOfBook(purchaseDate, book.pricePerDay, book.title, book.author, book.pages);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/audiobooks/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAudioBook(AudioBookBean audioBookBean){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(audioBookBean.purchase);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }
//        if(!title.matches("[A-Z ][AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż ]+")){
//            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable title format").build();
//        }
//        if(!author.matches("[A-Z ][AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż ]+")){
//            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable author name format").build();
//        }
        try {
            dataCenter.getResourcesManager().addAudioBook(purchaseDate, audioBookBean.pricePerDay, audioBookBean.title, audioBookBean.author, audioBookBean.duration,audioBookBean.lector);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }


    @PUT
    @Path("/books/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") String id, BookBean book){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(book.purchase);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }
        UUID uuid = UUID.fromString(id);
        Book updatedBook = new Book(uuid, purchaseDate, book.pricePerDay, book.title, book.author, book.pages);
        try {
            dataCenter.getResourcesManager().updateBook(uuid, updatedBook);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/audiobooks/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAudioBook(@PathParam("id") String id, AudioBookBean audioBookBean){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(audioBookBean.purchase);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }
        UUID uuid = UUID.fromString(id);
        AudioBook updatedAudioBook = new AudioBook(uuid, purchaseDate, audioBookBean.pricePerDay, audioBookBean.title, audioBookBean.author, audioBookBean.duration,audioBookBean.lector);
        try {
            dataCenter.getResourcesManager().updateAudioBook(uuid, updatedAudioBook);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

}
