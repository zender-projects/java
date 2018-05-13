package tree;

import javafx.geometry.Pos;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 链表二叉树的实现.
 * @author mac
 * */
public class LinkedBinaryTree {

    //根节点
    private Node root;

    public LinkedBinaryTree() {
        root = null;
    }

    public LinkedBinaryTree(Node root) {
        this.root = root;
    }

    public LinkedBinaryTree(Object rootData) {
        root = new Node(rootData, null, null);
    }


    /**
     * 添加节点
     * @param parentData
     * @param newData
     * @param position
     * */
    public void add(Object parentData, Object newData, Position position){

        //构造一个新的节点
        Node newNode = new Node(newData, null, null);
        //根据父节点的数据找到父节点对应的Node
        Node parentNode = findNode(this.root, parentData);

        if(Objects.isNull(parentData)) {
            throw new RuntimeException("父节点不存在");
        }

        switch (position.name()){
            case "LEFT":
                parentNode.left = newNode;
                break;
            case "RIGHT":
                parentNode.right = newNode;

            /*default:
                throw new RuntimeException("无效的位置");
                */
        }
    }


    /**
     * 根据指定数据查找Node节点
     * */
    public Node findNode(Node root, Object data) {
        Node tempRoot = root;
        if(Objects.isNull(tempRoot)) {
            return null;
        }else{
            if(tempRoot.data.equals(data)){
                return tempRoot;
            }else{
                tempRoot = findNode(tempRoot.left, data);
                if(!Objects.isNull(tempRoot)){
                    return tempRoot;
                }else if((tempRoot = findNode(tempRoot.right, data)) != null) {
                    return tempRoot;
                }else{
                    return null;
                }
            }
        }
    }

    public Node root(){
        return this.root;
    }

    /**
     * 获取左子树
     * */
    public Node findLeftNode(Node node) {
        return !Objects.isNull(node) ? node.left : null;
    }

    /**
     * 获取右子树
     * */
    public Node findRightNode(Node node) {
        return !Objects.isNull(node) ? node.right : null;
    }

    /**
     * 判断空树
     * */
    public boolean isEmpty(){
        return this.root == null;
    }

    /**
     * 计算二叉树的深度
     * TODO 待调试
     * */
    public int deep(Node node) {
        int leftDeep, rightDeep;
        if(Objects.isNull(node)) {
            return 0;
        }
        else {
            leftDeep = deep(node.left);
            rightDeep = deep(node.right);

            /*if(leftDeep > rightDeep) {
                return leftDeep + 1;
            }else {
                return rightDeep + 1;
            }
            */
            return leftDeep > rightDeep ? leftDeep + 1 : rightDeep + 1;
        }
    }

    /**
     * 清空二叉树
     * */
    public void clear(Node node) {
        if(!Objects.isNull(node)) {
            clear(node.left);
            clear(node.right);
            node = null;  //help GC
        }
    }

    /**
     * 先序遍历
     * 根节点->左子节点->右子节点
     * */
    public void dlrLoop(Node node) {
        if(!Objects.isNull(node)) {
            printNode(node);
            dlrLoop(node.left);
            dlrLoop(node.right);
        }
    }

    /**
     * 循环先序遍历
     * */
    public void dlrLoop2(Node root) {
        Node node = root;
        LinkedList<Node> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            while(node != null) {
                printNode(node);
                stack.push(node);
                node = node.left;
            }
            if(!stack.isEmpty()) {
                Node popNode = stack.pop();
                node = popNode.right;
            }
        }
    }

    /**
     * 中序遍历
     * */
    public void ldrLoop(Node node) {
        if(!Objects.isNull(node)) {
            ldrLoop(node.left);
            printNode(node);
            ldrLoop(node.right);
        }
    }

    /**
     * 循环中序遍历
     * */
    public void ldrLoop2(Node root) {
        Node node = root;
        LinkedList<Node> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()) {
            while(node != null) {
                stack.push(node);
                node = node.left;
            }

            if(!stack.isEmpty()) {
                Node popNode = stack.pop();
                printNode(popNode);
                node = popNode.right;
            }
        }
    }

    /**
     * 后序遍历
     * */
    public void lrdLoop(Node node) {
        if(!Objects.isNull(node)){
            lrdLoop(node.left);
            lrdLoop(node.right);
            printNode(node);
        }
    }

    /**
     * 循环后续遍历
     * */
    /*public void lrdLoop2(Node root) {
        Node node = root;
        LinkedList<Node> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()) {
            //printNode(node);
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
            if(!stack.isEmpty()) {
            
                Node popNode = stack.pop();
                node = popNode.right;
                //printNode(node);
            }
        }
    }
    */
    /**
     *层序遍历
     * */
    public void levelOrderLoop (Node root) {
        Node node = root;
        LinkedList<Node> queue = new LinkedList<>();
        queue.addFirst(root);

        while(!queue.isEmpty()) {
            Node temp = queue.removeLast();
            printNode(temp);
            if(temp.left != null)
                queue.addFirst(temp.left);
            if(temp.right != null)
                queue.addFirst(temp.right);
        }
    }

    private void printNode(Node node)  {
        System.out.printf("%s ", node.data);
    }

    private static enum Position {
        LEFT,RIGHT
    }

    public static class Node {
        private Object data;
        private Node left, right;
        public Node(Object data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }


    public static void main(String[] args) {
        LinkedBinaryTree tree = new LinkedBinaryTree("root");
        tree.add("root", "a", Position.LEFT);
        tree.add("root", "b", Position.RIGHT);
        tree.add("a", "c", Position.LEFT);
        tree.add("a", "d", Position.RIGHT);

        tree.ldrLoop(tree.root());
        System.out.println();
        tree.ldrLoop2(tree.root());
        System.out.println();

        tree.dlrLoop(tree.root());
        System.out.println();
        tree.dlrLoop2(tree.root());


        System.out.println();
        tree.lrdLoop(tree.root());
        System.out.println();
        //tree.lrdLoop2(tree.root());
        System.out.println();

        tree.levelOrderLoop(tree.root());
        
        System.out.println();
        System.out.println(tree.deep(tree.root));
    }
}
