package top.lovelily.reference;

import top.lovelily.User;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Desc: 虚引用，为了在被引用的对象回收后，收到通知，然后做其他事情，应用：直接内存
 * Author: xuhe
 * Date: 2020/7/10 2:57 下午
 * Version: 1.0
 */
public class TestPhantom {
    public static void main(String[] args) {
        User user = new User();
        ReferenceQueue queue = new ReferenceQueue<>();
        PhantomReference<User> userPhantomReference = new PhantomReference<User>(user, queue);
        System.out.println(userPhantomReference.get());
        System.out.println(queue.poll());
        user = null;

//        new Thread(() -> {
//            List<byte[]> bytesList = new ArrayList<>();
//            while (true) {
//                bytesList.add(new byte[1024*1024*1]);
//            }
//
//        }).start();
//

        new Thread(() -> {
            while (true) {
                System.gc();
                System.out.println("gc ...");
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (queue.poll() != null) {
                        System.out.println("虚引用指向的空间被回收了");
                        System.exit(0);
                    }
                }
            }
        }).start();

    }
}
