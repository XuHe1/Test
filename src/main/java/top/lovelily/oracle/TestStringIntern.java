package top.lovelily.oracle;

/**
 * Desc: TestStringIntern
 * Author: Xu He
 * created: 2021/10/25 9:51
 */

public class TestStringIntern {

        public static void main(String[] args) {
            // intern ： 如果字符串已经存在常量池，就直接返回字符串，否则加入常量池，并返回对该字符串的引用
            // 首次遇到，返回引用，常量池已经放入堆里，所以是同一个引用，1.6 常量池放在永久代里所以不同（一个是指向永久代里的引用，一个是指向堆里的引用）
            String str1 = new StringBuilder("计算机").append("软件").toString();
            System.out.println(str1 == str1.intern()); // true

            // 不是首次遇到，intern 返回的是字符串而非引用！！！
            String str2 = new StringBuilder("ja").append("va").toString();
            System.out.println(str2.intern() == str2); // false

            String str3 = "计算机软件1";
            System.out.println(str3.intern() == str3); // true


            String str = "计算机软件2";
            System.out.println(System.identityHashCode(str));

            String str4 = new String("计算机软件2");
            String str41 = new StringBuilder("计算机").append("软件3").toString();
            System.out.println(str41 == str41.intern());
            System.out.println(System.identityHashCode(str41));
            System.out.println(System.identityHashCode(str41.intern()));
            System.out.println(System.identityHashCode(str4));
            System.out.println(System.identityHashCode(str4.intern()));
            System.out.println(str4.intern() == str4); // false

        }
}
