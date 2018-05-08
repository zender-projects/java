package test;

public class Test01 {


    public void printN1(int n){
        for(int i = 1;i <= n;i ++) {
            System.out.println(i);
        }
    }

    public void printN2(int n) {
        if(n != 0) {
            printN2(n - 1);
            System.out.println(n);
        }
    }

    public static void main(String[] args) {

        int n = 100000;

        Test01 test01 = new Test01();
        long start1 = System.currentTimeMillis();
        test01.printN1(n);
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        long start2 = System.currentTimeMillis();
        test01.printN2(n);
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);

    }
}
