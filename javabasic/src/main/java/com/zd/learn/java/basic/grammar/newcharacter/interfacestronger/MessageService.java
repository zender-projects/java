package com.zd.learn.java.basic.grammar.newcharacter.interfacestronger;

public class MessageService implements IMessage{

    public static void main(String[] args) {

        IMessage message = new MessageService();
        message.method1();
        message.method2();
        IMessage.method3();

    }

    @Override
    public void method1() {
        System.out.println("Sub class method1()");
    }
}
