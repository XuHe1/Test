package top.lovelily.base;

/**
 * Desc: TestOperator: >> <<  &  |
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


        System.out.println(10>>1); // 右移， 相当于除以2
        System.out.println(10<<1); // 左移， 相当于乘以2

    }


}
