package top.lovelily.base;

import top.lovelily.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author h.xu
 * @create 2017-09-22 下午1:56
 **/

public class TestFinal {
    // 都只只能初始化一次；
    final  int a ; // 必须初始化，显示初始化，构造方法
    private static final int b = 0; // 显示初始化，static块中；不可以构造方法

    static {
        // a = 0; 错误
    }

    public TestFinal() {
        a = 0;// 增加了内存屏障，在对象返回前执行，保证返回的对象 final 都是已经初始化过的！！
        // b= 1; error
    }

    // final方法不可以被重写
    final void sayHello() {
       // a = 2; 不能初始化第二次
       // b = 2;
    }

    final static void sayBye() {

    }

    public static void main(String[] args) {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<User> userList = new ArrayList<>();
        long total = Runtime.getRuntime().totalMemory();
        long free1 = Runtime.getRuntime().freeMemory();
        System.out.println("Total: " + total + " free: " + free1);
        for (int i = 0; i < 350000; i++) {
            userList.add(new User(i, "user" + i));
            //userList.add(new User(i, "user" + i, 20, 175, "Shanghai, china"));
        }

        total = Runtime.getRuntime().totalMemory();
        long free2 = Runtime.getRuntime().freeMemory();
        System.out.println("Total: " + total + " free: " + free2);
        System.out.println("Used: " + (free1 - free2)/1024/1024 + " MB");
        // 每个对象占 97 bytes
        String[] names = new String[userList.size()];
        String[] names1 = new String[userList.size()];
        String[] names2 = new String[userList.size()];
        String[] names3 = new String[userList.size()];
        String[] names4 = new String[userList.size()];
        String[] names5 = new String[userList.size()];
        String[] names6 = new String[userList.size()];
        String[] names7 = new String[userList.size()];
        Iterator iterator = userList.iterator();
        User user;
        long start = System.currentTimeMillis();
        // 十毫秒级
        while (iterator.hasNext()) {
            user = (User) iterator.next();
            System.out.println(user.getName());
            names[user.getId()] = user.getName();
        }

        while (iterator.hasNext()) {
            user = (User) iterator.next();
            names1[user.getId()] = user.getName();
        }

        while (iterator.hasNext()) {
            user = (User) iterator.next();
            names2[user.getId()] = user.getName();
        }

        while (iterator.hasNext()) {
            user = (User) iterator.next();
            names3[user.getId()] = user.getName();
        }

        while (iterator.hasNext()) {
            user = (User) iterator.next();
            names4[user.getId()] = user.getName();
        }

        while (iterator.hasNext()) {
            user = (User) iterator.next();
            names5[user.getId()] = user.getName();
        }

        while (iterator.hasNext()) {
            user = (User) iterator.next();
            names6[user.getId()] = user.getName();
        }

        while (iterator.hasNext()) {
            user = (User) iterator.next();
            names7[user.getId()] = user.getName();
        }

//        long start = System.currentTimeMillis();
           // 百毫秒级
//        for (User user : userList) {
//            names[user.getId()] = user.getName();
//        }
//
//        for (User user : userList) {
//            names1[user.getId()] = user.getName();
//        }
//
//        for (User user : userList) {
//            names2[user.getId()] = user.getName();
//        }
//
//        for (User user : userList) {
//            names3[user.getId()] = user.getName();
//        }
//
//        for (User user : userList) {
//            names4[user.getId()] = user.getName();
//        }
//
//        for (User user : userList) {
//            names5[user.getId()] = user.getName();
//        }
//
//        for (User user : userList) {
//            names6[user.getId()] = user.getName();
//        }
//
//        for (User user : userList) {
//            names7[user.getId()] = user.getName();
//        }

        System.out.println("耗时："  + ((System.currentTimeMillis() - start)));

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
