package top.lovelily.oracle;

public class TestStringIntern_003 {

    public static void main(String[] args) {
        String str2 = new String("str01");     //(1)  首先在常量池创建 str01 再在堆中创建对象保存常量池的地址
        String intern = str2.intern();				//（2）   将对象地址放入常量池 "str01"-> str2    1.6 将字符串 str01 复制到常量池
        String str1 = "str01";		//（3）    常量池中已经有里了


        System.out.println(intern == str2); // false 常量池   堆！！

        System.out.println(intern == str1); // true 常量池


        System.out.println(str2==str1);  // 堆 常量池
        //输出值为：true

        System.out.println(intern==str1); // 常量池


        System.out.println("11".contains("12"));
    }
}
