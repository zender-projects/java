package linearlist;

/**
 * 链式存储的线性表.
 * @author mac
 * */
public class LinkedLinearList {

    private Node head;  //头节点
    private int length; //当前链表长度


    /**
     * 1.初始化
     * 2.获取指定位置上的元素
     * 3.在指定位置插入元素
     * 4.删除指定位置上的元素
     * 5.判断某个元素在链表中是否存在
     * 6.判断链表是否为空
     * 7.获取链表中元素的数量
     * 8.遍历链表中的元素
     * */


    /**
     * 初始化
     * */
    public LinkedLinearList(){
        //创建头节点，头指针默认为空
        this.head = new Node(null, null);
        this.length = 0;
    }

    /**
     * 获取指定位置上的元素
     * */
    public Object get(int position) {
        int j = 1;
        Node temp = this.head.next;
        while(temp != null && j < position) {
            temp = temp.next;
            ++j;
        }

        if(temp == null || j > position) {
            return null;
        }
        return temp.data;
    }

    /**
     * 在指定位置插入元素
     * */
    public boolean insert(int position, Object element) {
        int j = 1;
        Node temp, newNode;
        temp = this.head;

        //遍历到第 position - 1的位置
        while(temp != null && j < position) {
            temp = temp.next;
            ++j;
        }

        //第position个位置不存在
        if(temp == null || j > position) {
            return false;
        }

        newNode = new Node(element);
        newNode.next = temp.next;
        temp.next = newNode;
        this.length ++;
        return true;
    }


    /**
     * 删除指定位置的元素
     * */
    public boolean delete(int position) {
        int j = 1;
        Node temp = this.head;
        //遍历到待删除节点的前一个节点
        while (temp.next != null && j < position) {
            temp = temp.next;
            ++j;
        }

        //注意要判断temp.next， 否则后续操作会出现NullPointerException
        if(temp.next == null || j > position) {
            return false;
        }

        Node delNode = temp.next;
        temp.next = delNode.next;
        delNode.next = null;  //help GC
        this.length --;
        return true;
    }

    /**
     * 判断某个元素是否存在
     * */
    public boolean contains(Object element) {
        boolean isContains = false;
        Node temp = this.head.next;
        while(temp != null) {
            if(temp.data == element || temp.data.equals(element)) {
                isContains = true;
                break;
            }
            temp = temp.next;
        }
        return isContains;
    }

    /**
     * 判断链表是否为空
     * */
    public boolean isEmpty(){
        return this.length == 0;
    }

    public int size(){
        return this.length;
    }

    /**
     * 遍历链表
     * */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node temp = this.head.next;
        while(temp != null) {
            sb.append(temp.data + " ");
            temp = temp.next;
        }
        return sb.toString();
    }



    //链表节点
    private static class Node{
        Object data;
        Node next;
        public Node(Object data){
            this.data = data;
        }

        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedLinearList linkedLinearList = new LinkedLinearList();
        linkedLinearList.insert(1, "a");
        linkedLinearList.insert(2,"b");
        linkedLinearList.insert(1, "c");
        System.out.println(linkedLinearList);

        //System.out.println(linkedLinearList.get(1));
        //System.out.println(linkedLinearList.get(3));

        //linkedLinearList.delete(1);
        //System.out.println(linkedLinearList);
        //System.out.println(linkedLinearList.delete(3));

        System.out.println(linkedLinearList.contains("e"));
    }

}
