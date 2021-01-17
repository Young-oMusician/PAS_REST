package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;
import com.example.PAS_REST.model.datalayer.obj.Resources.Resource;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.AudioBookBean;
import com.example.PAS_REST.restapp.beans.BookBean;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


@Path("/resources")
public class Resources {

    @Context
    private Request request;

    @Inject
    private DataCenter dataCenter;

    //---------------getAll---------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resource> getAllResources(){
        return dataCenter.getResourcesManager().getAllResources();
    }

    @GET
    @Path("/books")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks(){
        return dataCenter.getResourcesManager().getAllCopiesOfBook();
    }

    @GET
    @Path("/audiobooks")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<AudioBook> getAllAudioBooks(){
        return dataCenter.getResourcesManager().getAllAudioBooks();
    }

    //---------------getSingle---------------

    @GET
    @Path("/books/{id}")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbook(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        Book book = dataCenter.getResourcesManager().getBook(uuid);
        EntityTag tag = new EntityTag(book.getId().hashCode()+"");
        return Response.ok(book).tag(tag).build();
    }

    @GET
    @Path("/audiobooks/{id}")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResource(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        AudioBook audioBook = dataCenter.getResourcesManager().getAudioBook(uuid);
        EntityTag tag = new EntityTag(audioBook.getId().hashCode()+"");
        return Response.ok(audioBook).tag(tag).build();
    }

    //---------------delete---------------

    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed({"EMPLOYEE"})
    public Response deleteResuorce(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        try {
            dataCenter.getResourcesManager().deleteResource(uuid);
            return Response.ok().build();
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    //---------------add---------------

    @POST
    @Path("/books/add")
    @RolesAllowed({"EMPLOYEE"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookBean book){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(book.purchase);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }
        try {
            dataCenter.getResourcesManager().addCopyOfBook(purchaseDate, book.pricePerDay, book.title, book.author, book.pages);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/audiobooks/add")
    @RolesAllowed({"EMPLOYEE"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAudioBook(AudioBookBean audioBookBean){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(audioBookBean.purchase);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }
        try {
            dataCenter.getResourcesManager().addAudioBook(purchaseDate, audioBookBean.pricePerDay, audioBookBean.title, audioBookBean.author, audioBookBean.duration,audioBookBean.lector);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

    //---------------update---------------

    @PUT
    @Path("/books/update/{id}")
    @RolesAllowed({"EMPLOYEE"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") String id, BookBean book){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(book.purchase);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }
        UUID uuid = UUID.fromString(id);
        Book bookToUpdate = dataCenter.getResourcesManager().getBook(uuid);
        Book updatedBook = new Book(uuid, purchaseDate, book.pricePerDay, book.title, book.author, book.pages);
        Response.ResponseBuilder evaluationResultBuilder = request.evaluatePreconditions(new EntityTag(bookToUpdate.getId().toString()));
        try {
            dataCenter.getResourcesManager().updateBook(uuid, updatedBook);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/audiobooks/update/{id}")
    @RolesAllowed({"EMPLOYEE"})
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
