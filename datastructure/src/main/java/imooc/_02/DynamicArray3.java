package imooc._02;

public class DynamicArray3<E> {

    private E[] data;
    private int size;

    public DynamicArray3(){
        this(10);
    }

    public DynamicArray3(int capacity) {
        data = (E[])new Object[capacity];
        size = 0;
    }

    public int size(){ return size; }

    public int getCapacity() {
        return data.length;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(int index, E e) {

        if(size == data.length) {
            //throw new IllegalArgumentException("data is full");
            //扩容
            resize(data.length * 2);
        }

        if(index < 0 || index > size - 1) {
            throw new IllegalArgumentException("index error");
        }

        for(int i = size - 1;i <= index;i --) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size ++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public void addLast(E e) {
        add(size, e);
    }

    public E get(int index) {
        if(index < 0 || index > size - 1) {
            throw new IllegalArgumentException("index error");
        }
        return data[index];
    }

    public boolean contains(E e) {
        for(int i = 0;i < size;i ++) {
            if(data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    public int find(E e) {
        for(int i = 0;i < size;i ++) {
            if(data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    public E remove(int index) {
        if(index < 0 || index > size - 1) {
            throw new IllegalArgumentException("index error");
        }
        E ret = data[index];
        for(int i = index + 1;i < size;i ++) {
            data[i - 1] = data[i];
        }
        size --;
        data[size] = null;  // help GC
        if(size == data.length / 4 && data.length / 2 != 0) {
            //缩容， /4 可以防止复杂度震荡
            resize(data.length / 2);
        }
        return ret;
    }


    private void resize(int newCapacity) {
        E[] newData = (E[])new Object[newCapacity];
        for(int i = 0;i < size;i ++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}
