package top.lovelily.concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author h.xu
 * @create 2018-01-30 下午7:01
 **/

public class TestIDGenerator {
    // 1. snowflake  2. atomic  3. ThreadLocalRandom (Math.random)

    public static void main(String[] args) {
        int r = ThreadLocalRandom.current() .nextInt(4, 77);
    }
}
