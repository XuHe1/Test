package top.lovelily.base.staticinnerclass;

import top.lovelily.User;

/**
 * Desc: TestStatic
 * Author: xuhe
 * Date: 2020/10/26 12:08 下午
 * Version: 1.0
 */
public class TestStatic {
    // clinit
    private static int count = 1;
    private int b = 2;

    private static final int num = 0;
    private static final int num2;

    private static User user;

    static {
        System.out.println("static block");
        num2 = 2;
    }

    static void incre() {
        count ++;
    }

    public void increment(int increment) {
        count = count + increment;
    }

    public int getCount() {
        return count;
    }

    public TestStatic() {
        //num2 = 2;
        System.out.println("constructor");
    }

    class Inner {
        private int i;
        private  int j;
        // private static int a; 非静态内部类不能定义static属性
    }

    static class StaticInner {
        private int i;
        private static int j;

        public void setI(int i) {
            this.i = count;
           // this.i = b;
        }
    }

    public void m1() {
        TestStatic testStatic = new TestStatic();
         Inner inner = new TestStatic.Inner(); // 外部不能这样使用，编译报错！!
        inner = testStatic.new Inner();
    }

    public static void main(String[] args) {
        TestStatic test = new TestStatic();
        test.increment(1);

        System.out.println(TestStatic.count);
        TestStatic.count = 3;
        System.out.println(TestStatic.count);

        TestStatic test1 = new TestStatic();
        System.out.println(test1.getCount());

        StaticInner staticInner = new StaticInner();

        // 静态变量作为缓存
        TestStatic.user = new User(1, "xuhe");
        System.out.println(TestStatic.user.getName());

        TestStatic.user = new User(1, "xuhe2");
        System.out.println(TestStatic.user.getName());




    }

}
