package com.example.PAS_REST.initialdata;


import com.example.PAS_REST.model.datalayer.interfaces.HRDataFiller;
import com.example.PAS_REST.model.datalayer.obj.People.Administrator;
import com.example.PAS_REST.model.datalayer.obj.People.Employee;
import com.example.PAS_REST.model.datalayer.obj.People.Reader;

import java.util.Calendar;
import java.util.List;

public class HRInitialData implements HRDataFiller {
    @Override
    public void fill(List<Reader> readers, List<Employee> employees, List<Administrator> administrators) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();

        cal1.set(1958, Calendar.JANUARY, 7);
        cal2.set(2010, Calendar.AUGUST, 9);
        Reader reader1 = new Reader("11111111111", "Adam", "Nowak", cal1.getTime(),
                "111111111", "adam.nowak@gmail.com", "Male", cal2.getTime(), "test123");
        cal1.set(1984, Calendar.JUNE, 8);
        cal2.set(2016, Calendar.AUGUST, 27);
        Reader reader2 = new Reader("11111222222", "Alicja", "Iksińska", cal1.getTime(),
                "111112222", "alicja.iksinska@outlook.com", "Female", cal2.getTime(), "test123");
        cal1.set(1955, Calendar.JANUARY, 24);
        cal2.set(2019, Calendar.AUGUST, 17);
        Reader reader3 = new Reader("22222222222", "Eryk", "Kowalczyk", cal1.getTime(),
                "222222222", "eryk.kowalczyk@outlook.com", "Male", cal2.getTime(), "test123");
        cal1.set(1963, Calendar.DECEMBER, 25);
        cal2.set(2013, Calendar.OCTOBER, 28);
        Reader reader4 = new Reader("22222333333", "Barbara", "Wiśniewska", cal1.getTime(),
                "222223333", "barbara.wisniewska@outlook.com", "Female", cal2.getTime(), "test123");
        cal1.set(1990, Calendar.JULY, 6);
        cal2.set(2016, Calendar.MARCH, 1);
        Reader reader5 = new Reader("33333333333", "Mateusz", "Wójcik", cal1.getTime(),
                "333333333", "mateusz.wojcik@outlook.com", "Male", cal2.getTime(), "test123");

        readers.add(reader1);
        readers.add(reader2);
        readers.add(reader3);
        readers.add(reader4);
        readers.add(reader5);

        cal1.set(1972, Calendar.DECEMBER, 22);
        cal2.set(2014, Calendar.OCTOBER,14);
        Employee employee1 = new Employee("44444444444", "Gabriela", "Kowalczyk", cal1.getTime(),
                "444444444", "gabriela.kowalczyk@ebibl.com", "Female", cal2.getTime(), "test123");
        cal1.set(1963, Calendar.APRIL, 26);
        cal2.set(2013, Calendar.JUNE,22);
        Employee employee2 = new Employee("44444455555", "Zygmunt", "Kamiński", cal1.getTime(),
                "444445555", "zygmunt.kaminski@ebibl.com", "Male", cal2.getTime(), "test123");
        cal1.set(1981, Calendar.DECEMBER, 5);
        cal2.set(2014, Calendar.OCTOBER,16);
        Employee employee3 = new Employee("55555555555", "Filip", "Lewandowski", cal1.getTime(),
                "555555555", "filip.lewandowski@ebibl.com", "Female", cal2.getTime(), "test123");

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        cal1.set(1978, Calendar.FEBRUARY, 5);
        cal2.set(2012, Calendar.AUGUST, 20);
        Administrator administrator1 = new Administrator("66666666666", "Natalia", "Zielińska",
                cal1.getTime(), "666666666", "natalia.zielinska@ebibl.com", "Female", cal2.getTime(), "test123");
        cal1.set(1983, Calendar.FEBRUARY, 22);
        cal2.set(2010, Calendar.JANUARY, 20);
        Administrator administrator2 = new Administrator("77777777777", "Wiktor", "Woźniak",
                cal1.getTime(), "777777777", "wiktor.wozniak@ebibl.com", "Male", cal2.getTime(), "test123");

        administrators.add(administrator1);
        administrators.add(administrator2);
    }
}
