package top.lovelily.designpattern.singleton;

/**
 * Desc: TestInternalClass
 * Author: xuhe
 * Date: 2019/11/13 11:38 上午
 * Version: 1.0
 */

public class TestInternalClass {
    private static TestInternalClass instance;
    // static只能修饰内部类, 内部类不能访问外部变量，首次使用时才会加载内部类
    final static class SingletonHolder {
        static TestInternalClass instance; // = new TestInternalClass();
        static {
           System.out.println("static");
           instance = new TestInternalClass();
       }
    }

    public static TestInternalClass getInstance() {
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        TestInternalClass test = new TestInternalClass();
       // System.out.println(getInstance());
    }

}
