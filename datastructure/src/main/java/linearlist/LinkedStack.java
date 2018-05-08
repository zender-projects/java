package linearlist;

/**
 * 栈的链式存储结构.
 * @author mac
 * */
public class LinkedStack<T> {

    private Node<T> top;
    private int size;

    /**
     * 初始化
     * */
    public LinkedStack(){
        this.top = null;
        this.size = 0;
    }

    public LinkedStack(T element) {
        top = new Node<T>(element, null);
        this.size = 1;
    }

    /**
     * 入栈
     * */
    public void push(T element) {
        Node<T> node = new Node<T> (element, top);
        top = node;
        this.size ++;
    }

    /**
     * 出栈
     * */
    public T pop(){
        if(!isEmpty()) {
            Node<T> oldTop = top;
            top = top.next;
            oldTop.next = null;
            this.size--;
            return oldTop.data;
        }
        return null;
    }

    public T peek(){
        if(!isEmpty()) {
            return top.data;
        }
        return null;
    }


    /**
     * 清空
     * */
    public void clear(){
        top = null;
        this.size = 0;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * 定义节点
     * */
    private static class Node<T> {
        T data;
        Node<T> next;
        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

}
