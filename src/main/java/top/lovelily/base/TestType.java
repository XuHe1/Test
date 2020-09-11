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


        double d = 138.918248864067381; // 小数点后14位
        System.out.println(d);

        float f = 138.91824886406738f; // 小数点后5位
        System.out.println(f);

        int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 16
        System.out.println(DEFAULT_INITIAL_CAPACITY);

        float DEFAULT_LOAD_FACTOR = 0.75f;

        int threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        System.out.println(threshold);


    }
}
