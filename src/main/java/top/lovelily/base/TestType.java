package top.lovelily.base;

/**
 * Desc: TestType
 * Author: xuhe
 * Date: 2020/3/24 3:37 下午
 * Version: 1.0
 */
public class TestType {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println(time);
        int rtc = (int) (time / 1000);
        System.out.println(time);
        time = rtc * 1000; // error data, time is treated as an int
        System.out.println(time);
        System.out.println(Integer.MAX_VALUE);
        time = rtc * 1000l;
        System.out.println(time);


        int a = 2;
        int b = Integer.MAX_VALUE;
        long c = a * b;
        System.out.println(c); // -2

    }
}
