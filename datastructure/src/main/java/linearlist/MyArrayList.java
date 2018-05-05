package linearlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<AnyType> implements Iterable<AnyType>{


    private static final int DEFAULT_CAPACITY = 10;
    private int theSize;
    private AnyType[] theItems;


    //初始化
    public MyArrayList(){
        clear();
    }

    //判断是否为空
    public boolean isEmpty(){
        return size() == 0;
    }

    public void trimToSize(){
        ensureCapacity(size());
    }

    //获取元素
    public AnyType get(int idx) {
        if(idx < 0 || idx >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return theItems[idx];
    }

    //设置元素
    public AnyType set(int idx, AnyType newVal) {
        if(idx < 0 || idx >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        AnyType old = get(idx);
        theItems[idx] = newVal;
        return old;
    }

    //在指定位置添加元素
    public void add(int idx, AnyType x) {
        if(theItems.length == size()) {
            ensureCapacity(size() * 2 + 1);
        }
        //将idx后面的元素向后移动
        for(int i = theSize;i > idx;i --) {
            theItems[i] = theItems[i - 1];
        }
        theItems[idx] = x;
        theSize ++;
    }

    public boolean add(AnyType x) {
        add(size(), x);
        return true;
    }

    public AnyType remove(int idx) {
        AnyType old = theItems[idx];
        //将idx后面的元素向前移动一个位置
        for(int i = idx;i < size() - 1;i ++) {
            theItems[i] = theItems[i + 1];
        }
        return old;
    }

    //重置
    public void clear(){
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    //获取当前列表的长度
    public int size(){
        return theSize;
    }

    public void ensureCapacity(int newCapacity) {
        if(newCapacity < theSize) {
            return;
        }
        AnyType[] old = theItems;
        //创建新的数组
        theItems = (AnyType[])new Object[newCapacity];
        //复制数据到新的数组中
        for(int i = 0;i < size();i ++) {
            theItems[i] = old[i];
        }
    }

    public Iterator<AnyType> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements java.util.Iterator<AnyType> {

        private int currentIdx;

        public boolean hasNext() {
            return currentIdx < size();
        }

        public AnyType next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return theItems[currentIdx ++];
        }

        public void remove() {
            MyArrayList.this.remove(-- currentIdx);
        }
    }

}
