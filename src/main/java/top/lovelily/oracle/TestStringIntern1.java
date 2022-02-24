package top.lovelily.oracle;

/**
 * Desc: TestStringIntern1
 * Author: Xu He
 * created: 2022/2/23 15:47
 */

public class TestStringIntern1 {
    public static void main(String[] args) {
            // 放在 intern 调用前
//        String str = "计算机软件";
//        String str1 = new StringBuilder("计算机").append("软件").toString();
//        String intern = str1.intern();
//        System.out.println(str == intern); // true  都指向常量池
//        System.out.println(str1 == intern); // false 一个指向堆，一个指向常量池
//        System.out.println(str == str1);    // false 一个指向常量池, 一个指向堆

//        String str1 = new StringBuilder("计算机").append("软件").toString(); // 没有 ldc 指令，没有放入常量池，因此是存放在堆里的，
//        String intern = str1.intern(); // 检查常量池，发现没有“计算机软件”，将“计算机软件”对应的引用 str1 放入常量池！（实际上是个hashTable 常量池表，key 字符串内容对应的 hashCode, value是引用地址）
//         // 放在 intern 调用后
//        String str = "计算机软件"; // 到常量池里发现有“计算机软件”，(实际上是个hashTable 常量池表，key 字符串内容对应的 hashCode, value是引用地址) 并且是个地址，把地址直接返回并复赋值给 str, 复用了 str1 里的 char 数组！！！
//        System.out.println(str == str1);    // true   都指向了堆内存地址
//        System.out.println(str == intern); // true
//        System.out.println(str1 == intern); // true   都指向堆
//        System.out.println(str.intern() == str1); // true



        String str1 = new StringBuilder("计算机").append("软件").toString(); // 没有 ldc 指令，没有放入常量池，因此是存放在堆里的，
        String intern = str1.intern(); // 检查常量池，发现没有“计算机软件”，将“计算机软件”对应的引用 str1 放入常量池！（实际上是个hashTable 常量池表，key 字符串内容对应的 hashCode, value是引用地址）
        // 放在 intern 调用后
        String str = new String("计算机软件");
        System.out.println(str == str1);    // false   堆内存中的不同地址
        System.out.println(str == intern); // false 堆内存中的不同地址
        System.out.println(str1 == intern); // true   都指向堆中相同地址, jdk1.6中返回false，intern 方法会将字符串实例复制到永久代中的常量池中，并返回的永久代的地址，
        System.out.println(str.intern() == str1); // true intern 方法还是首先检查常量池，找到了 str1 ，所以最终都指向上 str1 的堆内存地址！


    }
}
