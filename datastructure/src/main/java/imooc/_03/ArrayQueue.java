package imooc._03;

import imooc._02.DynamicArray2;

/**
 * 基于数组的队列.
 * */
public class ArrayQueue<E> implements Queue<E> {

    private DynamicArray2<E> array;

    public ArrayQueue(int capacity) {
        this.array = new DynamicArray2<>(capacity);
    }

    public ArrayQueue() {
        this.array = new DynamicArray2<>();
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.getFirst();
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
        stringBuilder.append("Queue:");
        stringBuilder.append("front [");
        for(int i = 0;i < array.size();i ++) {
            stringBuilder.append(array.get(i));
            if(i != array.size() -1){
                stringBuilder.append(",");
            }else{
                stringBuilder.append("] tail");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        System.out.println(arrayQueue);

        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.enqueue(4);

        System.out.println(arrayQueue);

    }
}
