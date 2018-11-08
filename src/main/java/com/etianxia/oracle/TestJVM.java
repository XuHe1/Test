package com.etianxia.oracle;

import com.etianxia.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: TestJVM
 * Author: xuhe
 * Date: 2018/10/17 下午2:59
 * Version: 1.0
 */
public class TestJVM {
    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " begin to sleep....");

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " begin to wakeup....");
        List<User> users = new ArrayList<>();
        // test heap
        for (int i = 0; i < 100000000; i++) {
           // Thread thread = new Thread();
            // todo: 逃逸
            new User(i, "xuhexuhexuhexuhexuhe", 20, 172, "中国上海市中国上海市中国上海市中国上海市中国上海市中国上海市中国上海市中国上海市中国上海市");
           // users.add(u);
        }

        System.out.println(Thread.currentThread().getName() + " begin to sleep again....");


        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
