package top.lovelily.base;

/**
 * @author h.xu
 * @create 2017-11-28 上午10:00
 **/

public class TestString {
    private StringBuilder stringBuilder;

    public static void main(String[] args) {
        String str;
        StringBuffer stringBuffer;  // synchronized
        StringBuilder stringBuilder; //

        String s = "abc";//
        String str1 = new String("abc"); // str1在栈里，堆里新建了个String对象，内容为"abc"，如果常量池不存在，加入常量池
        System.out.println(str1 == s); // false
        System.out.println(str1.intern()==s); // true

        String str2 = new String("abc");
        System.out.println(str1 == str2); // false

        String str3 = "abcd";
        str3 = str3.substring(0,3);
        System.out.println(str3.intern() == s); // true

        String str4 = "ab" + "cd"; // 3个
    }
}
