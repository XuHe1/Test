package top.lovelily.designpattern.singleton;

import top.lovelily.User;
import top.lovelily.base.Apple;

/**
 * Desc: TestInternalClass
 * Author: xuhe
 * Date: 2019/11/13 11:38 上午
 * Version: 1.0
 */

public class TestInnerClass {
    private static TestInnerClass instance;

    // 多实现
    class Inner implements Comparable, Cloneable{

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    // 单继承
    class Inner1 extends User {


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
