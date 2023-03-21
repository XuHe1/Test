package top.lovelily.oracle;

public class TestStringIntern_002 {

    public static void main(String[] args) {
        String str2 = new String("str")+new String("01");     //(1)
        String intern = str2.intern();				//（2）   将对象地址放入常量池 "str01"-> str2    1.6 将字符串 str01 复制到常量池
        String str1 = "str01";		//（3）    常量池中已经有里了


        System.out.println(intern == str2); // 都指向 堆！！

        System.out.println(intern == str1);


        System.out.println(str2==str1);
        //输出值为：true

        System.out.println(intern==str1);
    }
}
