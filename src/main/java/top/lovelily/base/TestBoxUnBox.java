package top.lovelily.base;

import top.lovelily.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc: TestBoxUnBox
 * Author: xuhe
 * Date: 2019/9/19 10:25 AM
 * Version: 1.0
 */
public class TestBoxUnBox {
    String test2;

    /**
     * 逃逸
     */
    void test02() {
        test2 = "test2";
    }

    /**
     * 无逃逸
     */
    void test01() {
        String test1 = "test1";
    }



    // 注意： 由于model里是基本类型， 参数里是引用类型， 实际项目中，如果参数optional就会出现NPE
    public void updateUser(Integer id) {
        User user = new User(1, "xuhe");
        user.setId(id);
    }
    private static  Integer a = new Integer(0);




    public static void main(String[] args) {
//        TestBoxUnBox test = new TestBoxUnBox();
//        test.updateUser(null);
//
        // Integer.valueOf会自动缓存 -128～127的数据, 否则直接new
        System.out.println(Integer.valueOf(0) == a);
        System.out.println(Integer.valueOf(0) == Integer.valueOf(0)); // true
        System.out.println(Integer.valueOf(128) == Integer.valueOf(128)); //false

        // 引用数据类型 == 比较会进行 unbox 操作，导致NPE！！！
        // 可以用 equals 比较，一方面避免了 NPE ,另一方面也防止 -128～127 外的数据 == 判断不准确问题
        //System.out.println(num == 1);


        Integer var1 = 1;
        Integer var2 = 1;
        Integer var3 = new Integer(1);
        Integer var4 = new Integer(1);
        System.out.println(var1 == var2); // true
        System.out.println(var1 == var3); // false
        System.out.println(var3 == var4); // false

        Integer a = 100, b = 100;    // [-128, 127] 会被缓存！直接赋值实际调用的是Integer.valueOf() !!
        Integer c = 1000, d = 1000;
        System.out.println(a==b); // true
        System.out.println(c==d); // false
        System.out.println("a==100 " + (a == 100));

        // Long 和 Integer也是，因此比较两个 Integer 或 Long 变量时一定要用equals
        Long l1 = -129l;
        Long l2 = -129l;
        System.out.println("l1==l2 "+ (l1 == l2));

        // Map中的装箱与拆箱
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        map1.putIfAbsent("a", 131);
        map2.put("a",131);
        System.out.println(map1.get("a").equals(map2.get("a"))); // true
        System.out.println(map1.get("a") == map2.get("a"));  // false

    }
}
