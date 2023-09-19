package top.lovelily.algrithm.leetcode;

import java.util.*;

public class BrachetMatch {

        public boolean isValid(String s) {
            if(s == null || s.length() ==0) {
                return false;
            }

            Map<Character, Character> map =new HashMap();
            map.put(')', '(');
            map.put(']', '[');
            map.put('}', '{');
            Stack stack = new Stack();
            // 入栈
            for(Character ch : s.toCharArray()) {
                stack.push(ch);
            }

            Set<Character> tmp = new HashSet();
            Stack right = new Stack();
            for(int i=0;i< s.length();i++) {
                Object obj = stack.pop();
                char ch = (char)obj;
                // 出栈的右括号， 把匹配的左括号放入集合
                if(map.keySet().contains(ch)) {
                    right.push(map.get(ch));

                }

                //  出栈的是左括号，移除集合
                if(right.contains(ch) && right.pop().equals(ch)) {
                    continue;
                }


            }

            if(tmp.size() == 0) {
                return true;
            }

            return false;


        }


    public static void main(String[] args) {
        BrachetMatch test = new BrachetMatch();
        test.isValid("()");
    }
}
