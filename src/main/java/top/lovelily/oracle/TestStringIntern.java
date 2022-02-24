package top.lovelily.oracle;

/**
 * Desc: 调用 intern 方法时，如果常量池已经存在相等的字符串（equals），返回该常量池里字符串的引用（首次引用），
 * 否则，添加到常量池，并返回对该常量池字符串的引用。
 * Author: Xu He
 * created: 2021/10/25 9:51
 */

public class TestStringIntern {

        public static void main(String[] args) {




            String a = new String("ab");
            String b = new String("ab");
            String c = "ab";
            System.out.println(b.intern() == a); // false
            System.out.println(b.intern() == c); // true 都是指向常量池


            System.out.println("=============================================");
            // intern ： 如果字符串已经存在常量池，就直接返回字符串，否则加入常量池，并返回对该字符串的引用
            // 首次遇到，返回引用，常量池已经放入堆里，所以是同一个引用，1.6 常量池放在永久代里所以不同（一个是指向永久代里的引用，一个是指向堆里的引用）
            // 1.6 会将首次出现的字符串复制到永久代的常量池中，返回的也是永久代中对该字符串的引用（地址）
            // 1.7 后没有永久代，new 的字符串会放在常量池中，intern 方法会检查常量池是否有相等equals的字符串，如果存在则返回该引用
            String str = "计算机软件";
            String str1 = new StringBuilder("计算机").append("软件").toString();
            String intern = str1.intern();
            //String str = "计算机软件"; // 放在 str1 上面一行，true false false
            System.out.println("**********************************************");
            System.out.println(str == intern); // true  常量池？
            System.out.println(str1 == intern); // true
            System.out.println(str == str1);    // true todo?

            System.out.println("**********************************************");



            // 在堆中，指向常量池
            String str2 = new StringBuilder("ja").append("va").toString();
            // 常量池里的引用（系统预创建的引用）
            System.out.println(str2.intern() == str2); // false

            String str3 = "计算机软件1";
            System.out.println(str3.intern() == str3); // true

            // 指向常量池里的 java 字符串
            //String str2 = "java";
            // 常量池里的引用（系统预创建的引用），两个都是常量池 java 的地址，因此是同一个引用！
            //System.out.println(str2.intern() == str2); // true


            String str0 = "计算机软件2";
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
