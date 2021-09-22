package top.lovelily.collection;

import top.lovelily.User;

import java.util.*;

/**
 * Created by XuHe on 17/4/27.
 */
public class TestCollectionCompare {

    public static void main(String[] args) {

        List list = new ArrayList();
        list.add(1);
        list.add(2);

        List list1 = new ArrayList();
        list1.add(1); //自动装箱
        list1.add(2);

        System.out.println(list.equals(list1)); // true
        // 调用的是元素的 equals 方法， 逐个对比，因此顺序有要求
       // System.out.println(new Integer(1).equals(new Integer(1))); true


        List<String> strList = new ArrayList();
        strList.add("1");
        strList.add("2");
        strList.add("3");


        List<String> strList1 = new ArrayList();
        strList1.add("1");
        strList1.add("2");
        strList1.add("3");

        System.out.println(strList.equals(strList1)); // true


        System.out.println("==============================================");

        List<User> userList1 = new ArrayList<>();
        User user1 = new User(1,"Hello");
        userList1.add(user1);

        List<User> userList2 = new ArrayList<>();
        User user2 = new User(1,"Hello");
        userList2.add(user2);
        // 需要重写 user的 equals方法
        System.out.println(userList1.equals(userList2));

    }
}
