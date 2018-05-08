package linearlist;

/**
 * 栈的顺序存储结构
 * @author mac
 * */
public class ArrayStack {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] items;
    private int size;
    private int capacity;

    public ArrayStack(){
        this.capacity = DEFAULT_CAPACITY;
        items = new Object[capacity];
        size = 0;
    }

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        items = new Object[capacity];
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * 入栈
     * */
    public void push(Object element) {
        ensureCapacity(size() + 1);
        items[size ++] = element;
    }

    /**
     * 出栈
     * */
    public Object pop(){
        if(!isEmpty()){
            Object elem = items[size - 1];
            //释放栈顶
            items[--size] = null;
            return elem;
        }
        return null;
    }

    public Object peek(){
        if(!isEmpty()) {
            return items[size - 1];
        }
        return null;
    }

    /**
     * 扩容，类似于ArrayList
     * */
    private void ensureCapacity(int newCapacity) {
        int oldCapacity = items.length;
        if(newCapacity <= oldCapacity) {
            return;
        }

        newCapacity = oldCapacity * 2 + 1;
        //items = Arrays.copyOf(items, newCapacity);
        Object[] oldData = items;
        items = new Object[newCapacity];
        for(int i = 0;i < oldData.length;i ++) {
            items[i] = oldData[i];
        }
    }

}
