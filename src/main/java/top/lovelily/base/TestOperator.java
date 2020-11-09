package top.lovelily.base;

/**
 * Desc: TestOperator: >> <<  &  |
 *
 * <<      :     左移运算符，num << 1,相当于num乘以2
 *
 * >>      :     右移运算符，num >> 1,相当于num除以2
 *
 * >>>    :     无符号右移，忽略符号位，空位都以0补齐
 *
 * Author: xuhe
 * Date: 2019/1/17 11:22 AM
 * Version: 1.0
 */
public class TestOperator {
    int size;
    public static void main(String[] args) {
        int a;
        TestOperator testOperator = new TestOperator();
        System.out.println(testOperator.size);
        // System.out.println(a);
        String string = Integer.toBinaryString(10);
        System.out.println(string);


        System.out.println(15>>1); // 右移， 相当于除以2的一次幂
        System.out.println(10<<1); // 左移1位， 相当于乘以2，左移n位相当于乘以2的n次方

        System.out.println(1 << 3); // 1 * 2的3次方

        // 11111111 11111111 65535  >>> 16   0
        //100000000 00000000 65536  >>> 16   000000000

        System.out.println(65536 >>> 16);
        System.out.println(65536 >> 16);

        //
        int SHARED_SHIFT   = 16;
        int SHARED_UNIT    = (1 << SHARED_SHIFT); // 32
        int MAX_COUNT      = (1 << SHARED_SHIFT) - 1; // 31
        int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1; // 31
        System.out.println(SHARED_UNIT + " " + MAX_COUNT + " " + EXCLUSIVE_MASK);


        long time = 1604479971000l;
        long time2 = 1604479971000l + 300000l;
        long duration = (time2-time)/1000;


        System.out.println((int)duration);

    }


}
