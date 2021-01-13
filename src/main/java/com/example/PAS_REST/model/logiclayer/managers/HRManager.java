package com.example.PAS_REST.model.logiclayer.managers;

import com.example.PAS_REST.model.datalayer.obj.Events.Rent;
import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Person;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;
import com.example.PAS_REST.model.datalayer.repositories.EventsRepository;
import com.example.PAS_REST.model.datalayer.repositories.HRRepository;
import com.example.PAS_REST.model.logiclayer.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HRManager {

    private HRRepository hrRepository;
    private EventsRepository eventsRepository;

    public HRManager(HRRepository hrRepository, EventsRepository eventsRepository) {
        this.hrRepository = hrRepository;
        this.eventsRepository = eventsRepository;
    }

//    public void addReader(String id, String name, String surname, Date birthDate, String phoneNumber, String email,
//                          Employee.Gender gender, Date dateOfRegistration) throws ExceptionHandler {
//        if(dataRepository.getAllReaders().stream().anyMatch(reader -> reader.getPesel().equals(id))){
//            throw new ExceptionHandler("Reader with this PESEL already exists in database!");
//        }
//        if(dataRepository.getAllReaders().stream().anyMatch(reader -> reader.getEmail().equals(email))){
//            throw new ExceptionHandler("Reader with this email already exists in database!");
//        }
//        if(dataRepository.getAllReaders().stream().anyMatch(reader -> reader.getPhoneNumber().equals(phoneNumber))){
//            throw new ExceptionHandler("Reader with this phone number already exists in database!");
//        }
//        dataRepository.addReader(new Reader(id,name,surname,birthDate,phoneNumber,email,gender,dateOfRegistration));
//    }

    public void addReader(String id, String name, String surname, Date birthDate, String phoneNumber, String email,
                          String gender, Date dateOfRegistration, Double balance) throws ExceptionHandler {
        hrRepository.addReader(new Reader(id,name,surname,birthDate,phoneNumber,email,gender,dateOfRegistration,balance));
    }

    public void addReader(String id, String name, String surname, Date birthDate, String phoneNumber, String email,
                          String gender, Date dateOfRegistration, Double balance, boolean active) throws ExceptionHandler {
        Reader newReader = new Reader(id,name,surname,birthDate,phoneNumber,email,gender,dateOfRegistration,balance);
        newReader.setActive(active);
        hrRepository.addReader(newReader);
    }

    public Person getPerson(String email){
        Person person = hrRepository.getReader(email);
        if(person == null){
            person = hrRepository.getEmployee(email);
        }
        if(person == null){
            person = hrRepository.getAdministrator(email);
        }
        return person;
    }

    public Reader getReader(String email){
        return new Reader(hrRepository.getReader(email));
    }

    public Employee getEmployee(String email){
        return new Employee(hrRepository.getEmployee(email));
    }

    public Administrator getAdministrator(String email){
        return new Administrator(hrRepository.getAdministrator(email));
    }

    public void deactivatePerson(String email) throws ExceptionHandler{
        Person activatingReader = hrRepository.getReader(email);
        if(activatingReader == null){
            activatingReader = hrRepository.getEmployee(email);
        }
        if(activatingReader == null){
            activatingReader = hrRepository.getAdministrator(email);
        }
        if(activatingReader == null){
            throw new ExceptionHandler("Person with this Id doesn't exist");
        }
        if(activatingReader.getClass().equals(Reader.class) && getReadersCurrentRent(activatingReader.getEmail()) != null){
            throw new ExceptionHandler("Reader with active rent cannot be deactivated!");
        }
        activatingReader.setActive(false);
    }

    public void activatePerson(String email) throws ExceptionHandler {
        Person activatingReader = hrRepository.getReader(email);
        if(activatingReader == null){
            activatingReader = hrRepository.getEmployee(email);
        }
        if(activatingReader == null){
            activatingReader = hrRepository.getAdministrator(email);
        }
        if(activatingReader == null){
            throw new ExceptionHandler("Person with this Id doesn't exist");
        }
        activatingReader.setActive(true);
    }

    public List<Reader> getAllReaders(){
        return hrRepository.getAllReaders().stream().map(Reader::new).collect(Collectors.toList());
    }

    public void updateReader(String email, Reader reader) throws ExceptionHandler {
        if(reader == null){
            throw new IllegalArgumentException("Reader cannot be null");
        }
        if(!email.equals(reader.getEmail())) {
            throw new ExceptionHandler("Readers email cannot be changed");
        }
        hrRepository.updateReader(email,reader);
    }

    public List<Rent> getReadersRents(String email){
        Reader reader = hrRepository.getReader(email);
        if(reader == null){
            throw new IllegalArgumentException("Reader cannot be null");
        }
        List<Rent> result = new ArrayList<>();
        for(Rent rent : eventsRepository.getAllRents()){
            if(rent.getReader().equals(reader)){
                result.add(rent);
            }
        }
        result = result.stream().map(Rent::new).collect(Collectors.toList());
        return result;
    }

    public Rent getReadersCurrentRent(String email){
        if(email == null){
            throw new IllegalArgumentException("Email cannot be null");
        }
        for(Rent rent : getReadersRents(email)){
            if(rent.getDateOfReturn() == null){
                return rent;
            }
        }
        return null;
    }


    public void addEmployee(String id, String name, String surname, Date birthDate, String phoneNumber, String email, String gender, Date dateOfEmployment) throws ExceptionHandler {
        hrRepository.addEmployee(new Employee(id,name,surname,birthDate,phoneNumber,email,gender,dateOfEmployment));
    }

    public List<Employee> getAllEmployees(){
        return hrRepository.getAllEmployees().stream().map(Employee::new).collect(Collectors.toList());
    }

    public void updateEmployee(String id, Employee employee) throws ExceptionHandler {
        if(employee == null){
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if(!id.equals(employee.getEmail())){
            throw new ExceptionHandler("Employees id cannot be changed");
        }
        hrRepository.updateEmployee(id, employee);
    }

    public void addAdministrator(String id, String name, String surname, Date birthDate, String phoneNumber, String email, String gender, Date dateOfEmployment) throws ExceptionHandler {
        hrRepository.addAdministrator(new Administrator(id,name,surname,birthDate,phoneNumber,email,gender,dateOfEmployment));
    }

    public void deactivateAdministrator(String email) throws ExceptionHandler {
        Administrator deactivatingAdministrator = hrRepository.getAdministrator(email);
        if(deactivatingAdministrator == null){
            throw new ExceptionHandler("Administrator with this email doesn't exists!");
        }
        deactivatingAdministrator.setActive(false);
        hrRepository.updateAdministrator(email, deactivatingAdministrator);
    }

    public void activateAdministrator(String email) throws ExceptionHandler {
        Administrator activatingAdministrator = hrRepository.getAdministrator(email);
        if(activatingAdministrator == null){
            throw new ExceptionHandler("Administrator with this email doesn't exists!");
        }
        activatingAdministrator.setActive(true);
        hrRepository.updateAdministrator(email, activatingAdministrator);
    }

    public List<Administrator> getAllAdministrators(){
        return hrRepository.getAllAdministrators().stream().map(Administrator::new).collect(Collectors.toList());
    }

    public void updateAdministrator(String id, Administrator administrator) throws ExceptionHandler {
        if(administrator == null){
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if(!id.equals(administrator.getEmail())){
            throw new ExceptionHandler("Administrators id cannot be changed");
        }
        hrRepository.updateAdministrator(id, administrator);
    }
}
