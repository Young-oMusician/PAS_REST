package com.example.PAS_REST.model.datalayer.repositories;

import com.example.PAS_REST.model.datalayer.interfaces.HRDataFiller;
import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Person;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class HRRepository {
    private List<Reader> readers;
    private List<Employee> employees;
    private List<Administrator> administrators;
    private HRDataFiller filler;

    public HRRepository(){
        readers = new ArrayList<>();
        employees = new ArrayList<>();
        administrators = new ArrayList<>();
    }

    public HRRepository(List<Reader> readers, List<Employee> employees, List<Administrator> administrators){
        this.readers = readers;
        this.employees = employees;
        this.administrators = administrators;
    }

    public synchronized void addReader(Reader reader) throws ExceptionHandler {
        if(isUnique(reader.getPesel(), reader.getEmail(), reader.getPhoneNumber())) {
            readers.add(reader);
        }
    }

    public synchronized void addEmployee(Employee employee) throws ExceptionHandler {
        if(isUnique(employee.getPesel(), employee.getEmail(), employee.getPhoneNumber())) {
            employees.add(employee);
        }
    }

    public synchronized void addAdministrator(Administrator administrator) throws ExceptionHandler {
        if(isUnique(administrator.getPesel(), administrator.getEmail(), administrator.getPhoneNumber())) {
            administrators.add(administrator);
        }
    }

    private synchronized void deleteReader(Reader reader) {
        readers.remove(reader);
    }

    private synchronized void deleteEmployee(Employee employee) {
        employees.remove(employee);
    }

    private synchronized void deleteAdministrator(Administrator administrator) {
        administrators.remove(administrator);
    }

    public synchronized Reader getReader(String email) {
        return readers.stream()
                .filter(Reader -> Reader.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }

    public synchronized Employee getEmployee(String email) {
        return  employees.stream()
                .filter(Employee -> Employee.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }

    public synchronized Administrator getAdministrator(String email) {
        return administrators.stream()
                .filter(administrator -> administrator.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }

    public synchronized List<Reader> getAllReaders() {
        return readers;
    }

    public synchronized List<Employee> getAllEmployees() {
        return employees;
    }

    public synchronized List<Administrator> getAllAdministrators() {
        return administrators;
    }

    public synchronized void updateReader(String email, Reader reader) throws ExceptionHandler {
        if(!reader.getEmail().equals(email)){
            throw new ExceptionHandler("Cannot change email!");
        }
        if(updateValidatior(reader.getPesel(), reader.getEmail(), reader.getPhoneNumber())){
            for(int i = 0; i < readers.size(); i++){
                if(readers.get(i).getEmail().equals(email)){
                    readers.set(i, reader);
                }
            }
        }
    }

    public synchronized void updateEmployee(String email, Employee employee) throws ExceptionHandler {
        if(!employee.getEmail().equals(email)){
            throw new ExceptionHandler("Cannot change email!");
        }
        if(updateValidatior(employee.getPesel(), employee.getEmail(), employee.getPhoneNumber())){
            for(int i = 0; i < employees.size(); i++){
                if(employees.get(i).getEmail().equals(email)){
                    employees.set(i, employee);
                }
            }
        }
    }

    public synchronized void updateAdministrator(String email, Administrator administrator) throws ExceptionHandler {
        if(!administrator.getEmail().equals(email)){
            throw new ExceptionHandler("Cannot change email!");
        }
        if(updateValidatior(administrator.getPesel(), administrator.getEmail(), administrator.getPhoneNumber())){
            for(int i = 0; i < administrators.size(); i++){
                if(administrators.get(i).getEmail().equals(email)){
                    administrators.set(i, administrator);
                }
            }
        }
    }

    public synchronized HRDataFiller getFiller() {
        return filler;
    }

    public synchronized void setFiller(HRDataFiller filler) {
        this.filler = filler;
    }

    public synchronized void fill(){
        if(filler != null){
            filler.fill(readers, employees, administrators);
        }
    }

    private boolean isUnique(String pesel, String email, String phoneNumber) throws ExceptionHandler {
        if(readers.stream().anyMatch(reader1 -> reader1.getPesel().equals(pesel))){
            throw new ExceptionHandler("Reader with this PESEL already exists in database!");
        }
        if(readers.stream().anyMatch(reader1 -> reader1.getEmail().equals(email))){
            throw new ExceptionHandler("Reader with this email already exists in database!");
        }
        if(readers.stream().anyMatch(reader1 -> reader1.getPhoneNumber().equals(phoneNumber))){
            throw new ExceptionHandler("Reader with this phone number already exists in database!");
        }
        if(employees.stream().anyMatch(employee -> employee.getPesel().equals(pesel))){
            throw new ExceptionHandler("Reader with this PESEL already exists in database!");
        }
        if(employees.stream().anyMatch(employee -> employee.getEmail().equals(email))){
            throw new ExceptionHandler("Reader with this email already exists in database!");
        }
        if(employees.stream().anyMatch(employee -> employee.getPhoneNumber().equals(phoneNumber))){
            throw new ExceptionHandler("Reader with this phone number already exists in database!");
        }
        if(administrators.stream().anyMatch(administrator -> administrator.getPesel().equals(pesel))){
            throw new ExceptionHandler("Reader with this PESEL already exists in database!");
        }
        if(administrators.stream().anyMatch(administrator -> administrator.getEmail().equals(email))){
            throw new ExceptionHandler("Reader with this email already exists in database!");
        }
        if(administrators.stream().anyMatch(administrator -> administrator.getPhoneNumber().equals(phoneNumber))){
            throw new ExceptionHandler("Reader with this email already exists in database!");
        }
        return true;
    }

    private boolean updateValidatior(String pesel, String email, String phoneNumber) throws ExceptionHandler {
        Person updatingPerson = getReader(email);
        if(updatingPerson == null){
            updatingPerson = getEmployee(email);
        }
        if(updatingPerson == null){
            updatingPerson = getAdministrator(email);
        }
        if(updatingPerson == null){
            throw new ExceptionHandler("Person with this email doesn't exist");
        }
        Person finalUpdatingPerson = updatingPerson;
        if(readers.stream().anyMatch(reader1 -> reader1.getPesel().equals(pesel)
                && !((Person)reader1).equals(finalUpdatingPerson))){
            throw new ExceptionHandler("Reader with this PESEL already exists in database!");
        }
        if(readers.stream().anyMatch(reader1 -> reader1.getPhoneNumber().equals(phoneNumber)
                && !((Person)reader1).equals(finalUpdatingPerson))){
            throw new ExceptionHandler("Reader with this phone number already exists in database!");
        }
        if(employees.stream().anyMatch(employee -> employee.getPesel().equals(pesel)
                && !((Person)employee).equals(finalUpdatingPerson))){
            throw new ExceptionHandler("Reader with this PESEL already exists in database!");
        }
        if(employees.stream().anyMatch(employee -> employee.getPhoneNumber().equals(phoneNumber)
                && !((Person)employee).equals(finalUpdatingPerson))){
            throw new ExceptionHandler("Reader with this phone number already exists in database!");
        }
        if(administrators.stream().anyMatch(administrator -> administrator.getPesel().equals(pesel)
                && !((Person)administrator).equals(finalUpdatingPerson))){
            throw new ExceptionHandler("Reader with this PESEL already exists in database!");
        }
        if(administrators.stream().anyMatch(administrator -> administrator.getPhoneNumber().equals(phoneNumber)
                && !((Person)administrator).equals(finalUpdatingPerson))){
            throw new ExceptionHandler("Reader with this phone number already exists in database!");
        }
        return true;
    }
}
