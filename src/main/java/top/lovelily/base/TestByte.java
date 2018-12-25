package top.lovelily.base;

import java.nio.charset.Charset;

/**
 * Created by kaiitsugyou on 16/12/8.
 */
public class TestByte {
    public static void main(String[] args) {

        // 2^0 + 2^1 + .... + 2^n = 2^n+1 - 1

        // 有符号
        byte b = 127;  // 最大的应该是0111 1111，因为第一位是符号位，0表示正数 2^7-1
        byte c = -128; // 最小的应该是1000 0000，因为第一位是符号位，1表示负数 -2^7

        // 无符号 0-255  00000000－－－11111111 2^8-1


        // 有符号
        short d = 32767;  // 2^15-1
        short e = -32768;  // -2^15

        // 无符号  0---65535



        int a = 1; //int 4个字节  -2^31   2^31 - 1

        //1111111111111111111111111111111     2^31 - 1
        //10000000000000000000000000000000    2^31

        //int max = (int) Math.pow(2, 31);


        long max = (long) Math.pow(2, 31) - 1;
        long min = -(long) Math.pow(2, 31);

        min = (long) Math.pow(2, 15);
        max = (long) (Math.pow(2, 15) - 1);

        System.out.println(min + "——" + max);

        // hashmap table[(len-1) & hash(key)]
        System.out.println(1 & 1);

        String zw = "我";
        // utf-8 3个字节
        System.out.println("一个中文： " + zw.getBytes(Charset.defaultCharset()).length);
        // gbk 2个字节
        System.out.println("一个中文：" + zw.getBytes(Charset.forName("GBK")).length);
        String yw = "a";
        // 1个字节
        System.out.println("一个非中文："  + yw.getBytes().length);

    }
}
