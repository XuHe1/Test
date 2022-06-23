package top.lovelily.collection;

import top.lovelily.User;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TestPriorityQueue {
    public static void main(String[] args) {
        PriorityQueue<String> queue = new PriorityQueue<>();
        queue.add("B");
        queue.add("A");
        String head = queue.poll();
        System.out.println(head);
        System.out.println(queue.size());

        // 二叉小顶堆，取出的永远是权重最小的元素
        PriorityQueue<User> queue1 = new PriorityQueue<>(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getId() - o2.getId();
            }
        });

        queue1.add(new User(2, "张三"));
        queue1.add(new User(1, "李四"));
        User header1 = queue1.poll();
        System.out.println(header1.getName());


    }
}
