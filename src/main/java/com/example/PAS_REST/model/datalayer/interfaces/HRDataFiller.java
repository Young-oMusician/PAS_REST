package com.example.PAS_REST.model.datalayer.interfaces;



import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;

import java.util.List;

public interface HRDataFiller {
    void fill(List<Reader> readers, List<Employee> employees, List<Administrator> administrators);
}
