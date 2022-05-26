package top.lovelily.oracle;

/**
 * Desc: TestStringIntern1
 * Author: Xu He
 * created: 2022/2/23 15:47
 *
 *  String str = "计算机软件";
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: ldc           #2                  // String 计算机软件
 *        2: astore_1
 *        3: return
 * }
 *
 *
 *
 *
 *
 *       String str1 = new StringBuilder("计算机").append("软件").toString();
 *
 *              0: new           #2                  // class java/lang/StringBuilder
 *        3: dup
 *        4: ldc           #3                  // String 计算机
 *        6: invokespecial #4                  // Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V
 *        9: ldc           #5                  // String 软件
 *       11: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
 *       14: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
 *       17: astore_1
 *       18: return
 * }
 *
 *
 *
 *
 *
 *  String str1 = new StringBuilder("计算机软件").toString();
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: new           #2                  // class java/lang/StringBuffer
 *        3: dup
 *        4: ldc           #3                  // String 计算机软件
 *        6: invokespecial #4                  // Method java/lang/StringBuffer."<init>":(Ljava/lang/String;)V
 *        9: astore_1
 *       10: return
 * }
 *
 *
 *
 *
 *  String a = "计算机" + "软件";
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: ldc           #2                  // String 计算机软件
 *        2: astore_1
 *        3: return
 *
 *
 *
 *
 *
 *         String b = "软件";
 *         String a = "计算机" + b;
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: ldc           #2                  // String 软件
 *        2: astore_1
 *        3: new           #3                  // class java/lang/StringBuilder
 *        6: dup
 *        7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
 *       10: ldc           #5                  // String 计算机
 *       12: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
 *       15: aload_1
 *       16: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
 *       19: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
 *       22: astore_2
 *       23: return
 * }
 *
 */

public class TestStringIntern1 {
    public static void main(String[] args) {
            // 放在 intern 调用前
//        String str = "计算机软件";
//        String str1 = new StringBuilder("计算机").append("软件").toString();
//        String intern = str1.intern();     // 常量池已经存在了，直接返回字符串
//        System.out.println(str == intern); // true
//        System.out.println(str1 == intern); // false 一个指向堆，一个指向常量池
//        System.out.println(str == str1);    // false 一个指向常量池, 一个指向堆

//        String str1 = new StringBuilder("计算机").append("软件").toString(); // 有 ldc 指令，是将"计算机"/"软件"放入常量池，因此是存放在堆里的，
//        String intern = str1.intern(); // 检查常量池，发现没有“计算机软件”，将“计算机软件”放入常量池，并返回对应的引用 str1！（实际上是个hashTable 常量池表，key 字符串内容对应的 hashCode, value是引用地址）
//         // 放在 intern 调用后
//        String str = "计算机软件"; // 到常量池里发现有“计算机软件”，(实际上是个hashTable 常量池表，key 字符串内容对应的 hashCode, value是引用地址) 并且是个地址，把地址直接返回并复赋值给 str, 复用了 str1 里的 char 数组！！！
//        System.out.println(str == str1);    // true   都指向了堆内存地址
//        System.out.println(str == intern); // true
//        System.out.println(str1 == intern); // true   都指向堆
//        System.out.println(str.intern() == str1); // true



//        String str1 = new StringBuilder("计算机软件").toString();// 有ldc 指令将，将 "计算机软件" 放入常量池
//        String intern = str1.intern(); // 检查常量池，发现有“计算机软件”，将“计算机软件” 返回
//        // 放在 intern 调用后
//        String str = new String("计算机软件");
//        System.out.println(str == str1);    // false   堆内存中的不同地址
//        System.out.println(str == intern); // false 堆内存中的不同地址
//        System.out.println(str1 == intern); // false   一个是堆/一个是常量池内容
//        System.out.println(str.intern() == str1); // false intern 方法还是首先检查常量池，找到了 str1 ，所以最终都指向上 str1 的堆内存地址！
//        System.out.println(str1.intern() == str.intern()); // true






    }
}
