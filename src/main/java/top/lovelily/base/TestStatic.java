package top.lovelily.base;

/**
 * Desc: TestStatic
 * Author: xuhe
 * Date: 2020/10/26 12:08 下午
 * Version: 1.0
 */
public class TestStatic {

    private static int count = 1;
    private int b = 2;

    private static final int num = 0;
    private static final int num2;

    static {
        System.out.println("static block");
        num2 = 2;
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
    }

    static class StaticInner {
        private int i;
        private static int j;

        public void setI(int i) {
            this.i = count;
           // this.i = b;
        }
    }

    public static void main(String[] args) {
        TestStatic test = new TestStatic();
        test.increment(1);

        System.out.println(TestStatic.count);

        TestStatic test1 = new TestStatic();
        System.out.println(test1.getCount());

        StaticInner staticInner = new StaticInner();

    }

}
