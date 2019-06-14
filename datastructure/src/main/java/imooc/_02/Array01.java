package imooc._02;

public class Array01 {
    public static void main(String[] args) {


        int[] arr = new int[10];
        for(int i = 0 ; i < arr.length ; i ++) {
            arr[i] = i;
        }

        int[] scores = new int[]{100, 99, 600};
        for (int i = 0;i < scores.length ; i ++) {
            System.out.println(scores[i]);
        }

        scores[0] = 98;
    }
}
