package linearlist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<AnyType> implements Iterable<AnyType> {

    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker; //头节点
    private Node<AnyType> endMarkder; //尾节点

    /**
     * 初始化
     * */
    public MyLinkedList() {
        clear();
    }

    /**
     * 获取链表中元素个数
     * */
    public int size() {
        return theSize;
    }

    /**
     * 判断链表是否为空
     * */
    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * 在末尾添加
     * */
    public void add(AnyType x) {
        add(size(), x);
    }

    /**
     * 在指定的位置上添加
     * */
    public void add(int idx, AnyType x) {
        addBefore(getNode(idx), x);
    }

    /**
     * 在指定节点的前面添加
     * */
    public void addBefore(Node<AnyType> p, AnyType x) {
        //创建新节点
        Node<AnyType> newNode = new Node<AnyType>(x, p.prev, p);
        //调整指针
        p.prev.next = newNode;
        p.prev = newNode;

        theSize ++;
        modCount ++;
    }

    /**
     * 删除指定位置上的元素
     * */
    public void remove(int idx) {
        remove(getNode(idx));
    }

    /**
     * 删除指定的节点
     * */
    private AnyType remove(Node<AnyType> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;

        theSize --;
        modCount ++;

        return node.data;
    }

    /**
     * 获取指定位置上的Node
     * */
    private Node<AnyType> getNode(int idx) {

        Node<AnyType> temp;
        if(idx < 0 || idx > size()) {
            throw new IndexOutOfBoundsException();
        }

        if(idx < size() / 2) {
            //从前开始遍历
            temp = beginMarker;
            for(int i = 0;i < idx;i ++) {
                temp = temp.next;
            }
        }else{
            temp = endMarkder;
            for(int i = size();i > idx;i --) {
                temp = temp.prev;
            }
        }
        return temp;
    }


    /**
     * 重置链表
     * */
    public void clear(){

        //调整头和尾节点
        beginMarker = new Node<AnyType>(null, null, null);
        endMarkder = new Node<AnyType>(null, beginMarker, null);
        beginMarker.next = endMarkder;

        //清空相关数据
        theSize = 0;
        //记录本次修改
        modCount ++;
    }





    //链表节点
    private static class Node<AnyType> {

        public Node(AnyType data, Node<AnyType> prev, Node<AnyType> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        private AnyType data;
        private Node<AnyType> prev;
        private Node<AnyType> next;
    }

    public Iterator<AnyType> iterator() {
        return null;
    }

    private class LinkedListIterator implements Iterator<AnyType> {

        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext() {
            return current != endMarkder;
        }

        public AnyType next() {
            if(modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if(!hasNext()) {
                throw new NoSuchElementException();
            }

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;

            return nextItem;
        }

        public void remove() {
            if(modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if(!okToRemove) {
                throw new IllegalStateException();
            }
            MyLinkedList.this.remove(current.prev);
            okToRemove=false;
            //保持迭代器和列表中的modCount一致
            expectedModCount ++;
        }
    }
}
