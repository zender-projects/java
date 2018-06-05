package com.zd.learn.java.basic.io;

import com.sun.tools.jdeprscan.scan.Scan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TextFileTest {

    public static void main(String[] args) {

        Employee[] employees = new Employee[3];
        employees[0] = new Employee("Carl Cracker",75000, 1987, 12,15);
        employees[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        employees[2] = new Employee("Tony Tester", 40000, 1990, 3, 5);

        try{

            //写入数据
            PrintWriter printWriter = new PrintWriter("employee.dat");
            writeData(employees, printWriter);
            printWriter.close();

            //读取数据
            Scanner scanner = new Scanner("employee.dat");
            Employee[] employees1 = readData(scanner);
            scanner.close();

            for (Employee employee:employees1) {
                System.out.println(employee);
            }

        }catch (IOException e) {

        }

    }



    private static void writeData(Employee[] employees, PrintWriter printWriter)
            throws IOException {

        printWriter.println(employees.length);
        for(Employee employee : employees) {
            employee.weiteData(printWriter);
        }

    }



    private static Employee[] readData(Scanner scanner) {
        int n = scanner.nextInt();
        scanner.nextLine();

        Employee[] employees = new Employee[n];
        for(int i = 0;i < n ;i ++) {
            employees[i] = new Employee();
            employees[i].readData(scanner);
        }
        return employees;
    }

}

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class Employee{
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        this.hireDay = calendar.getTime();
    }

    public void weiteData(PrintWriter printWriter) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(this.hireDay);
        printWriter.println(this.name + "|" +
                            this.salary + "|" +
                            calendar.get(Calendar.YEAR) + "|" +
                            (calendar.get(Calendar.MONTH) + 1) + "|" +
                            calendar.get(Calendar.DAY_OF_MONTH));
    }


    public void readData(Scanner scanner) {
        String line = scanner.nextLine();
        String[] tokens = line.split("\\|");
        this.name = tokens[0];
        this.salary = Double.parseDouble(tokens[1]);
        int year = Integer.parseInt(tokens[2]);
        int month = Integer.parseInt(tokens[3]);
        int day = Integer.parseInt(tokens[4]);

        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        this.hireDay = calendar.getTime();
    }
}