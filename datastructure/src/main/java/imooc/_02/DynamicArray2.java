package imooc._02;

public class DynamicArray2<E> {

    private E[] data;
    private int size;

    public DynamicArray2(){
        this(10);
    }

    public DynamicArray2(int capacity) {
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
            throw new IllegalArgumentException("data is full");
        }

        if(index < 0 || index > size) {
            throw new IllegalArgumentException("index error");
        }

        for(int i = size - 1;i >= index;i --) {
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

    public E getLast() {
        return get(size - 1);
    }

    public E getFirst() {
        return get(0);
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
        return ret;
    }

    public E removeLast(){
        return remove(size - 1);
    }

    public E removeFirst() {
        return remove(0);
    }
}
