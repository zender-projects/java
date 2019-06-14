package imooc._03;

import imooc._02.DynamicArray2;

public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    private int size;
    private int front;
    private int tail;

    public LoopQueue(int capacity) {
        //循环队列中又一个空位
        data = (E[])new Object[capacity + 1];
        this.size = 0;
        this.front = 0;
        this.tail = 0;
    }

    public LoopQueue() {
        this(10);
    }

    @Override
    public void enqueue(E e) {

        //满了， 扩容
        if((tail + 1) % data.length == front) {
            resize(getCapacity() * 2);
        }

        data[tail] = e;
        tail = (tail + 1) % data.length;
        size ++;
    }

    /**
     * 扩容
     * */
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity + 1];

        //将原来的数组中的元素复制到newData中
        for(int i = 0; i < size;i ++) {
            newData[i] = data[(i + front) % data.length];
        }

        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public E dequeue() {

        if(isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size --;

        if(size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }

        return null;
    }

    @Override
    public E getFront() {
        if(isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }
        return data[front];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return tail == front;
    }

    public int getCapacity() {
        return this.data.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Queue: size = %d, capacity = %d\n", size, getCapacity()));
        stringBuilder.append("front [");

        for (int i = front; i != tail;i = (i + 1) % data.length) {
            stringBuilder.append(data[i]);
            //i 的下一个位置不是tail，则说明不是最后一个
            if((i + 1) % data.length != tail) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("] tail");
        return stringBuilder.toString();
    }
}
