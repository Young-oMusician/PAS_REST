package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.Events.Event;
import com.example.PAS_REST.model.datalayer.obj.Events.Payment;
import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.Events.Return;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.AddPaymentBean;
import com.example.PAS_REST.restapp.beans.AddRentBean;
import com.example.PAS_REST.restapp.beans.AddReturnBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


@Path("/events")
public class Events {

    @Inject
    private DataCenter dataCenter;

    //---------------getAll---------------

    @GET
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEvents(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getAllEvents());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/rents")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRents(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getAllRents());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/returns")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReturns(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getAllReturns());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/payments")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPayments(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getAllPayments());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/events/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@Valid @PathParam("id") UUID id){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getEvent(id));
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/rents/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRent(@Valid @PathParam("id") UUID id){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getRent(id));
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/returns/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReturn(@Valid @PathParam("id") UUID id){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getReturn(id));
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/payments/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayment(@Valid @PathParam("id") UUID id){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(dataCenter.getEventsManager().getPayment(id));
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @POST
    @Path("/rents/add")
    @RolesAllowed({"READER"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRent(@Valid AddRentBean rent){
        UUID uuid = UUID.fromString(rent.resourceId);
        try {
            dataCenter.getEventsManager().addRent(rent.readersEmail, uuid);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("/returns/add")
    @RolesAllowed({"READER"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReturn(@Valid AddReturnBean returnBean){
        UUID rentUuid = UUID.fromString(returnBean.rentID);
        UUID resourceUuid = UUID.fromString(returnBean.resourceID);
        try {
            dataCenter.getEventsManager().addReturn(rentUuid, resourceUuid);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }

        return Response.ok().build();
    }
    @POST
    @Path("/payment/add")
    @RolesAllowed({"READER"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPayment(@Valid AddPaymentBean payment){
        try {
            dataCenter.getEventsManager().addPayment(payment.readersEmail, payment.cash);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }

        return Response.ok().build();
    }
}
