package imooc._03;


//https://leetcode-cn.com/problems/valid-parentheses/

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

 有效字符串需满足：

 左括号必须用相同类型的右括号闭合。
 左括号必须以正确的顺序闭合。
 注意空字符串可被认为是有效字符串。

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/valid-parentheses
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * */
import java.util.Stack;
public class Solution {

    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();

        for(int i = 0;i < chars.length;i ++) {
            char currentChar = chars[i];
            if('{' == currentChar || '(' == currentChar || '[' == currentChar) {
                stack.push(currentChar);
            }else {
                if(stack.isEmpty()) {
                    return false;
                }

                char topChar = stack.pop();
                if(')' == currentChar && '(' != topChar) {
                    return false;
                }

                if('}' == currentChar && '{' != topChar){
                    return false;
                }

                if(']' == currentChar && '[' != topChar) {
                    return false;
                }
            }
        }
        //如果为空，则才说明匹配成功  如果有剩余，则匹配失败
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isValid("()"));
    }
}
