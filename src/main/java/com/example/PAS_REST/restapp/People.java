package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Person;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.EmployeeAndAdminBean;
import com.example.PAS_REST.restapp.beans.ReaderBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Path("hr")
public class People {

    @Inject
    private DataCenter dataCenter;

    //-----------------getAll----------------

    @GET
    @Path("/readers")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReaders(){
        try {
            String result = new ObjectMapper().writeValueAsString(dataCenter.get_hr().getAllReaders());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/employees")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees(){
        try {
            String result = new ObjectMapper().writeValueAsString(dataCenter.get_hr().getAllEmployees());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/admins")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAdministrators(){
        try {
            String result = new ObjectMapper().writeValueAsString(dataCenter.get_hr().getAllAdministrators());
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    //---------------getSingle---------------

    @GET
    @Path("/readers/{email}")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReader(@PathParam("email") String email){
        try {
            String result = new ObjectMapper().writeValueAsString(dataCenter.get_hr().getReader(email));
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/employees/{email}")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("email") String email){
        try {
            String result = new ObjectMapper().writeValueAsString(dataCenter.get_hr().getEmployee(email));
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    @GET
    @Path("/admins/{email}")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdministrator(@PathParam("email") String email){
        try {
            String result = new ObjectMapper().writeValueAsString(dataCenter.get_hr().getAdministrator(email));
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    //-----------------Create----------------

    @POST
    @Path("/readers/add")
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReader(ReaderBean readerBean){
        Date dateOfBirth = null;
        Date dateOfRegistration = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(readerBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        try {
            dataCenter.get_hr().addReader(readerBean.id,readerBean.name,readerBean.surname,dateOfBirth,readerBean.phoneNumber,
                    readerBean.email,readerBean.gender,dateOfRegistration ,readerBean.balance, readerBean.password);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }
    @POST
    @Path("/employees/add")
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        try {
            dataCenter.get_hr().addEmployee(employeeAndAdminBean.id, employeeAndAdminBean.name, employeeAndAdminBean.surname,dateOfBirth, employeeAndAdminBean.phoneNumber,
                    employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment, employeeAndAdminBean.password);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }
    @POST
    @Path("/admins/add")
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAdmin(EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        try {
            dataCenter.get_hr().addAdministrator(employeeAndAdminBean.id, employeeAndAdminBean.name, employeeAndAdminBean.surname,dateOfBirth, employeeAndAdminBean.phoneNumber,
                    employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment, employeeAndAdminBean.password);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

    //-----------------Update----------------
    @PUT
    @Path("/readers/update/{email}")
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateReader(@PathParam("email") String email, ReaderBean readerBean){
        Date dateOfBirth = null;
        Date dateOfRegistration = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(readerBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Reader updatedReader = new Reader(readerBean.id, readerBean.name, readerBean.surname, dateOfBirth,readerBean.phoneNumber, readerBean.email,readerBean.gender,dateOfRegistration, readerBean.balance, readerBean.password);

        try {
            dataCenter.get_hr().updateReader(email,updatedReader);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();

    }
    @PUT
    @Path("/employees/update/{email}")
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("email") String email, EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Employee updatedEmployee = new Employee(employeeAndAdminBean.id, employeeAndAdminBean.name, employeeAndAdminBean.surname, dateOfBirth, employeeAndAdminBean.phoneNumber, employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment, employeeAndAdminBean.password);

        try {
            dataCenter.get_hr().updateEmployee(email,updatedEmployee);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();

    }

    @PUT
    @Path("/admins/update/{email}")
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAdmin(@PathParam("email") String email, EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Administrator updatedAdmin = new Administrator(employeeAndAdminBean.id, employeeAndAdminBean.name, employeeAndAdminBean.surname, dateOfBirth, employeeAndAdminBean.phoneNumber, employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment, employeeAndAdminBean.password);

        try {
            dataCenter.get_hr().updateAdministrator(email,updatedAdmin);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();

    }

    //---------------other---------------

    @PUT
    @Path("/activate/{email}")
    @RolesAllowed({"ADMIN"})
    public Response activatePerson(@PathParam("email") String email){
        try {
            dataCenter.get_hr().activatePerson(email);
            return Response.ok().build();
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }
    }
}
