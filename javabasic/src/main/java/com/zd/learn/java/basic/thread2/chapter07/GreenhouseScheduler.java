package com.zd.learn.java.basic.thread2.chapter07;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模拟温室调度系统.
 * @author mac
 * */
public class GreenhouseScheduler {

    private volatile boolean light = false;
    private volatile boolean water = false;

    private String thermostat = "Day";

    public synchronized String getThermostat() {
        return thermostat;
    }

    public synchronized void setThermostat(String thermostat) {
        this.thermostat = thermostat;
    }

    //任务调度器
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            new ScheduledThreadPoolExecutor(10);

    //单次执行
    public void schedule(Runnable event, long delay) {
        scheduledThreadPoolExecutor.schedule(event, delay, TimeUnit.MILLISECONDS);
    }

    //周期执行
    public void repeat(Runnable even, long delay, long period){
        scheduledThreadPoolExecutor.scheduleAtFixedRate(even, delay, period, TimeUnit.MICROSECONDS);
    }

    //Events
    class LightOn implements Runnable {

        @Override
        public void run() {
            System.out.println("Turning on lights");
            light = true;
        }
    }

    class LightOff implements Runnable {
        @Override
        public void run() {
            System.out.println("Turning off lights");
            light = false;
        }
    }

    class WaterOn implements Runnable {

        @Override
        public void run() {
            System.out.println("Turning greenhouse water on");
            water = true;
        }
    }

    class WaterOff implements Runnable {
        @Override
        public void run() {
            System.out.println("Turning greenhouse water off");
            water = false;
        }
    }

    class ThermostatNight implements Runnable {

        @Override
        public void run() {
            System.out.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }

    class ThermostatDay implements Runnable {
        @Override
        public void run() {
            System.out.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        @Override
        public void run() {
            System.out.println("Bing.!");
        }
    }



    //用于保存数据的数据结构
    static class DataPoint{
        final Calendar time;
        final float temperature;
        final float humidity;

        public DataPoint(Calendar time , float temperature, float humidity) {
            this.time = time;
            this.temperature = temperature;
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return time.getTime() + " temperature:" + temperature + ", humidity:" + humidity;
        }
    }

    //收集数据
    

    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random random = new Random(47);

    private Calendar lastTime = Calendar.getInstance();

    //初始化
    {
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 30);
    }

    List<DataPoint> data = Collections.synchronizedList(new ArrayList<DataPoint>());


}
