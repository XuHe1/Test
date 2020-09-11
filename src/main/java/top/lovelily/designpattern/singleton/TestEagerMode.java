package top.lovelily.designpattern.singleton;

/**
 *
 * 无论是提前实例化， 还是在static代码中，只要类被加载并被初始化后就会创建对象， 即使不用
 * Desc: TestEagerMode
 * Author: xuhe
 * Date: 2019/11/13 2:27 下午
 * Version: 1.0
 */
public class TestEagerMode {
    private static TestEagerMode instance; //= new TestEagerMode();
    private static Test test = new Test("static"); // 类初始化： 会被初始化
    private  Test test1 = new Test(); // 类初始化不会被初始化
    public static int a = 9;

    static {
        System.out.println("static block");
    }

    public TestEagerMode() {
        System.out.println("执行了构造方法");
    }
    public static TestEagerMode getInstance() {
        return instance;
    }
}

class Test {
    public Test() {
        System.out.println("初始化了普通成员变量");
    }

    public Test(String s) {
        System.out.println("初始化了static成员变量");
    }
}
