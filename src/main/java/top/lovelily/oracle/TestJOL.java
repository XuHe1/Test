package top.lovelily.oracle;

import org.openjdk.jol.info.ClassLayout;

/**
 * Desc: TestJOL
 * Author: xuhe
 * Date: 2020/5/29 12:05 下午
 * Version: 1.0
 */
public class TestJOL {
    public static void main(String[] args) {
        // 8B MarkWord + 4B ClassPointer + 4B 对齐 = 16B
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
