package com.zd.learn.java.basic.grammar.newcharacter.enumtype;

/**
 * 多例设计模式的应用
 * */
public class MultiInstance {

    public static void main(String[] args) {
        //Color color = new Color();
        System.out.println(Color.getInstance(1));
        System.out.println(Color1.RED);

        //获取所有多枚举实例
        Color1[] color1s = Color1.values();
        for(Color1 color1 : color1s) {
            //获取当前枚举实例多序号 和 名称
            System.out.println(color1.ordinal() + "-" + color1.name());
        }

        Color2 color2 = Color2.GREEN;
        String color = color2.getColor();
        System.out.println(color);
    }
}

/***
 * 非枚举实现多例模式
 * @author mac
 * */
class Color {

    private String color;
    //构造器私有化
    private Color(String color) { this.color = color; }

    //实例化对象
    private static Color RED = new Color("RED");
    private static Color GREEN = new Color("GREEN");
    private static Color BLUE = new Color("BLUE");

    public static Color getInstance(int flag) {

        switch (flag) {
            case 1 : return RED;
            case 2 : return GREEN;
            case 3 : return BLUE;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return this.color;
    }
}

/**
 * 枚举实现多例
 * */
enum Color1 {
    RED, GREEN, BLUE
}

/**
 * 枚举的高级应用.
 * */
interface IColor {
    String getColor();
}

//复杂枚举类型
enum Color2 implements IColor {
    RED("RED"), GREEN("GREEN"), BLUE("BLUE");

    //定义属性
    private String title ;
    //定义构造方法
    private Color2(String title) {
        this.title = title;
    }

    //复写接口方法
    @Override
    public String getColor() {
        return "color:" + this.title;
    }

    @Override
    public String toString() {
        return this.title ;
    }
}