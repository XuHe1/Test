package top.lovelily.designpattern.singleton;

/**
 * Desc: TestInternalClass
 * Author: xuhe
 * Date: 2019/11/13 11:38 上午
 * Version: 1.0
 */

public class TestInnerClass {
    private static TestInnerClass instance;

    class Inner{

    }

    // static只能修饰内部类, 内部类不能访问外部变量，首次使用时才会加载内部类
    final static class SingletonHolder {

        static TestInnerClass instance; // = new TestInternalClass();
        static {
           System.out.println("static");
           instance = new TestInnerClass();
       }
    }

    public static TestInnerClass getInstance() {
        return SingletonHolder.instance; // 加载内部类
    }

    public static void main(String[] args) {
        TestInnerClass test = new TestInnerClass();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
       System.out.println(getInstance());

       SingletonHolder holder = new SingletonHolder();

       TestInnerClass testInnerClass = new TestInnerClass();
       // Inner inner = new Inner();
      Inner inner = testInnerClass.new Inner();

    }

}
