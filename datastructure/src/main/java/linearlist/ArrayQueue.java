        package linearlist;

        /**
         * 队列的数组实现
         * @author mac
         * */
        public class ArrayQueue {

            private static final int DEFAULT_SIZE = 10;

            private Object[] items;    //数组容器
            private int front;         //头元素：指向第一个元素的前一个位置
            private int rear;          //尾元素：指向最后一个元素
            private int capacity;
            private int size;

            /**
             * 初始化
             * */
            public ArrayQueue(){
                items = new Object[DEFAULT_SIZE];
                this.capacity = DEFAULT_SIZE;
                front = rear = 0;
                size = 0;
            }

            public ArrayQueue(int capacity) {
                items = new Object[capacity];
                this.capacity = capacity;
                front = rear = 0;
                size = 0;
            }


            /**
             * 判断队列是否尾空
             *
             * 两种方法：
             * 1)判断size
             * 2）判断指针位置
             * */
            public boolean isEmpty() {
                return size == 0;
            }

            /**
             * 判断队列是否已满
             *
             * 两种方法：
             * 1）判断size
             * 2）判断指针
             * */
            public boolean isFull(){
                return size == capacity;
            }


            /**
             * 入队
             * */
            public boolean enqueue(Object elem) {
                if(isFull()) {
                    return false;
                }
                items[rear] = elem;
                //调整rear指针位置，向后移动或从头开始
                rear = (rear + 1) % capacity;
                size ++;
                return true;
            }

            /**
             * 出队
             * */
            public Object dequeue(){
                if(isEmpty()) {
                    return null;
                }
                Object obj = items[front];  //返回队列的头节点
                size --;
                //调整front指针
                front = (front + 1) % capacity;
                return obj;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                if(isEmpty()) {
                    sb.append("]");
                }else {
                    int i = front;
                    int loop = 0;
                    while (loop < size) {
                        sb.append(items[i]).append(",");
                        i = (i + 1) % capacity;
                        loop ++;
                    }
                    sb.append("]");
                }
                return sb.toString();
            }

            public static void main(String[] args) {

                ArrayQueue arrayQueue = new ArrayQueue(5);

                arrayQueue.enqueue("a");
                arrayQueue.enqueue("b");
                arrayQueue.enqueue("d");

                System.out.println(arrayQueue);
                arrayQueue.enqueue("e");
                arrayQueue.enqueue("f");
                System.out.println(arrayQueue);
                System.out.println(arrayQueue.dequeue());
                arrayQueue.enqueue("g");
                System.out.println(arrayQueue);

            }

        }
