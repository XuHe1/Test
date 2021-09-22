package top.lovelily.algrithm;

/**
 * Desc: TestFib
 * Author: xuhe
 * Date: 2019/2/25 4:33 PM
 * Version: 1.0
 */
public class TestFib {

    public static long getByRecursion(int index) {
        if (index == 0 || index == 1) {
            return 1;
        } else {
            return getByRecursion(index - 2) + getByRecursion(index - 1);
        }
    }
    // 1 1 2 3 5 8 13 ...
    public static long get(int index) {
        if (index == 0 || index == 1) {
            return 1;
        } else {
            long num1 = 1;
            long num2 = 1;
            for (int i = 2; i <= index; i++) {
                long  tmp = num1 + num2;
                num1 = num2;
                num2 = tmp;
            }
            return num2;
        }
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(get(50));
        long end = System.nanoTime();
        System.out.println("use for cost: " + (end - start));

        // 调用栈过深， 比较耗时
        start = System.nanoTime();
        System.out.println(getByRecursion(50));
        end = System.nanoTime();
        System.out.println("use recursion cost: " + (end - start));
    }
}
