package top.lovelily.oracle;

import org.openjdk.jol.info.ClassLayout;
import top.lovelily.Address;
import top.lovelily.User;

/**
 * Desc: TestJOL
 * Author: xuhe
 * Date: 2020/5/29 12:05 下午
 * Version: 1.0
 */
public class TestJOL {
    public static void main(String[] args) {
        // 8B MarkWord + 4B ClassPointer + 4B 对齐 = 16B   // 8的整数倍
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        Thread t = new Thread();
        //String s = new String("hello, World."); // 24 B   8B MarkWord + 4B ClassPointer +  4B value + 4B hash + 4B 对齐 = 24B
        String s = s = "hello, World.";
        System.out.println(ClassLayout.parseInstance(s).toPrintable());


        /**
         *  OFFSET  SIZE                   TYPE DESCRIPTION                               VALUE
         *       0     4                        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4                        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4                        (object header)                           46 03 01 f8 (01000110 00000011 00000001 11111000) (-134151354)
         *      12     4                    int User.id                                   1
         *      16     4                    int User.age                                  30
         *      20     4                  float User.height                               172.0
         *      24     4       java.lang.String User.name                                 (object)
         *      28     4   top.lovelily.Address User.addr                                 (object)
         */

        User user = new User(1, "xuhe", 30, 172f);
        user.setAddr(new Address());
        System.out.println(ClassLayout.parseInstance(user).toPrintable());  // 32B, 对象引用只占4个字节(-XX:+UseCompressedOops, 否则是8B)，被引用对象不计入引用对象的内存空间内

        Integer i = 0;
        System.out.println(ClassLayout.parseInstance(i).toPrintable());

    }
}
