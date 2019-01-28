package top.lovelily.collection;

import top.lovelily.User;

import java.util.*;

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


        // HashMap无序的：遍历顺序与放入顺序不一致
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "one");
        hashMap.put("3", "three");
        hashMap.put("2", "two");
        Set<String> set = hashMap.keySet();
        // set.add("4");  // unsupport
        // set.add("1");
        iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            System.out.println(key);
        }

        // LinkedHashMap有序：遍历顺序与放入顺序一致
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("1", "one");
        linkedHashMap.put("3", "three");
        linkedHashMap.put("2", "two");
        set = linkedHashMap.keySet();
        // set.add("4");  // unsupport
        // set.add("1");
        iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            System.out.println(key);
        }


    }
}
