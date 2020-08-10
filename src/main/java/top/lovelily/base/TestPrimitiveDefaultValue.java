package top.lovelily.base;

/**
 * Desc: TestPrimitiveDefaultValue
 * Author: xuhe
 * Date: 2020/8/3 10:12 上午
 * Version: 1.0
 */
public class TestPrimitiveDefaultValue {
    // 基本类型幽默认0值， 包括基本类型的数组
    static int arr[] = new int[6];
    static int i;
    static long l;
    static float f;
    static double d;

    // 引用类型，默认null
    static String str ;
    static Integer integer;
    static Integer[] integers = new Integer[10];

    public static void main(String[] args) {
        System.out.println(arr[0]);
        System.out.println(i);
        System.out.println(l);
        System.out.println(f);
        System.out.println(d);

        System.out.println(str);
        System.out.println(integer);


       // 局部变量必须初始化才能使用， 基础类型数组例外！
        int localArr[] = new int[6];
        System.out.println(localArr[0]);

        int num;
        // System.out.println(num);

        String string;
        // System.out.println(string);

        Integer integer1;
       // System.out.println(integer1);

        System.out.println(integers[0]);
    }
}
