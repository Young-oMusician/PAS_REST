package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.Resources.Book;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.model.logiclayer.managers.ResourcesManager;
import com.example.PAS_REST.restapp.beans.AddBookBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resource> getAllResources(){
        return dataCenter.getResourcesManager().getAllResources();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getResource(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        return dataCenter.getResourcesManager().getResource(uuid);
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
    @Path("/add/book")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(AddBookBean book){
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


    @PUT
    @Path("/update/book/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") String id, AddBookBean book){
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

}
