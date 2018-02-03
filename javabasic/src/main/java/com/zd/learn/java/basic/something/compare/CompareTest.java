package com.zd.learn.java.basic.something.compare;

import java.util.TreeSet;

public class CompareTest {

    public static void main(String[] args) {

        Person p1 = new Person("zhangsan",12);
        Person p2 = new Person("lisi", 34);

        TreeSet<Person> set = new TreeSet<>();
        set.add(p2);
        set.add(p1);

        set.stream().forEach(person -> {
            System.out.println(person);
        });

    }

}

class Person implements Comparable
{

    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public int compareTo(Object o) {
        Person person = (Person)o;
        return this.name.compareTo(person.getName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}