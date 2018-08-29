package com.etianxia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by XuHe on 17/4/27.
 */
public class TestCollectionNull {
    public static void main(String[] args) {
        Map<Integer, String> table = new Hashtable<>();  //线程安全的，每个方法都synchronized
        // table.put(null, null);  //error!
        System.out.println("==========Test Table=======");
//        System.out.println(table.get(null));

        Map<Integer, String> map = new HashMap<>();
        map.put(1, null);
        map.put(2, null);
        map.put(new Integer(3), "3");
        map.put(null, null);
        map.put(null, "NULL");
        System.out.println("==========Test Map=======");
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.get(3));
        System.out.println(map.get(null));

        Map<User, String> objKeyMap = new HashMap<>();

        List<User>  userList1 = new ArrayList<>();
        List<User>  userList2 = new ArrayList<>(); //允许重复元素->  多个null
        Set<User> userSet = new HashSet<>(); // 去重 -> 1个null
        for (int i = 0; i < 10; i++) {
            User u = new User(i, "name" + i);
            userList1.add(u);
            userList1.add(u);
            userList1.add(null);
            userSet.add(u);
            userSet.add(u);
            userSet.add(null);

            objKeyMap.put(u, u.getName());

        }

        for (User user : objKeyMap.keySet()) {
            System.out.println(user + ": " + objKeyMap.get(user));
        }

        for (int i = 10; i < 20; i++) {
            User u = new User(i, "name" + i);
            userList2.add(u);
        }

        Iterator iterator = userList1.iterator();
        User user;
        while (iterator.hasNext()) {
            user = (User) iterator.next();
            System.out.println("List: " + user);
        }

        iterator = userList2.iterator();
        while (iterator.hasNext()) {
            user = (User) iterator.next();
            System.out.println(user.getId() + " : " + user.getName());
        }


        iterator = userSet.iterator();
        while (iterator.hasNext()) {
            user = (User) iterator.next();
            System.out.println("Set: " + user);
        }

//        List<User> userLists = null;
//        for (User u: userLists) {  // NPE
//            System.out.println(u.getName());
//        }



        Map<String, String> map1 = new HashMap<>();
        map1.put("1", "1");
        map1.put("2", "1");
        map1.put("3", "1");
        Set<String> set = map1.keySet();
        set.add("4");  // unsupport
        set.add("1");
        for (String str: set) {
            System.out.println(str);
        }



    }
}
