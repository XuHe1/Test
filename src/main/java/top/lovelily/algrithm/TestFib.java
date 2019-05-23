package top.lovelily.algrithm;

/**
 * Desc: TestFib
 * Author: xuhe
 * Date: 2019/2/25 4:33 PM
 * Version: 1.0
 */
public class TestFib {

    public static int getByRecursion(int index) {
        if (index == 0 || index == 1) {
            return 1;
        } else {
            return getByRecursion(index - 2) + getByRecursion(index - 1);
        }
    }
    // 1 1 2 3 5 8 13 ...
    public static int get(int index) {
        if (index == 0 || index == 1) {
            return 1;
        } else {
            int num1 = 1;
            int num2 = 1;
            for (int i = 2; i <= index; i++) {
                int tmp = num1 + num2;
                num1 = num2;
                num2 = tmp;
            }
            return num2;
        }
    }

    public static void main(String[] args) {
        System.out.println(get(5));
    }
}
