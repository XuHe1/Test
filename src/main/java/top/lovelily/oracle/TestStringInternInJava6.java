package top.lovelily.oracle;

/**
 * Desc: TestStringIntern
 * Author: Xu He
 * created: 2021/10/25 9:51
 */

public class TestStringInternInJava6 {

        public static void main(String[] args) {



            String str1 = new StringBuilder("ab").append("c").toString();
            String str = "abc";
            String intern = str1.intern();
            //String str = "abc";
            System.out.println(str == intern); // true
            System.out.println(str1 == intern); // false
            System.out.println(str == str1);    // false


        }

}
