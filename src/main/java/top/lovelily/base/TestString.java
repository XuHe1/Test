package top.lovelily.base;

import top.lovelily.User;

import java.math.BigDecimal;
import java.util.Arrays;

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
        String s1  = "abc";
        String ab = "ab";
        String c = "c";
        String str1 = new String("abc"); // str1在栈里，堆里新建了个String对象，内容为"常量池地址"，如果常量池不存在，加入常量池
        System.out.println(str1 == s); // false
        System.out.println("s==s1: " + (s == s1));
        System.out.println(str1.intern()==s); // true
        System.out.println(str1.intern()==str1); // false
        System.out.println(str1.intern() == (ab + c)); // false
        System.out.println(str1.intern() == (ab + "c")); // false
        System.out.println(str1.intern() == ("ab" + c)); // false
        System.out.println(str1.intern() == ("ab" + "c")); // true 都是静态字符串的结果会添加到字符串池，如果其中含有变量（如f中的e）则不会进入字符串池中
        System.out.println("s == \"ab\" + \"c\" : " + (s == "ab" + "c"));
        System.out.println("s == ab + c: " + (s == ab + c));
        System.out.println("s == ab + \"c\" : " + (s == ab + "c"));



        String str2 = new String("abc");
        System.out.println(str1 == str2); // false

        String str3 = "abcd";
        str3 = str3.substring(0,3);
        System.out.println(str3.intern() == s); // true

        String str4 = "ab" + "cd"; // 3个


        String string = "a" + "b" + "c";// 5个： a, b, c, ab, abc, string，优化后：只创建一个，abc


        // TestStatic.Inner inner =  new TestStatic.Inner();
        TestStatic.Inner inner =  new TestStatic().new Inner();
        TestStatic.StaticInner staticInner = new TestStatic.StaticInner();


        User user = new User(1,"xuhe");
        String name = user.getName();
        name = "xuhe2";
        user.setName(name);
        System.out.println(user.getName());

        System.out.println(Arrays.toString("aa".split(",")));

        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", "hello");
        System.out.println(url);

        url = url.replace("ACCESS_TOKEN", "hello2");
        System.out.println(url);

        BigDecimal a = new BigDecimal(2.22);
        BigDecimal b = new BigDecimal(2.220);
        System.out.println(a.equals(b));

        // 1
        System.out.println("AA".split("&").length);

    }
}
