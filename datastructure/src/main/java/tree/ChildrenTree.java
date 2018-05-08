package tree;

import java.util.Arrays;
import java.util.Objects;

/**
 * 树的孩子表示法.
 * @author mac
 * */
public class ChildrenTree {

    private static Integer DEFAULT_CAPACITY = 10;
    private Node[] items;
    private Integer capacity;
    private Integer size;


    public ChildrenTree(){
        this.capacity = DEFAULT_CAPACITY;
        items = new Node[capacity];
        size = 0;
    }

    public ChildrenTree(Integer capacity) {
        this.capacity = capacity;
        this.items = new Node[capacity];
        this.size = 0;
    }

    /**
     * 清空树
     * */
    public void clear(){
        this.items = new Node[capacity];
        this.size =  0;
    }

    /**
     * 判断是否为空
     * */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 返回当前节点的总树
     * */
    public Integer size(){
        return size;
    }

    /**
     * 返回树的深度
     * */
    public Integer deep(){
        Integer deep = 0;
        if(size > 0) {
            for(int i = 0;i < items.length;i ++)
            {
                if(!Objects.isNull(items[i])) {
                    Integer currentDeep = 1;
                    ChildrenNode childPointer = items[i].firstChild;
                    while(childPointer != null) {
                        childPointer = childPointer.nextChild;
                        currentDeep ++;
                    }
                    if(currentDeep > deep){
                        deep = currentDeep;
                    }
                }
            }
        }
        return deep;
    }

    /**
     * 根据节点获取索引下标
     * */
    public Integer index(Node node) {
        for(int i = 0;i < items.length;i ++)
        {
            if(!Objects.isNull(items[i]) && items[i] == node) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据节点值获取索引
     * */
    public Integer index(Object value) {
        for (int i = 0;i < items.length;i ++) {
            if(!Objects.isNull(items[i]) && items[i].data.equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void addRoot(Object data) {
        for (int i = 0; i < items.length; i++) {
            if(Objects.isNull(items[i])) {
                items[i] = new Node(data, -1);
                break;
            }
        }
    }

    public void add(Object parentValue , Object data) {
        Integer parentIndex = index(parentValue);
        if(parentIndex == -1) {
            throw new RuntimeException("父节点不存在");
        }
        Integer newNodeIndex = -1;
        for(int i = 0;i < items.length;i ++) {
            if(Objects.isNull(items[i])) {
                items[i] = new Node(data, parentIndex);
                newNodeIndex = i;
                break;
            }
        }
        if(newNodeIndex == -1) {
            throw new RuntimeException("添加节点失败");
        }
        Node parent = items[parentIndex];
        ChildrenNode childrenNode = new ChildrenNode();
        childrenNode.childIndex = newNodeIndex;
        ChildrenNode first = parent.firstChild;
        if(Objects.isNull(first)){
            parent.firstChild = childrenNode;
            this.size ++;
            return;
        }
        while(!Objects.isNull(first) && first.nextChild!= null) {
            first = first.nextChild;
        }

        first.nextChild = childrenNode;
        this.size ++;
    }

    public void print(){
        for(int i = 0;i < items.length &&
                !Objects.isNull(items[i]);i ++) {

            System.out.println(items[i]);
        }
    }



    private class Node{
        private Object data;
        private Integer parentIndex;   //父节点的索引
        private ChildrenNode firstChild;  //孩子链的头指针

        public Node(Object data, Integer parentIndex) {
            this.data =data;
            this.parentIndex = parentIndex;
        }

        @Override
        public String toString() {
            return "[data : " + data + ", parent index:" + parentIndex+"]";
        }
    }

    private class ChildrenNode{
        Integer childIndex;  //当前子节点的索引
        ChildrenNode nextChild; //下一个子节点的指针
    }


    public static void main(String[] args) {

        ChildrenTree tree = new ChildrenTree();
        tree.addRoot("a");
        tree.add("a", "b");
        tree.add("a", "c");
        tree.add("c", "d");
        tree.add("c", "e");
        tree.add("b", "f");

        tree.print();

        System.out.println(tree.deep());
    }
}
