package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.AudioBookBean;
import com.example.PAS_REST.restapp.beans.BookBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Path("/resources")
public class Resources {

    @Context
    private Request request;

    @Inject
    private DataCenter dataCenter;

    //---------------getAll---------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllResources(){
        ObjectMapper mapper = new ObjectMapper();

        try {
            String result = mapper.writeValueAsString(dataCenter.getResourcesManager().getAllResources());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/books")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getResourcesManager().getAllCopiesOfBook());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/audiobooks")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAudioBooks(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getResourcesManager().getAllAudioBooks());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }

    }

    //---------------getSingle---------------

    @GET
    @Path("/books/{id}")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbook(@Valid @PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        Book book = dataCenter.getResourcesManager().getBook(uuid);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Response.ok(mapper.writeValueAsString(book)).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/audiobooks/{id}")
    @RolesAllowed({"ADMIN", "EMPLOYEE", "READER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResource(@Valid @PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        AudioBook audioBook = dataCenter.getResourcesManager().getAudioBook(uuid);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Response.ok(mapper.writeValueAsString(audioBook)).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    //---------------delete---------------

    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed({"EMPLOYEE"})
    public Response deleteResuorce(@Valid @PathParam("id") String id){
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
    public Response addBook(@Valid BookBean book){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(book.purchaseDate);
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
    public Response addAudioBook(@Valid AudioBookBean audioBookBean){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(audioBookBean.purchaseDate);
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
    public Response updateBook(@Valid @PathParam("id") String id, BookBean book){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(book.purchaseDate);
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
    @RolesAllowed({"EMPLOYEE"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAudioBook(@Valid @PathParam("id") String id, AudioBookBean audioBookBean){
        Date purchaseDate = null;
        try {
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(audioBookBean.purchaseDate);
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
