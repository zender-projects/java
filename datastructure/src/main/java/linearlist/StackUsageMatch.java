package linearlist;

import java.util.LinkedList;

/**
 * 匹配字符是否成对
 *
 * [[([{}]([])()[])]]  true
 * ([(}])   false
 * */
public class StackUsageMatch {

    public static boolean isMatch(String str) {
        LinkedList<Character> stack = new LinkedList<Character>();
        char[] chars = str.toCharArray();
        for(Character c : chars) {
            Character temp = stack.poll();
            if(temp == null){
                stack.push(c);
            }else if(temp == '[' && c == ']'){
                //配对成功，新char不入栈，temp弹出，不入栈
            }else if(temp == '(' && c == ')'){
                //同上
            }else if(temp == '{' && c == '}'){
                //同上
            }else if(temp == '<' && c == '>'){

            }
            else{
                //匹配失败，将temp重新入栈，同时将c入栈, 注意入栈顺序不能变
                stack.push(temp);
                stack.push(c);
            }
        }
        //栈为空，则配对成功
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isMatch("{<[()]>}"));
        System.out.println(isMatch("{<(]>"));
    }
}
