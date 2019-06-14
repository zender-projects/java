package imooc._02;

public class DynamicArray {

    private int[] data;
    private int size;

    public DynamicArray() {
        this(10);
    }

    public DynamicArray(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //向尾部插入
    public void addLast(int e) {

        if(size == data.length) {
            throw new IllegalArgumentException("add last failed, array is full.");
        }

        data[size] = e;
        size ++;
    }

    //向指定位置插入
    public void add(int index , int e) {
        if(size == data.length) {
            throw new IllegalArgumentException("add last failed, array is full.");
        }

        if(index < 0 ||index > size - 1) {
            throw new IllegalArgumentException("index error");
        }

        for(int i = size - 1;i >= index; i --) {
            data[i + 1] = data[i];
        }

        data[index] = e;
        size ++;
    }

    public void addFirst(int e){
        add(0, e);
    }


    public int get(int index) {
        if(index < 0 || index >=  size) {
            throw  new IllegalArgumentException("index error");
        }
        return data[index];
    }

    public boolean contains(int e){
        for(int i =  0;i < size;i ++) {
            if(data[i] == e){
                return true;
            }
        }
        return false;
    }


    public int find(int e) {
        //int index = -1;
        for(int i = 0;i < size;i ++) {
            if(data[i] == e) {
                return i;
            }
        }
        return -1;

    }

    public int remove(int index) {
        if(index < 0 || index >=  size) {
            throw  new IllegalArgumentException("index error");
        }
        int ret = data[index];
        for(int i = index + 1;i < size;i ++) {
            data[i - 1] = data[i];
        }
        size --;
        return ret;
    }

    public int removeFirst(){
        return remove(0);
    }

    public int removeLast(){
        return remove(size - 1);
    }


    public void removeElement(int e) {
        int index = find(e);
        if(index != -1) {
            remove(index);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Array: size = %d, capacity = %d, ", size, data.length));
        stringBuilder.append("[");
        for(int i = 0;i < size;i ++) {
            stringBuilder.append(data[i]);
            if(i != size - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray();
        dynamicArray.addLast(1);
        dynamicArray.addLast(2);
        dynamicArray.add(1, 3);;
        dynamicArray.add(0,10);
        dynamicArray.addFirst(11);
        System.out.println(dynamicArray);
    }

}
