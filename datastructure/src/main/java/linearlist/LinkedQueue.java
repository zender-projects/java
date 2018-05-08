package linearlist;

/**
 * 链表实现队列
 * @author mac
 * */
public class LinkedQueue {


    private Node front;
    private Node rear;
    private int size;


    /**
     * 初始化
     * */
    public LinkedQueue(){
        this.front = this.rear = null;
        this.size = 0;
    }

    /**
     * 判断队列是否为空
     * */
    public boolean isEmpty(){
        return this.front == null;
    }

    /**
     * 入队
     * */
    public boolean enqueue(Object elem) {
        //构造一个新的节点
        Node newNode = new Node(elem, null);
        if(isEmpty()) {
            this.front = newNode;
            this.rear = newNode;
        }else{
            //添加到节点尾部
          this.rear.next = newNode;
          this.rear = newNode;
        }
        return true;
    }


    /**
     * 出队
     * */
    public Object dequeue(){
        if(isEmpty()) {
            return null;
        }else{
           Node node = this.front;

           //判断是否只有一个元素i
           if(this.front == this.rear) {
               //将头节点和尾节点只为空
               this.front = this.rear = null;
           }else{
               //调整头节点位置
               this.front = node.next;
               node.next = null;
           }
           return node.data;
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node node = this.front;
        while(node != null) {
            sb.append(node.data).append(" ");
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }

    //定义链表节点
    private static class Node{
        Object data;
        Node next;

        public Node(){}
        public Node(Object data, Node next){
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) {

        LinkedQueue queue = new LinkedQueue();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        System.out.println(queue);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        System.out.println(queue);
        queue.enqueue("d");
        System.out.println(queue);

    }

}
