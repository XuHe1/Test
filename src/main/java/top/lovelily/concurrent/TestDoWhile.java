package top.lovelily.concurrent;

import top.lovelily.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestDoWhile {
    public static void main(String[] args) {
        int a = 0;

            do {
                System.out.println("hello");
            } while (a > 0);


            while (true) {
                a++;
                if (a==100) {
                    //return;  // 退出循环，不再继续向下执行
                    break; // 退出循环
                }
            }

        User user = new User(1, "xuhe");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        Map<String, User> userMap = new HashMap<>();
        userMap.put("xuhe", user);
        BlockingQueue<User> blockingQueue = new LinkedBlockingQueue<>();
        blockingQueue.add(user);

        user.setName("xuhe1");

        System.out.println(userMap.get("xuhe").getName());
        System.out.println(userList.get(0).getName());
        // System.out.println(blockingQueue.poll().getName());

        User user1 = userMap.get("xuhe");
        user1.setName("xuhe2");

        System.out.println(userMap.get("xuhe").getName());
        System.out.println(userList.get(0).getName());
        System.out.println(blockingQueue.poll().getName());



    }
}
