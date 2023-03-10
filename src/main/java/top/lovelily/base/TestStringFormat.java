package top.lovelily.base;

import java.util.Random;

public class TestStringFormat {
    public static void main(String[] args) {
        // 4d 4位整数 不够的话 用0补
        String str = String.format("%04d", new Random(9999).nextInt());
    }
}
