package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.Events.Event;
import com.example.PAS_REST.model.datalayer.obj.Events.Payment;
import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.Events.Return;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.AddPaymentBean;
import com.example.PAS_REST.restapp.beans.AddRentBean;
import com.example.PAS_REST.restapp.beans.AddReturnBean;

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
    public List<Event> getAllEvents(){
        return dataCenter.getEventsManager().getAllEvents();
    }

    @GET
    @Path("/rents")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rent> getAllRents(){
        return dataCenter.getEventsManager().getAllRents();
    }

    @GET
    @Path("/returns")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Return> getAllReturns(){
        return dataCenter.getEventsManager().getAllReturns();
    }

    @GET
    @Path("/payments")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> getAllPayments(){
        return dataCenter.getEventsManager().getAllPayments();
    }

    @GET
    @Path("/events/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Event getEvent(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        return dataCenter.getEventsManager().getEvent(uuid);
    }

    @GET
    @Path("/rents/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Rent getRent(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        return dataCenter.getEventsManager().getRent(uuid);
    }

    @GET
    @Path("/returns/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Return getReturn(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        return dataCenter.getEventsManager().getReturn(uuid);
    }

    @GET
    @Path("/payments/{id}")
    @RolesAllowed({"EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayment(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        return dataCenter.getEventsManager().getPayment(uuid);
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
    public Response addReturn(AddReturnBean returnBean){
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
    public Response addPayment(AddPaymentBean payment){

//        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
//        if(!emailPattern.matcher(payment.readersEmail).matches()){
//            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Invalid email format").build();
//        }

        try {
            dataCenter.getEventsManager().addPayment(payment.readersEmail, payment.cash);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }

        return Response.ok().build();
    }
}
