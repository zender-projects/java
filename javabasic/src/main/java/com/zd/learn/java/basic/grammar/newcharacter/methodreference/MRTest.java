package com.zd.learn.java.basic.grammar.newcharacter.methodreference;

public class MRTest {

    public static void main(String[] args) {

        //静态方法引用
        IUtil1<Integer, String> util1 = String::valueOf; //将valueOf方法应用在IUtil上
        String rs = util1.convert(45);
        System.out.println(rs);


        //对象方法引用
        IUtil2<String> util2 = "hello"::toUpperCase;
        //等价🌧️于
        IUtil2<String> util21 = () -> "hello".toUpperCase();
        System.out.println(util2.convert());
        System.out.println(util21.convert());


        //引用普通方法
        IUtil3<String, Integer> util3 = (String s1, String s2) -> s1.compareTo(s2);
        System.out.println(util3.convert("H", "h"));



        //引用构造方法
        IUtil4<Person, String, String> util4 = Person::new;
        Person person = util4.create("zhangsan", "23");
        System.out.println(person.toString());


    }
}


class Person {
    private String name;
    private String age;
    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "[" + name + "," + age +"]";
    }
}