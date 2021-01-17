package com.example.PAS_REST.restapp.auth;

import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Person;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.DataCenter;
import com.example.PAS_REST.restapp.beans.AboutEmployeeOrAdmin;
import com.example.PAS_REST.restapp.beans.AboutPerson;
import com.example.PAS_REST.restapp.beans.AboutReader;
import com.example.PAS_REST.restapp.beans.LoginBean;
import com.fasterxml.jackson.core.util.JsonParserDelegate;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Status;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.security.enterprise.SecurityContext;

import static javax.ws.rs.core.Response.Status.*;

@Path("/auth")
public class authenticateControler{

    private static final Logger LOGGER = Logger.getLogger(authenticateControler.class.getName());

    @Inject
    private SecurityContext securityContext;

    @Inject
    private DataCenter dataCenter;

    @Inject
    private JWTGenerator jwtGenerator;

    @Inject
    private AuthenticationIdentityStore authenticationIdentityStore;

    @Inject
    private HttpServletRequest request;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login() throws IOException, ServletException {
            if(securityContext.getCallerPrincipal() == null){
                return Response.status(UNAUTHORIZED).build();
            }
            Person loggingUser = dataCenter.get_hr().getPerson(securityContext.getCallerPrincipal().getName());
            if(loggingUser == null) {
                return Response.status(UNAUTHORIZED).build();
            }
            if(!loggingUser.isActive()) {
                return Response.status(FORBIDDEN).build();
            }
            String token = jwtGenerator.createToken(loggingUser.getEmail(), Collections.singleton(loggingUser.getRole()));
            return Response.ok().entity(Json.createObjectBuilder().add("token", token).build()).build();
    }


    @GET
    @Path("/extendToken")
    @RolesAllowed({"READER", "EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response extendToken(@HeaderParam("Authorization") @NotBlank String token){
        String exToken = token.substring(6, token.length());
        JWTCredential credential = jwtGenerator.getCredential(exToken);
        String newToken = jwtGenerator.createToken(credential.getPrincipal(), credential.getAuthorities());
        return Response.ok(Json.createObjectBuilder().add("token", newToken).build()).build();
    }

    @GET
    @Path("/myProfile")
    @RolesAllowed({"READER", "EMPLOYEE", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response myProfile(){
        if(securityContext.getCallerPrincipal() != null){
            AboutPerson me = wrapPersonInfo(dataCenter.get_hr().getPerson(securityContext.getCallerPrincipal().getName()));
            return Response.ok(me).build();
        }else{
            return Response.ok(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/rent/{id}")
    @RolesAllowed({"READER"})
    public Response rent(@PathParam("id") UUID id){
        if(securityContext.getCallerPrincipal() != null) {
            try {
                dataCenter.getEventsManager().addRent(securityContext.getCallerPrincipal().getName(), id);
                return Response.ok().build();
            } catch (ExceptionHandler exceptionHandler) {
                return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
            }
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @POST
    @Path("/return/{id}")
    @RolesAllowed({"READER"})
    public Response returnRent(@PathParam("id") UUID id){
        if(securityContext.getCallerPrincipal() != null) {
            Rent rent = dataCenter.getEventsManager().getRent(id);
            if(rent != null) {
                try {
                    dataCenter.getEventsManager().addReturn(rent.getId(), rent.getRentedResource().getId());
                    return Response.ok().build();
                } catch (ExceptionHandler exceptionHandler) {
                    return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage())
                            .build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(),
                    "Rent with this ID does not exits!")
                    .build();
        }
        return Response.status(Status.STATUS_UNKNOWN).build();
    }

    @POST
    @Path("/payment/{cash}")
    @RolesAllowed({"READER"})
    public Response pay(@PathParam("cash") @Positive double cash){
        if(securityContext.getCallerPrincipal() != null){
            try {
                dataCenter.getEventsManager().addPayment(securityContext.getCallerPrincipal().getName(), cash);
                return Response.ok().build();
            } catch (ExceptionHandler exceptionHandler) {
                return Response.status(CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
            }
        }
        return Response.status(UNAUTHORIZED).build();
    }

    private AboutPerson wrapPersonInfo(Person person){
        if(person.getClass().equals(Reader.class)){
            Reader reader = (Reader)person;
            AboutReader aboutReader = new AboutReader();
            aboutReader.pesel = reader.getPesel();
            aboutReader.name = reader.getName();
            aboutReader.surname = reader.getSurname();
            aboutReader.birthDate = reader.getBirthDate();
            aboutReader.phoneNumber = reader.getPhoneNumber();
            aboutReader.email = reader.getEmail();
            aboutReader.gender = reader.getGender();
            aboutReader.active = reader.isActive();
            aboutReader.role = reader.getRole();
            aboutReader.balance = reader.getBalance();
            aboutReader.dateOfRegistration = reader.getDateOfRegistration();
            aboutReader.rentList = dataCenter.get_hr().getReadersRents(reader.getEmail());
            return aboutReader;
        }
        if(person.getClass().equals(Employee.class) || person.getClass().equals(Administrator.class)){
            Employee employee = (Employee)person;
            AboutEmployeeOrAdmin aboutEmployeeOrAdmin = new AboutEmployeeOrAdmin();
            aboutEmployeeOrAdmin.pesel = employee.getPesel();
            aboutEmployeeOrAdmin.name = employee.getName();
            aboutEmployeeOrAdmin.surname = employee.getSurname();
            aboutEmployeeOrAdmin.birthDate = employee.getBirthDate();
            aboutEmployeeOrAdmin.phoneNumber = employee.getPhoneNumber();
            aboutEmployeeOrAdmin.email = employee.getEmail();
            aboutEmployeeOrAdmin.gender = employee.getGender();
            aboutEmployeeOrAdmin.active = employee.isActive();
            aboutEmployeeOrAdmin.role = employee.getRole();
            aboutEmployeeOrAdmin.dateOfEmployement = employee.getDateOfEmployment();
            return aboutEmployeeOrAdmin;
        }

        return null;
    }
}