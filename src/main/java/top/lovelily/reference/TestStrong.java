package top.lovelily.reference;

import org.openjdk.jol.info.ClassLayout;
import top.lovelily.User;

import java.lang.ref.SoftReference;

/**
 * Desc: TestStrong
 * Author: xuhe
 * Date: 2020/7/10 1:59 下午
 * Version: 1.0
 */



public class TestStrong {
    public static void main(String[] args) {
        User user = new User(1, "xuhe", 30, 172f);
        user = null;
        System.gc();
    }
}
