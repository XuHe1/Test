package top.lovelily.base;


interface ParentInterface{
    int a = 100;
}

interface ChildInterface extends ParentInterface{
    // 初始化不会触发父接口初始化
    int b = 100;
}

/**
 * 类加载过程:初始化
 * 1. 变量赋值
 * 2. 静态语句
 */
public class TestClinit implements ParentInterface{
    // 初始化不会触发父接口初始化，但是如果是父类（非接口）就会先触发父类的初始化
    int c = 100;
    public static void main(String[] args) {
        TestClinit test = new TestClinit();
        System.out.println(test);
    }
}
