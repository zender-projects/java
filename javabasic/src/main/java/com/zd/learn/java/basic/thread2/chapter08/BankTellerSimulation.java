package com.zd.learn.java.basic.thread2.chapter08;


import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

//顾客
class Customer{
    private final int serviceTime;
    public Customer(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public int getServiceTime(){ return this.serviceTime; }

    @Override
    public String toString() {
        return "[" + serviceTime + "]";
    }
}

//排队队列
class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    @Override
    public String toString() {
        if(this.size() == 0) {
            return "[Empty]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Customer customer : this) {
            stringBuilder.append(customer);
        }
        return stringBuilder.toString();
    }
}

//Consumer生成器
class CustomerGenerator implements Runnable {

    private Random random = new Random(47);
    private CustomerLine customerLine;
    public CustomerGenerator(CustomerLine customerLine) {
        this.customerLine = customerLine;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
                customerLine.put(new Customer(random.nextInt(1000)));
            }
        }catch (InterruptedException ex) {
            System.out.println("Customer Generator Interrupted.");
        }
        System.out.println("CustomerGenerator terminating!");
    }
}

//出纳员,服务于Customer
class Teller implements Runnable, Comparable<Teller> {

    private static int counter = 0;
    private final int id = counter ++;

    private int customersSaved = 0;
    private CustomerLine customerLine;
    private boolean servingCustomerLine = true;

    public Teller(CustomerLine customerLine) {
        this.customerLine = customerLine;
    }



    @Override
    public synchronized int compareTo(Teller o) {
        return customersSaved < o.customersSaved ? -1 :
                (customersSaved == o.customersSaved ? 0 : 1);
    }

    //服务
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                Customer customer = customerLine.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this) {
                    customersSaved ++;
                    //暂停服务
                    while (!servingCustomerLine) {
                        wait();
                    }
                }
            }
        }catch (InterruptedException ex) {
            System.out.println(this + " interrupted.");
        }
        System.out.println(this + " terminating...");
    }


    public synchronized void doSomethingEle(){
        this.customersSaved = 0;
        this.servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine(){
        this.servingCustomerLine = true;
        notifyAll();
    }
}

//总调度
class TellerManager implements Runnable {

    private ExecutorService executorService;
    private CustomerLine customerLine;

    //工作队列
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();
    //空闲队列
    private PriorityQueue<Teller> doOtherThingsTellers = new PriorityQueue<>();

    private int adjustmentPeriod;
    private Random random = new Random(47);
    public TellerManager(ExecutorService executorService, CustomerLine customerLine,
                         int adjustmentPeriod) {
        this.executorService = executorService;
        this.customerLine = customerLine;
        this.adjustmentPeriod = adjustmentPeriod;

        Teller teller = new Teller(customerLine);
        executorService.execute(teller);
        workingTellers.add(teller);
    }

    public void adjustTellerNumber() {
        if(customerLine.size() / workingTellers.size() > 2) {
            if(doOtherThingsTellers.size() > 0) {
                Teller teller = doOtherThingsTellers.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller);
                return;
            }

            Teller teller = new Teller(customerLine);
            executorService.execute(teller);
            workingTellers.add(teller);
            return;
        }

        if(workingTellers.size() > 1 && customerLine.size() / workingTellers.size() < 2) {
            reassignOneTeller();
        }

        if(customerLine.size() == 0) {
            while (workingTellers.size() > 1) {
                reassignOneTeller();
            }
        }
    }

    //释放一个Teller
    private void reassignOneTeller(){
        Teller teller = workingTellers.poll();
        teller.doSomethingEle();;
        doOtherThingsTellers.offer(teller);
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                adjustTellerNumber();
                System.out.println( customerLine + "{ ");
                for (Teller teller : workingTellers) {
                    System.out.println(teller);
                }
                System.out.println("}");
            }
        }catch (Exception ex) {
            System.out.println( this + " interrupted");
        }
        System.out.println(this + "terminating");
    }
}




public class BankTellerSimulation {

}
