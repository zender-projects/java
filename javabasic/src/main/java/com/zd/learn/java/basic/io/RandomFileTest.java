package com.zd.learn.java.basic.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RandomFileTest {


    public static void main(String[] args) {

        Employee2[] staff = new Employee2[3];
        staff[0] = new Employee2("Carl Cracker",75000, 1987, 12,15);
        staff[1] = new Employee2("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee2("Tony Tester", 40000, 1990, 3, 5);

        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.txt"));
            for(Employee2 e : staff) {
                e.writeData(out);
            }
            out.close();



            RandomAccessFile accessFile = new RandomAccessFile("employee.txt", "rw");
            int n = (int)accessFile.length() / Employee2.RECORD_SIZE;
            Employee2[] newStaffs = new Employee2[n];
            for(int i = n - 1;i >= 0;i ++) {
                newStaffs[i] = new Employee2();
                accessFile.seek(i * Employee2.RECORD_SIZE);
                newStaffs[i].readData(accessFile);
            }
            accessFile.close();

            for(Employee2 employee2 : newStaffs) {
                System.out.println(employee2);
            }

        }catch (IOException ex) {

        }

    }


}


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class Employee2 {

    private String name;
    private double salary;
    private Date hireDay;

    public static final int NAME_SIZE = 40;
    public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;

    public Employee2(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = day;
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        this.hireDay = calendar.getTime();
    }

    public void writeData(DataOutput out) throws IOException {
        DataIO.writeFixedString(name, NAME_SIZE, out);
        out.writeDouble(salary);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(hireDay);

        out.writeInt(calendar.get(Calendar.YEAR));
        out.writeInt(calendar.get(Calendar.MONTH) + 1);
        out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
    }


    public void readData(DataInput in) throws IOException {
       name = DataIO.readFixedString(NAME_SIZE, in) ;
       salary = in.readDouble();
       int year = in.readInt();
       int month = in.readInt();
       int day = in.readInt();
       GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
       this.hireDay = calendar.getTime();
    }
}

class DataIO {

    public static String readFixedString(int size, DataInput in) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        boolean more = true;
        while(more && i < size) {
            char n = in.readChar();
            i ++;
            if(n == 0)
                more = false;
            else
                stringBuilder.append(n);
        }
        in.skipBytes(2 * (size - i));
        return stringBuilder.toString();
    }

    public static void writeFixedString(String s, int size, DataOutput out) throws IOException{
        for(int i = 0;i < size;i ++) {
            char ch = 0;
            if(i < s.length())
                ch = s.charAt(i);
            out.writeChar(ch);
        }
    }
}
