package top.lovelily.base;

import top.lovelily.User;

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

    public static void updateInteger(Integer a) {
       // a = 100;
        a = new Integer(128); // todo:new的对象会在方法结束后立刻回收吗？如果没有引用是的，
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法结束");
    }

    public static void updateString(String s) {
        s = "Hello, World";
        //s = new String("Hello, World");
    }

    public static void updateUser(User user) {
        user.setName("Tom"); // 改的是原先指向的堆内存地址的对象
        // 方法参数传递是副本传递，基本类型存的是数值， 引用类型存的是地址，没有真正的"应用传递"，这里的user 跟实参并不是同一个， 只是值相同的一个副本。
        user = new User(1, "Xuhe"); // 重新指向了新的空间， 方法结束被回收，所以调用方法里还是老的地址；
        user.setName("Xuhe"); //改的是新对象的属性
    }

    public static void main(String[] args) {
//        TestBoxUnBox test = new TestBoxUnBox();
//        test.updateUser(null);
//
        // Integer.valueOf会自动缓存 -128～127的数据, 否则直接new
        System.out.println(Integer.valueOf(0) == a);
        System.out.println(Integer.valueOf(0) == Integer.valueOf(0)); // true
        System.out.println(Integer.valueOf(128) == Integer.valueOf(128)); //false


        //User user = null;
        User user = new User(1, "tom");
        updateUser(user);
        System.out.println(user.getName());

        // 不可变对象， value是final类型
        Integer num  = null;//= new Integer(0);
        new Thread(() -> {
            updateInteger(num);
        }).start();

        System.out.println(num);

        Integer var1 = 1;
        Integer var2 = 1;
        Integer var3 = new Integer(1);
        Integer var4 = new Integer(1);
        System.out.println(var1 == var2); // true
        System.out.println(var1 == var3); // false
        System.out.println(var3 == var4); // false


        String str = "abc";
        updateString(str);

        System.out.println(str);


        Integer a = 100, b = 100;    // 直接赋值实际调用的是Integer.valueOf()
        Integer c = 1000, d = 1000;
        System.out.println(a==b); // true
        System.out.println(c==d); // false

    }
}
