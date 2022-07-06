package top.lovelily.base;

public class TestStaticChild extends TestStatic{

    // 父类的 static 方法不能被重写
    static void incre() {
        System.out.println("child static");
    }

    public static void main(String[] args) {
        TestStatic t = new TestStaticChild();
        // 指向子类的父类对象，调用不了父类的静态方法
        //t.incr();
        // 可以调用父类的非静态方法
        t.increment(1);



        TestStatic t1 = new TestStatic();
    }
}
