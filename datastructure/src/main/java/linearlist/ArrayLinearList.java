package linearlist;

/**
 * 线性表的顺序存储.
 * @author mac
 * */
public class ArrayLinearList {

    private int DEFAULT_LENGTH = 10;    //最大长度
    private Object[] items;         //数组容器
    private int length;             //线性表长度

    /**
     * 1.初始化
     * 2.插入元素
     * 3.删除元素
     * 4.获取元素
     *
     * 5.判断是否为空
     * 6.清空
     * 7.获取当前长度
     * */

    public ArrayLinearList(){
        items = new Object[DEFAULT_LENGTH];
        this.length = 0;
    }


    public ArrayLinearList(int capacity) {
        if(capacity < 1)
            throw new IllegalArgumentException("initialize capacity error.");
        items = new Object[capacity];
        this.length = 0;
    }


    /**
     * 在指定位置插入元素.
     * */
    public boolean insert(int position, Object item) {
        if(position < 1 || position > items.length) {
            throw new IllegalArgumentException("invalid position.");
        }
        if(this.length == items.length) {
            throw new RuntimeException("linear list is full.");
        }
        //从指定位置开始将后面的元素向后移动
        for(int i = length - 1; i >= position - 1;i --) {
            items[i + 1] = items[i];
        }
        //将新元素插入到第i个位置
        items[position - 1] = item;
        this.length ++;
        return true;
    }

    /**
     * 在尾部插入元素
     * */
    public boolean insert(Object item) {
       return insert(this.length + 1, item);
    }

    /**
     * 删除指定位置上的元素
     * */
    public Object delete(int position) {
        if(position < 1 || position > items.length) {
            throw new IllegalArgumentException();
        }
        Object obj = items[position - 1];

        //从position开始遍历后面的元素并向前移动一个位置
        for(int i = position - 1;i < length - 1;i ++) {
            items[i] = items[i + 1];
        }
        this.length --;
        return obj;
    }

    /**
     * 删除指定元素
     * */
    public Object delete(Object item) {
        int idx  = idx(item);
        return delete(idx + 1);
    }


    /**
     * 获取指定位置上的元素
     * */
    public Object get(int position) {
        if(position < 1 || position >= items.length){
            throw new IllegalArgumentException();
        }
        return items[position - 1];
    }


    /**
     * 判断当前链表是否为空
     * */
    public boolean isEmpty(){
        return this.length == 0;
    }

    /**
     * 获取当前链表的长度
     * */
    public int size() {
        return this.length;
    }

    /**
     * 获取指定元素的索引位置
     * */
    public int idx(Object item) {
        for(int i = 0;i < this.length;i ++) {
            if(items[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < this.length;i ++) {
            sb.append(items[i] + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ArrayLinearList list = new ArrayLinearList();
        list.insert("a");
        list.insert("b");
        System.out.println(list);
        list.insert(2,"c");
        System.out.println(list);
        list.insert(3, "d");
        System.out.println(list);
        list.delete(2);
        System.out.println(list);
    }
}
