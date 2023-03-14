package top.lovelily.collection;

import top.lovelily.User;

import java.util.*;

/**
 * Created by XuHe on 17/4/27.
 */
public class TestCollectionNull {

    public static void main(String[] args) {
        // map结构
        Map<Integer, String> table = new Hashtable<>();  //线程安全的，每个方法都synchronized
//        table.put(1, null);  // value不可以为空 NullPointerException
       // table.put(null, "null"); // key也不可以为空 NullPointerException
        System.out.println("==========Test Table=======");

        Map<Integer, String> map = new HashMap<>();
        map.put(1, null); // value值可以有多个null
        map.put(2, null);
        map.put(new Integer(3), "3");
        map.put(null, null); // key可以为null
        map.put(null, "NULL"); // 会覆盖
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
        hashMap.put(null, null); // key可以为null, hash(null) = 0;
        Set<String> set = hashMap.keySet();
        // set.add("4");  // unsupport
        // set.add("1");
        iterator = set.iterator();
        System.out.println(iterator.getClass().getName());
        System.out.println("HashMap key: ");
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            System.out.println(key);
        }

        // LinkedHashMap有序：遍历顺序与放入顺序一致, 额外维护了一个双向链表来保存放入顺序
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("1", "one");
        linkedHashMap.put("3", "three");
        linkedHashMap.put("2", "two");
        set = linkedHashMap.keySet();
        // set.add("4");  // unsupport
        // set.add("1");
        iterator = set.iterator();
        System.out.println(iterator.getClass().getName());
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            System.out.println(key);
        }

        // Collections.synchronizedList()


        List list = new ArrayList(3);
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(0);
        System.out.println(list.size());


      //  list.stream().flatMap();
        User tmp = new User(2, "Bye");

        User user1 = new User(1,"Hello");
        userList1.add(user1);
        user1.setName("Hello World"); //会影响到userList1
        //user1 = tmp; // 不影响
        //user1 = new User(2, "Bye"); // 不影响
        System.out.println(userList1.get(userList1.size() - 1).getName());

        Map<String, List<User>> map1 = new HashMap<>();
        List<User> users = map1.get("1");
        if (users == null) {
            users = new ArrayList<>();
            // 必须, 否则下面为null
            map1.put("1", users);
        }
        // null
        System.out.println(map1.get("1"));
        users.add(new User(1, "ll"));
        System.out.println(users);
        System.out.println(map1.get("1"));


    }
}
