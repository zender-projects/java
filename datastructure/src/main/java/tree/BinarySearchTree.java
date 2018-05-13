package tree;
import tree.LinkedBinaryTree.Node;

/**
 * 二叉搜索树.
 * @author mac
 * */
public class BinarySearchTree {

    private LinkedBinaryTree root;

    public BinarySearchTree(LinkedBinaryTree tree) {
        this.root = tree;
    }

    /**
     * 1.查找给定值的节点
     * 2.查找最小值
     * 3.查找最大值
     * 4.插入一个节点
     * 5.删除一个节点
     * */

    /**
     * 查找给定值的节点-递归
     * */
    public Node find(Object value, Node node){
        if(node == null)
            return null;

        Integer v = (Integer)value;
        Integer nv = (Integer)node.getData();

        if(v > nv) {
            return find(value, node.getRight());
        }else if(v < nv) {
            return find(value, node.getLeft());
        }else{
            return node;
        }
    }

    /**
     * 查找给定值的节点-循环
     * */
    public Node find2(Object value, Node root) {
        Node node = root;
        Integer findValue = (Integer)value;
        while(root != null) {
            Integer v = (Integer)node.getData();
            if(findValue > v) {
                node = node.getRight();
            }else if(findValue < v) {
                node = node.getLeft();
            }else{
                return node;
            }
        }
        return null;
    }

    /**
     * 查找最小值
     * */
    public Node findMin(Node node) {
        if(node == null) {
            return null;
        }else if(node.getLeft() == null) {
            return node;
        }else{
            return findMin(node.getLeft());
        }
    }

    /**
     * 查找最大值
     * */
    public Node findMax(Node node) {
        if(node == null) {
            return null;
        }else if(node.getRight() == null) {
            return node;
        }else{
            return findMax(node.getRight());
        }
    }

    public Node findMax2(Node root) {
        Node node = root;
        if(node != null) {
            while (node.getRight() != null) {
                node = node.getRight();
            }
        }
        return node;
    }

    /**
     * 插入
     */
   public Node insert(Object value, Node node) {
       if(node == null) {
           node = new Node(value, null, null);
       }else{
            Integer v = (Integer)value;
            Integer nv = (Integer)node.getData();
            if(v < nv) {
                node.setLeft(insert(value, node.getLeft()));
            }else if(v > nv) {
                node.setRight(insert(value, node.getRight()));
            }
       }
       return node;
   }


}
