package imooc._03;

import imooc._02.DynamicArray2;

/**
 * 基于数组的栈实现.
 * */
public class ArrayStack<E> implements Stack<E> {

    DynamicArray2<E> array;


    public ArrayStack(int capacity) {
        array = new DynamicArray2<>(capacity);
    }

    public ArrayStack(){
        array = new DynamicArray2<>();
    }


    @Override
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public int getSize() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int getCapacity() {
        return array.getCapacity();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append("[");
        for(int i = 0;i < array.size();i ++) {
            stringBuilder.append(array.get(i));
            if(i != array.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("] top");
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        ArrayStack<String> stringArrayStack = new ArrayStack<>();
        stringArrayStack.push("a");
        stringArrayStack.push("b");
        System.out.println(stringArrayStack);
        stringArrayStack.pop();
        System.out.println(stringArrayStack);
    }
}
