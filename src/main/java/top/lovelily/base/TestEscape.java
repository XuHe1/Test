package top.lovelily.base;

import top.lovelily.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestEscape {

    public static void updateString(String s) {
        s = "Hello, World";
        //s = new String("Hello, World");
    }

    public static void updateInteger(Integer a) {
        a = 100;
        //a = new Integer(128);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法结束");
    }

    public static void updateUser(User user) {
        user.setName("Tom"); // 改的是原先指向的堆内存地址的对象
        // 方法参数传递是副本传递，基本类型存的是数值， 引用类型存的是地址，没有真正的"应用传递"，这里的user 跟实参并不是同一个， 只是值相同的一个副本。
        user = new User(1, "Xuhe"); // 重新指向了新的空间， 方法结束被回收，所以调用方法里还是老的地址；
        user.setName("Xuhe"); //改的是新对象的属性
    }


    private static void filter(List<User> list) {
        // list 并没有变化
        list = list.stream().filter(user -> user.getAge() > 18).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        // 不可变对象，value 不会再变
        String str = "abc";
        updateString(str);
        System.out.println(str); // "abc"

        // 不可变对象， value是final类型
        Integer num  = null;//= new Integer(0);
        new Thread(() -> {
            updateInteger(num);
        }).start();
        // 仍然是 NULL
        System.out.println("num 仍然是：" + num);

        // 逃逸
        User user = new User(1, "tom");
        updateUser(user);
        System.out.println(user.getName()); // Tom


        List<User> list = new ArrayList<>();
        list.add(new User(1, "Lily", 20, 50.0f));
        list.add(new User(1, "Lucy", 15, 40.0f));
        list.add(new User(1, "Li Lei", 22, 60.0f));

        filter(list);
        System.out.println(Arrays.toString(list.toArray()));

    }


}
