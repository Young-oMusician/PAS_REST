package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.EmployeeAndAdminBean;
import com.example.PAS_REST.restapp.beans.ReaderBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public Response getReader(@Valid @PathParam("email") String email){
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
    public Response getEmployee(@Valid @PathParam("email") String email){
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
    public Response getAdministrator(@Valid @PathParam("email") String email){
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
    public Response addReader(@Valid ReaderBean readerBean){
        Date dateOfBirth = null;
        Date dateOfRegistration = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(readerBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        try {
            dataCenter.get_hr().addReader(readerBean.pesel,readerBean.name,readerBean.surname,dateOfBirth,readerBean.phoneNumber,
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
    public Response addEmployee(@Valid EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        try {
            dataCenter.get_hr().addEmployee(employeeAndAdminBean.pesel, employeeAndAdminBean.name, employeeAndAdminBean.surname,dateOfBirth, employeeAndAdminBean.phoneNumber,
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
    public Response addAdmin(@Valid EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        try {
            dataCenter.get_hr().addAdministrator(employeeAndAdminBean.pesel, employeeAndAdminBean.name, employeeAndAdminBean.surname,dateOfBirth, employeeAndAdminBean.phoneNumber,
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
    public Response updateReader(@Valid @PathParam("email") String email, ReaderBean readerBean){
        Date dateOfBirth = null;
        Date dateOfRegistration = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(readerBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Reader updatedReader = new Reader(readerBean.pesel, readerBean.name, readerBean.surname,
                dateOfBirth,readerBean.phoneNumber, readerBean.email,readerBean.gender,dateOfRegistration,
                readerBean.balance, readerBean.password);
        updatedReader.setActive(readerBean.active);

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
    public Response updateEmployee(@Valid @PathParam("email") String email, EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Employee updatedEmployee = new Employee(employeeAndAdminBean.pesel, employeeAndAdminBean.name, employeeAndAdminBean.surname, dateOfBirth, employeeAndAdminBean.phoneNumber, employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment, employeeAndAdminBean.password);
        updatedEmployee.setActive(employeeAndAdminBean.active);

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
    public Response updateAdmin(@Valid @PathParam("email") String email, EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Administrator updatedAdmin = new Administrator(employeeAndAdminBean.pesel, employeeAndAdminBean.name, employeeAndAdminBean.surname, dateOfBirth, employeeAndAdminBean.phoneNumber, employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment, employeeAndAdminBean.password);
        updatedAdmin.setActive(employeeAndAdminBean.active);

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
    public Response activatePerson(@Valid @PathParam("email") String email){
        try {
            dataCenter.get_hr().activatePerson(email);
            return Response.ok().build();
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }
    }
    @PUT
    @Path("/deactivate/{email}")
    @RolesAllowed({"ADMIN"})
    public Response deactivatePerson(@PathParam("email") String email){
        try {
            dataCenter.get_hr().deactivatePerson(email);
            return Response.ok().build();
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), exceptionHandler.getMessage()).build();
        }
    }
}
