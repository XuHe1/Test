package top.lovelily.base.staticinnerclass;

import top.lovelily.base.staticinnerclass.TestStatic;

public class Test {
    public static void main(String[] args) {
        // 静态内部类，外面类可以直接实例化
        TestStatic.StaticInner staticInner = new TestStatic.StaticInner();
        staticInner.setI(1);
        // 非静态的内部类，外部类不能实例化
        // TestStatic.Inner inner = new TestStatic.Inner();
    }
}
