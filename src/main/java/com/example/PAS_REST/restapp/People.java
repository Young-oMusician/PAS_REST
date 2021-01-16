package com.example.PAS_REST.restapp;

import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;
import com.example.PAS_REST.restapp.beans.EmployeeAndAdminBean;
import com.example.PAS_REST.restapp.beans.ReaderBean;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Path("hr")
@RolesAllowed({"ADMINS"})
public class People {

    @Inject
    private DataCenter dataCenter;

//-----------------Display----------------
    //readers
    @GET
    @Path("/readers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reader> getAllReaders(){
        return dataCenter.get_hr().getAllReaders();
    }

    @GET
    @Path("/readers/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Reader getReader(@PathParam("email") String email){
        return dataCenter.get_hr().getReader(email);
    }
    //emplyees
    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees(){
        return dataCenter.get_hr().getAllEmployees();
    }

    @GET
    @Path("/employees/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("email") String email){
        return dataCenter.get_hr().getEmployee(email);
    }
    //admins
    @GET
    @Path("/admins")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Administrator> getAllAdministrators(){
        return dataCenter.get_hr().getAllAdministrators();
    }

    @GET
    @Path("/admins/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Administrator getAdministrator(@PathParam("email") String email){
        return dataCenter.get_hr().getAdministrator(email);
    }
    //-----------------Delete----------------
    // w wymaganiach nie ma delete
    //-----------------Create----------------
    @POST
    @Path("/readers/add")
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
                    readerBean.email,readerBean.gender,dateOfRegistration ,readerBean.balance);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }
    @POST
    @Path("/employees/add")
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
                    employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }
    @POST
    @Path("/admins/add")
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
                    employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();
    }

    //-----------------Update----------------
    @PUT
    @Path("/readers/update/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateReader(@PathParam("email") String email, ReaderBean readerBean){
        Date dateOfBirth = null;
        Date dateOfRegistration = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(readerBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Reader updatedReader = new Reader(readerBean.id, readerBean.name, readerBean.surname, dateOfBirth,readerBean.phoneNumber, readerBean.email,readerBean.gender,dateOfRegistration, readerBean.balance);

        try {
            dataCenter.get_hr().updateReader(email,updatedReader);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();

    }
    @PUT
    @Path("/employees/update/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("email") String email, EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Employee updatedEmployee = new Employee(employeeAndAdminBean.id, employeeAndAdminBean.name, employeeAndAdminBean.surname, dateOfBirth, employeeAndAdminBean.phoneNumber, employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment);

        try {
            dataCenter.get_hr().updateEmployee(email,updatedEmployee);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();

    }

    @PUT
    @Path("/admins/update/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAdmin(@PathParam("email") String email, EmployeeAndAdminBean employeeAndAdminBean){
        Date dateOfBirth = null;
        Date dateOfEmployment = new Date();
        try {
            dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employeeAndAdminBean.birthDate);
        } catch (ParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Not acceptable date format").build();
        }

        Administrator updatedAdmin = new Administrator(employeeAndAdminBean.id, employeeAndAdminBean.name, employeeAndAdminBean.surname, dateOfBirth, employeeAndAdminBean.phoneNumber, employeeAndAdminBean.email, employeeAndAdminBean.gender,dateOfEmployment);

        try {
            dataCenter.get_hr().updateAdministrator(email,updatedAdmin);
        } catch (ExceptionHandler exceptionHandler) {
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), exceptionHandler.getMessage()).build();
        }
        return Response.ok().build();

    }
}
