package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 树的双亲表示法.
 * @author mac
 * */
public class ParentTree {

    private static Integer DEFAULT_CAPACITY = 10;
    private Node[] items;
    private Integer capacity;
    private Integer size;

    private static class Node{
        Object data;
        Integer parentIndex;

        public Node(Object data, Integer parentIndex) {
            this.data = data;
            this.parentIndex = parentIndex;
        }

        @Override
        public String toString() {
            return "[data: " + data +", parent index: " + parentIndex + "]";
        }
    }

    /**
     * 初始化一颗空树
     * */
    public ParentTree(){
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.items = new Node[this.capacity];
    }

    public ParentTree(Integer capacity){
        this.capacity = capacity;
        this.size = 0;
        this.items = new Node[this.capacity];
    }

    /**
     * 清空树
     * */
    public void clear(){
        this.items = new Node[this.capacity];
        this.size = 0;
    }

    /**
     * 判断是否为空
     * */
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * 返回当前树的总节点数
     * */
    public Integer size(){
        return this.size;
    }

    /**
     * 求树的深度
     * */
    public Integer deep(){
        Integer deep = 0;
        for(int i = 0;i < items.length;i ++) {
            Integer currentDeep = 1;
            if(!Objects.isNull(items[i])){
               for(int j = 0;j < items.length;j ++) {
                   if(!Objects.isNull(items[j]) && items[j].parentIndex == i){
                      /* Node node = items[j];
                       Integer parentIndex = node.parentIndex;
                       if(parentIndex == i) {*/
                           currentDeep ++;
                       //}
                   }
               }
            }
            //deep = currentDeep;
            if(currentDeep > deep) {
                deep = currentDeep;
            }
        }
        return deep;
    }

    /**
     * 返回树的根节点
     * */
    public Node root(){
        for(int i = 0;i < items.length;i ++) {
            if(!Objects.isNull(items[i]) && items[i].parentIndex == -1) {
                return items[i];
            }
        }
        return null;
    }

    /**
     * 返回Node的数组index
     * */
    public Integer index(Node node) {
        for(int i = 0;i < items.length;i ++) {
            if(items[i] == node){
                return i;
            }
        }
        return -1;
    }

    public Integer index(Object value) {
        for(int i = 0;i < items.length;i ++) {
            if(items[i].data.equals(value)){
                return i;
            }
        }
        return -1;
    }


    /**
     * 添加数据
     * */
    public Integer add(Integer parent, Object data){
        if(size == items.length) {
            throw new RuntimeException("树已满");
        }
        Node newNode = new Node(data, parent);
        for(int i = 0;i < items.length;i ++) {
            if(items[i] == null) {
                items[i] = newNode;
                this.size ++;
                return i;
            }
        }
        return -1;
    }

    public Integer add(Object parentValue, Object data){
        Integer idx = index(parentValue);
        return add(idx, data);
    }


    public Integer addRoot(Object data) {
        for(int i = 0;i < items.length;i ++) {
            if(!Objects.isNull(items[i])) {
                if (items[i].parentIndex == -1) {
                    throw new RuntimeException("根节点已存在");
                }
            }
        }
        for(int i = 0;i < items.length;i ++)
        {
            if(Objects.isNull(items[i])){
                items[i] = new Node(data, -1);
                return i;
            }
        }
        return -1;
    }



    /**
     * 递归遍历
     * */
    public void print(Node root){
        List<Node> children = new ArrayList<Node>();
        Integer nodeIdx = index(root);
        for(int i  = 0;i < items.length;i ++) {
            if(!Objects.isNull(items[i]) && items[i].parentIndex == nodeIdx){
                children.add(items[i]);
            }
        }
        if(children.size() > 0){
            children.stream().forEach(child -> {
                print(child);
            });
        }
        System.out.println(root);
    }

    public static void main(String[] args) {
        ParentTree parentTree = new ParentTree();
        parentTree.addRoot("a");
        parentTree.add("a", "b");
        parentTree.add("a", "c");
        parentTree.add("b", "d");

        parentTree.print(parentTree.root());
        System.out.println(parentTree.deep());
    }

}
