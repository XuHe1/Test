package top.lovelily.designpattern.singleton.designpattern;

/**
 *
 * 无论是提前实例化， 还是在static代码中，只要类被加载并被初始化后就会创建对象， 即使不用
 * Desc: TestEagerMode
 * Author: xuhe
 * Date: 2019/11/13 2:27 下午
 * Version: 1.0
 */
public class TestEagerMode {
    private static TestEagerMode instance; // = new TestLazyMode();

    static {
        System.out.println("static");
        instance = new TestEagerMode();
    }

    public TestEagerMode() {
        System.out.println("执行了构造方法");
    }
    public static TestEagerMode getInstance() {
        return instance;
    }
}
