package top.lovelily.reference;

import java.lang.ref.SoftReference;

/**
 * Desc: TestSoft
 * 软引用包装的对象发生gc不会被立刻回收，只有再次申请内存，内存不够时会被回收
 * Author: xuhe
 * Date: 2020/7/10 2:51 下午
 * Version: 1.0
 */
public class TestSoft {
    public static void main(String[] args) {
        // -Xmx20M
        SoftReference<byte[]> userSoftReference = new SoftReference<>(new byte[1024 * 1024 * 10]); // 10 M
        System.out.println(userSoftReference.get());
        System.gc();
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(userSoftReference.get());

        byte[] bytes = new byte[1024 * 1024 * 12]; // 12M   已经申请了10M， 再次申请12M todo: -Xmx20M 不能申请15M?
        System.out.println(userSoftReference.get()); //  弱引用被回收

    }
}
